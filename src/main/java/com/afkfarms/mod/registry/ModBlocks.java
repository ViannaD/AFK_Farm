package com.afkfarms.mod.registry;

import com.afkfarms.mod.block.FarmBlock;
import com.afkfarms.mod.block.entity.FarmBlockEntity;
import com.afkfarms.mod.farm.FarmTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModBlocks {
    private ModBlocks() {
    }

    public static final FarmBlock WHEAT_FARM = register("wheat_farm",
            settings -> new FarmBlock(settings, FarmTypes.WHEAT,
                    (pos, state) -> new FarmBlockEntity(ModBlockEntities.WHEAT_FARM, pos, state, FarmTypes.WHEAT)),
            AbstractBlock.Settings.create().strength(3.5f).nonOpaque());

    private static <T extends Block> T register(String path, java.util.function.Function<AbstractBlock.Settings, T> factory,
                                                 AbstractBlock.Settings settings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of("afkfarms", path));
        T block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }

    public static void init() {
        // triggers static init
    }
}
