package com.afkfarms.mod.client.render;

import com.afkfarms.mod.block.entity.FarmBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

/**
 * Draws the wheat farm block using the geometry converted from the original Bedrock model
 * (see {@link WheatFarmModel}) and reproduces its two "always-on" animation behaviours:
 * <ul>
 *   <li>the windmill ("mill" part) spins continuously while the farm is active, matching
 *       the looping {@code animation.ldz_241216.farm_wheat.idle.active} clip;</li>
 *   <li>each of the 6 crop rows cycles through its 4 growth-stage sub-parts over time,
 *       offset from each other, matching the same clip's per-row scale keyframes.</li>
 * </ul>
 * The original's floating pixel-art item icon / digit scoreboard system (the shared
 * "blocks" geometry, ~266 extra cubes) is intentionally NOT reproduced 1:1 - it's replaced
 * here with plain floating text showing the fuel timer, which is far simpler in Java and
 * shows the same information.
 */
public class WheatFarmBlockEntityRenderer implements BlockEntityRenderer<FarmBlockEntity> {
    private static final Identifier TEXTURE = Identifier.of("afkfarms", "textures/entity/farms/farm_wheat.png");

    private final WheatFarmModel model;

    public WheatFarmBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new WheatFarmModel(ctx.getLayerModelPart(WheatFarmModel.LAYER));
    }

    @Override
    public void render(FarmBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                        int light, int overlay) {
        matrices.push();
        matrices.translate(0.5, 0.0, 0.5);

        BlockState state = entity.getCachedState();
        Direction facing = state.contains(com.afkfarms.mod.block.FarmBlock.FACING)
                ? state.get(com.afkfarms.mod.block.FarmBlock.FACING) : Direction.NORTH;
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-facing.asRotation()));

        boolean active = entity.isActive();
        long age = entity.getWorld() != null ? entity.getWorld().getTime() : 0L;
        float animTime = age + tickDelta;

        // Windmill: one full spin every ~4 seconds (matches the original clip length of 3.96s) while active.
        float millSpeed = active ? (360f / (4f * 20f)) : 0f;
        model.getMill().pitch = 0; // keep axis-locked; original spins around a horizontal axis baked into the bone pivot
        model.getMill().roll = (animTime * millSpeed) * ((float) Math.PI / 180f);

        // Crops: cycle through 4 growth stages every 4 seconds per row, rows gently offset.
        for (int row = 0; row < 6; row++) {
            int stageCount = 4;
            float rowOffset = row * 3f; // ticks offset between rows for a "wave" look
            int stage = active
                    ? (int) (((animTime + rowOffset) / 20f) % stageCount)
                    : 0; // dormant: only the smallest stage shows, matching idle.inactive
            for (int s = 0; s < stageCount; s++) {
                model.getCropStage(row, s).visible = active ? (s == stage) : (s == 0);
            }
        }

        var buffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        model.getRoot().render(matrices, buffer, light, overlay);

        matrices.pop();

        if (entity.isTimerVisible()) {
            renderFuelText(entity, matrices, vertexConsumers, light);
        }
    }

    private void renderFuelText(FarmBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Note: fixed-orientation (doesn't billboard to face the camera) - a reasonable
        // simplification of the original's always-facing digit scoreboard bones.
        matrices.push();
        matrices.translate(0.5, 1.8, 0.5);
        matrices.scale(-0.02f, -0.02f, 0.02f);
        TextRenderer textRenderer = net.minecraft.client.MinecraftClient.getInstance().textRenderer;
        String label = entity.isActive() ? entity.getFuelText() : Text.translatable("afkfarms.farm.out_of_fuel").getString();
        float x = -textRenderer.getWidth(label) / 2f;
        textRenderer.draw(label, x, 0, 0xFFFFFF, false, matrices.peek().getPositionMatrix(),
                vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, light);
        matrices.pop();
    }
}
