package com.afkfarms.mod.registry;

import com.afkfarms.mod.block.entity.FarmBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlockEntities {
    private ModBlockEntities() {
    }

    // IMPORTANT: ModBlocks must be loaded before this class (AfkFarmsMod.onInitialize() enforces
    // that order) so that ModBlocks.WHEAT_FARM already exists here.
    //
    // The field declaration and its assignment are split on purpose: javac treats a simple
    // reference to a static final field *inside its own variable-initializer expression* as an
    // illegal forward reference, even inside a lambda that only runs later ("self-reference in
    // initializer"). Moving the assignment into a static initializer block (a separate statement,
    // not the field's initializer) sidesteps that rule - by the time the lambda actually runs
    // (when a block entity is created), WHEAT_FARM is already assigned.
    public static final BlockEntityType<FarmBlockEntity> WHEAT_FARM;

    static {
        WHEAT_FARM = register("wheat_farm",
                FabricBlockEntityTypeBuilder.create(
                        (pos, state) -> new FarmBlockEntity(WHEAT_FARM, pos, state, com.afkfarms.mod.farm.FarmTypes.WHEAT),
                        ModBlocks.WHEAT_FARM
                ).build());
    }

    private static <T extends BlockEntityType<?>> T register(String id, T type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("afkfarms", id), type);
    }

    public static void init() {
        // triggers static init
    }
}
