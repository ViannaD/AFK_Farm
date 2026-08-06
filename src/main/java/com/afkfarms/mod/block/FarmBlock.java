package com.afkfarms.mod.block;

import com.afkfarms.mod.block.entity.FarmBlockEntity;
import com.afkfarms.mod.farm.FarmType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Base block for every AFK farm.
 * <p>
 * In the original Bedrock addon a "farm" is a rideable, non-solid entity with a custom
 * hit-test box that is spawned by a blueprint item and snapped to one of 4 yaw rotations
 * (rot_0 / rot_90 / rot_180 / rot_-90 component groups). Here that becomes a normal
 * horizontally-facing block, which is simpler to place, survives world reload without any
 * extra bookkeeping, and works with vanilla hoppers/comparators out of the box.
 * <p>
 * The visible model is intentionally larger than the block's own hitbox (like a Bedrock
 * entity with a bigger render bounds than its collision box) - see the concrete block
 * entity renderer for each farm type.
 */
public class FarmBlock extends Block implements BlockEntityProvider {
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class,
            Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    // Roughly matches the original's `collision.volume` footprint (about 3x3, 2-3 tall).
    private static final VoxelShape SHAPE = VoxelShapes.union(
            createCuboidShape(0, 0, 0, 16, 16, 16)
    );

    /** Deferred factory so FarmBlock doesn't need a hard compile-time link to a BlockEntityType (avoids class-init cycles). */
    @FunctionalInterface
    public interface EntityFactory {
        FarmBlockEntity create(BlockPos pos, BlockState state);
    }

    private final FarmType farmType;
    private final EntityFactory entityFactory;

    public FarmBlock(Settings settings, FarmType farmType, EntityFactory entityFactory) {
        super(settings);
        this.farmType = farmType;
        this.entityFactory = entityFactory;
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    public FarmType getFarmType() {
        return farmType;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(net.minecraft.item.ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        // No baked block model - fully drawn by the FarmBlockEntityRenderer, like the
        // original's custom-geometry entity.
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return entityFactory.create(pos, state);
    }

    @Override
    protected ActionResult onUseWithItem(net.minecraft.item.ItemStack stack, BlockState state, World world, BlockPos pos,
                                          PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
        if (world.getBlockEntity(pos) instanceof FarmBlockEntity be) {
            if (!world.isClient) {
                if (be.onPlayerUse(player, stack)) {
                    return ActionResult.SUCCESS;
                }
                if (player instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer) {
                    serverPlayer.openHandledScreen(be);
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (world.getBlockEntity(pos) instanceof FarmBlockEntity be) {
                net.minecraft.inventory.ItemScatterer.spawn(world, pos, be);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (w, pos, st, be) -> FarmBlockEntity.tick(w, pos, st, (FarmBlockEntity) be);
    }
}
