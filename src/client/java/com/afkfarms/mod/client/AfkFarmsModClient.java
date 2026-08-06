package com.afkfarms.mod.client;

import com.afkfarms.mod.client.render.WheatFarmBlockEntityRenderer;
import com.afkfarms.mod.client.render.WheatFarmModel;
import com.afkfarms.mod.registry.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererFactories;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

public class AfkFarmsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(WheatFarmModel.LAYER, WheatFarmModel::getTexturedModelData);
        BlockEntityRendererFactories.register(ModBlockEntities.WHEAT_FARM, WheatFarmBlockEntityRenderer::new);
    }
}
