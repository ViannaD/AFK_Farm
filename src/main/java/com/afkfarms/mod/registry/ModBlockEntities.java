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
    // This field is intentionally NOT `final`. A blank `final` field must be provably
    // definitely-assigned at every point it's read, and javac enforces that even for a read
    // inside a lambda that will only actually run later (when a block entity is created) -
    // so a final field here fails to compile with "variable WHEAT_FARM might not have been
    // initialized", even from inside its own static initializer block. Dropping `final` removes
    // that compile-time check; it's still only assigned once, right here.
    public static BlockEntityType<FarmBlockEntity> WHEAT_FARM;

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
