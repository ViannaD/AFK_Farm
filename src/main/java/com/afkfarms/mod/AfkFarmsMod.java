package com.afkfarms.mod;

import com.afkfarms.mod.registry.ModBlockEntities;
import com.afkfarms.mod.registry.ModBlocks;
import com.afkfarms.mod.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AfkFarmsMod implements ModInitializer {
    public static final String MOD_ID = "afkfarms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Order matters: ModBlocks must load before ModBlockEntities (which needs the
        // block instances to call `.validFor(...)` on its BlockEntityType.Builder), and
        // ModItems needs ModBlocks for its BlockItem. See the comments in ModBlockEntities.
        ModBlocks.init();
        ModBlockEntities.init();
        ModItems.init();

        LOGGER.info("AFK Farms (Fabric port) initialized - {} farm type(s) loaded", 1);
    }
}
