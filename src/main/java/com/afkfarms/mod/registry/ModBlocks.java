package com.afkfarms.mod.registry;

import com.afkfarms.mod.block.FarmBlock;
import com.afkfarms.mod.block.entity.FarmBlockEntity;
import com.afkfarms.mod.farm.FarmTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks {
    private ModBlocks() {
    }

    public static final FarmBlock WHEAT_FARM = register("wheat_farm",
            new FarmBlock(AbstractBlock.Settings.create().strength(3.5f).nonOpaque(), FarmTypes.WHEAT,
                    (pos, state) -> new FarmBlockEntity(ModBlockEntities.WHEAT_FARM, pos, state, FarmTypes.WHEAT)));

    private static <T extends Block> T register(String path, T block) {
        return Registry.register(Registries.BLOCK, Identifier.of("afkfarms", path), block);
    }

    public static void init() {
        // triggers static init
    }
}
