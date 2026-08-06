package com.afkfarms.mod.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * In the original addon, placing a farm is a two-step process: craft a generic
 * "blueprint_base", spend a farm's build-cost materials on it via a converter menu to turn
 * it into a specific "blueprint_farm_x" item, then use that item to place the farm.
 * <p>
 * For this Fabric port that whole flow is collapsed into a single BlockItem per farm
 * (crafted directly, see data/afkfarms/recipes) that places the farm block like any other
 * block item - simpler, and the block itself still enforces the same fuel/production rules.
 */
public final class ModItems {
    private ModItems() {
    }

    public static final Item WHEAT_FARM_BLUEPRINT = register("wheat_farm",
            key -> new BlockItem(ModBlocks.WHEAT_FARM, new Item.Settings()
                    .useBlockPrefixedTranslationKey()
                    .maxCount(64)
                    .registryKey(key)));

    private static <T extends Item> T register(String path, java.util.function.Function<RegistryKey<Item>, T> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("afkfarms", path));
        return Registry.register(Registries.ITEM, key, factory.apply(key));
    }

    public static void init() {
        ItemGroupSetup.register();
    }

    private static final class ItemGroupSetup {
        static void register() {
            net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
                    .register(entries -> entries.add(WHEAT_FARM_BLUEPRINT));
        }
    }
}
