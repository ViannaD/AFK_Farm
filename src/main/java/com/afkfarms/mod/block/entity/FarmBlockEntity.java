package com.afkfarms.mod.block.entity;

import com.afkfarms.mod.farm.FarmResource;
import com.afkfarms.mod.farm.FarmType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java/Fabric equivalent of the Bedrock addon's farm actor: the combination of
 * {@code FarmingComponent} + {@code FarmFuelComponent} (scripts/actors/components/farming.js
 * and fuel.js) rolled into a single BlockEntity.
 * <p>
 * Behaviour ported from the original:
 * <ul>
 *   <li>Production is driven by real (wall-clock) elapsed time, not just ticks - a farm
 *       keeps "producing" fuel-permitting even if the chunk was unloaded or the server was
 *       closed, exactly like the Bedrock version's {@code Date.now()}-based timer. This is
 *       what makes it an "AFK" farm: progress is calculated in a single catch-up batch
 *       whenever the block entity is next ticked.</li>
 *   <li>Fuel (coal / charcoal / coal block) keeps the farm running and is consumed by
 *       elapsed real seconds, capped at {@link #FUEL_CAP} seconds (~416 days).</li>
 *   <li>"Boost" items (defined per {@link FarmType}) temporarily halve the production
 *       interval, capped at {@link #BOOST_CAP} seconds.</li>
 *   <li>Produced items go into a 27-slot inventory (same as the original's
 *       {@code minecraft:inventory} component); if it's full, production keeps counting
 *       an overflow backlog that drains first once space frees up.</li>
 * </ul>
 */
public class FarmBlockEntity extends BlockEntity implements Inventory, NamedScreenHandlerFactory {

    public static final long FUEL_CAP = 35_996_400L; // seconds, matches Bedrock FUEL_CAP
    public static final long BOOST_CAP = 5_120L;      // seconds, matches Bedrock BOOST_CAP

    private static final Map<Item, Integer> FUEL_TABLE = Map.of(
            Items.COAL_BLOCK, 800,
            Items.CHARCOAL, 80,
            Items.COAL, 80
    );

    private final FarmType farmType;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(27, ItemStack.EMPTY);

    private long fuelSeconds = 0;
    private long boostSeconds = 0;
    private boolean timerVisible = true;

    /** Epoch seconds of the last time production actually gathered an item (fractional progress preserved otherwise). */
    private long lastGatherEpochSec = nowEpochSec();
    /** Epoch seconds of the last poll, used to drain fuel/boost even when nothing was produced. */
    private long lastPollEpochSec = nowEpochSec();

    private final Map<Item, Integer> overflow = new HashMap<>();

    /** Client-synced "is running" flag, mirrors Bedrock's `ldz_241216:active` entity property. */
    private boolean active = false;
    /** Client-synced boost fraction 0..1, mirrors `ldz_241216:boost_percent`. */
    private float boostPercent = 0f;

    public FarmBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, FarmType farmType) {
        super(type, pos, state);
        this.farmType = farmType;
    }

    private static long nowEpochSec() {
        return System.currentTimeMillis() / 1000L;
    }

    public FarmType getFarmType() {
        return farmType;
    }

    public boolean isActive() {
        return active;
    }

    public float getBoostPercent() {
        return boostPercent;
    }

    public boolean isTimerVisible() {
        return timerVisible;
    }

    public void toggleTimerVisible() {
        this.timerVisible = !this.timerVisible;
        markDirty();
    }

    public String getFuelText() {
        long total = fuelSeconds;
        long hours = total / 3600;
        long minutes = (total % 3600) / 60;
        long seconds = total % 60;
        return String.format("%04d:%02d:%02d", hours, minutes, seconds);
    }

    // ------------------------------------------------------------------
    // Player interaction: feed fuel / boost items directly, like the original
    // (right-click the farm holding coal/charcoal/coal block = fuel, holding a
    // boost item = boost). Any other item / empty hand opens the inventory.
    // ------------------------------------------------------------------

    /** @return true if the interaction was consumed (fuel/boost added), false to fall through to opening the GUI. */
    public boolean onPlayerUse(PlayerEntity player, ItemStack heldStack) {
        if (!heldStack.isEmpty() && FUEL_TABLE.containsKey(heldStack.getItem())) {
            addFuelFromStack(player, heldStack, FUEL_TABLE, FUEL_CAP, true);
            return true;
        }
        Integer boostAmount = farmType.boostTable().get(heldStack.isEmpty() ? null : heldStack.getItem());
        if (boostAmount != null) {
            addFuelFromStack(player, heldStack, farmType.boostTable(), BOOST_CAP, false);
            return true;
        }
        if (player.isSneaking() && heldStack.isEmpty()) {
            toggleTimerVisible();
            return true;
        }
        return false;
    }

    private void addFuelFromStack(PlayerEntity player, ItemStack stack, Map<Item, Integer> table, long cap, boolean isFuel) {
        long current = isFuel ? fuelSeconds : boostSeconds;
        if (current >= cap) {
            return;
        }
        Integer perItem = table.get(stack.getItem());
        if (perItem == null) return;
        int useAmount = player.isSneaking() ? stack.getCount() : 1;
        long totalToAdd = Math.min(cap - current, (long) perItem * useAmount);
        if (totalToAdd <= 0) return;
        if (isFuel) {
            fuelSeconds = MathHelper.clamp(fuelSeconds + totalToAdd, 0, FUEL_CAP);
            boolean nowActive = fuelSeconds > 0;
            if (!active && nowActive) {
                lastGatherEpochSec = nowEpochSec();
                lastPollEpochSec = nowEpochSec();
            }
            active = nowActive;
        } else {
            boostSeconds = MathHelper.clamp(boostSeconds + totalToAdd, 0, BOOST_CAP);
            boostPercent = (float) boostSeconds / BOOST_CAP;
        }
        if (!player.isCreative()) {
            stack.decrement(useAmount);
        }
        markDirty();
    }

    // ------------------------------------------------------------------
    // Production tick - call every server tick from a BlockEntityTicker.
    // Internally throttled to ~once per second, same cadence as the original.
    // ------------------------------------------------------------------

    public static void tick(net.minecraft.world.World world, BlockPos pos, BlockState state, FarmBlockEntity be) {
        if (world.isClient) return;
        if (world.getTime() % 20 != 0) return; // poll ~once per second, like the original
        be.collect((ServerWorld) world);
    }

    private void collect(ServerWorld world) {
        long now = nowEpochSec();

        // Drain any pending overflow into the inventory first.
        drainOverflow();

        if (fuelSeconds > 0) {
            double productionRate = farmType.productionRate(boostSeconds > 0);
            long elapsedSinceGather = now - lastGatherEpochSec;
            long elapsedSincePoll = now - lastPollEpochSec;

            long maxByFuel = fuelSeconds * farmType.fuelCostPerItem();
            long count = Math.min((long) Math.floor(elapsedSinceGather / productionRate), maxByFuel);

            if (count > 0) {
                long added = addResources(count);
                long leftover = count - added;
                if (leftover > 0) {
                    overflow.merge(pickResource(), (int) leftover, Integer::sum);
                }
                lastGatherEpochSec = now;
            }

            long fuelUsed = Math.min(fuelSeconds, elapsedSincePoll * farmType.fuelCostPerItem());
            long boostUsed = Math.min(boostSeconds, elapsedSincePoll * farmType.fuelCostPerItem());
            fuelSeconds = Math.max(0, fuelSeconds - fuelUsed);
            boostSeconds = Math.max(0, boostSeconds - boostUsed);
            boostPercent = BOOST_CAP > 0 ? (float) boostSeconds / BOOST_CAP : 0f;

            boolean nowActive = fuelSeconds > 0;
            if (active != nowActive) {
                active = nowActive;
            }
        }

        lastPollEpochSec = now;
        markDirty();
    }

    private void drainOverflow() {
        if (overflow.isEmpty()) return;
        for (Map.Entry<Item, Integer> entry : new HashMap<>(overflow).entrySet()) {
            int remaining = entry.getValue();
            int placed = 0;
            for (int i = 0; i < remaining; i++) {
                if (!insertOne(entry.getKey())) break;
                placed++;
            }
            if (placed >= remaining) {
                overflow.remove(entry.getKey());
            } else {
                overflow.put(entry.getKey(), remaining - placed);
            }
        }
    }

    /** Rolls {@code count} production events against the farm's resource table and inserts what fits. @return number actually inserted. */
    private long addResources(long count) {
        long inserted = 0;
        for (long i = 0; i < count; i++) {
            Item item = pickResource();
            if (item != null && insertOne(item)) {
                inserted++;
            } else if (item != null) {
                overflow.merge(item, 1, Integer::sum);
            }
        }
        return inserted;
    }

    private Item pickResource() {
        List<FarmResource> resources = farmType.resources();
        if (resources.isEmpty()) return null;
        List<FarmResource> shuffled = new ArrayList<>(resources);
        java.util.Collections.shuffle(shuffled);
        for (FarmResource r : shuffled) {
            if (world != null && world.random.nextFloat() <= r.chance()) {
                return r.item();
            }
        }
        return shuffled.get(0).item();
    }

    private boolean insertOne(Item item) {
        ItemStack toInsert = new ItemStack(item, 1);
        for (int i = 0; i < items.size(); i++) {
            ItemStack slot = items.get(i);
            if (slot.isEmpty()) {
                items.set(i, toInsert);
                return true;
            }
            if (ItemStack.areItemsAndComponentsEqual(slot, toInsert) && slot.getCount() < slot.getMaxCount()) {
                slot.increment(1);
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Inventory implementation (27 slots, chest-like) - lets vanilla hoppers
    // pull from this block automatically, matching the original's hopper chain.
    // ------------------------------------------------------------------

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(items, slot, amount);
        if (!result.isEmpty()) markDirty();
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(items, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return world != null && world.getBlockEntity(pos) == this
                && player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.afkfarms.farm");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    // ------------------------------------------------------------------
    // Persistence
    // ------------------------------------------------------------------

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putLong("FuelSeconds", fuelSeconds);
        nbt.putLong("BoostSeconds", boostSeconds);
        nbt.putLong("LastGatherEpochSec", lastGatherEpochSec);
        nbt.putLong("LastPollEpochSec", lastPollEpochSec);
        nbt.putBoolean("TimerVisible", timerVisible);
        nbt.putBoolean("Active", active);
        Inventories.writeNbt(nbt, items, registryLookup);

        NbtCompound overflowNbt = new NbtCompound();
        for (Map.Entry<Item, Integer> entry : overflow.entrySet()) {
            overflowNbt.putInt(net.minecraft.registry.Registries.ITEM.getId(entry.getKey()).toString(), entry.getValue());
        }
        nbt.put("Overflow", overflowNbt);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        fuelSeconds = nbt.getLong("FuelSeconds");
        boostSeconds = nbt.getLong("BoostSeconds");
        lastGatherEpochSec = nbt.contains("LastGatherEpochSec") ? nbt.getLong("LastGatherEpochSec") : nowEpochSec();
        lastPollEpochSec = nbt.contains("LastPollEpochSec") ? nbt.getLong("LastPollEpochSec") : nowEpochSec();
        timerVisible = !nbt.contains("TimerVisible") || nbt.getBoolean("TimerVisible");
        active = nbt.getBoolean("Active");
        boostPercent = BOOST_CAP > 0 ? (float) boostSeconds / BOOST_CAP : 0f;
        items.clear();
        Inventories.readNbt(nbt, items, registryLookup);

        overflow.clear();
        if (nbt.contains("Overflow")) {
            NbtCompound overflowNbt = nbt.getCompound("Overflow");
            for (String key : overflowNbt.getKeys()) {
                net.minecraft.registry.Registries.ITEM.getOrEmpty(net.minecraft.util.Identifier.of(key))
                        .ifPresent(item -> overflow.put(item, overflowNbt.getInt(key)));
            }
        }
    }
}
