package com.afkfarms.mod.client;

import com.afkfarms.mod.client.render.WheatFarmBlockEntityRenderer;
import com.afkfarms.mod.client.render.WheatFarmModel;
import com.afkfarms.mod.registry.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
// This is the vanilla class, not a Fabric API one. Fabric API used to provide a wrapper
// (BlockEntityRendererRegistry) for this, but it was deprecated in favor of just calling the
// vanilla register() method directly - Fabric's "transitive access widener" mechanism makes
// this normally-private method callable from mod code without any wrapper class needed.
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class AfkFarmsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(WheatFarmModel.LAYER, WheatFarmModel::getTexturedModelData);
        BlockEntityRendererFactories.register(ModBlockEntities.WHEAT_FARM, WheatFarmBlockEntityRenderer::new);
    }
}
