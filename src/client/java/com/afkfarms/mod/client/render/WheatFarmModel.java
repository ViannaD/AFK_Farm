package com.afkfarms.mod.client.render;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/**
 * Model for the Wheat Farm block, auto-converted from the original Bedrock
 * geometry "geometry.ldz_241216.farm_wheat" (models/entity/farms/farm_wheat.geo.json).
 *
 * The conversion was generated programmatically from the Bedrock bone/cube tree
 * (root structure, windmill "mill", 6 crop rows each with 4 growth-stage sub-parts,
 * and the iron-bars "cage"). Bedrock's separate, shared "blocks" geometry library
 * (furnace flame, chest lid, and a ~266-cube voxel-art item/digit scoreboard display)
 * was intentionally NOT ported 1:1 - it's cosmetic flourish with no real Java equivalent,
 * and is replaced by simple floating text in the renderer instead. Everything that defines
 * this farm's actual look (the building, the spinning mill, the growing wheat rows, the
 * fence cage) was converted from the real Bedrock geometry.
 */
public class WheatFarmModel extends Model {
    public static final EntityModelLayer LAYER =
            new EntityModelLayer(Identifier.of("afkfarms", "wheat_farm"), "main");

    private final ModelPart root;
    private final ModelPart mill;
    // [row][stage] -> ModelPart, row 0..5, stage 0..3 (stage index = growth stage - 1)
    private final ModelPart[][] cropStages = new ModelPart[6][4];

    public WheatFarmModel(ModelPart root) {
        super(net.minecraft.client.render.RenderLayer::getEntityCutout);
        this.root = root.getChild("root");
        this.mill = this.root.getChild("mill");
        for (int row = 1; row <= 6; row++) {
            ModelPart line = this.root.getChild(row <= 3 ? "line_1" : "line_2");
            ModelPart crop = line.getChild("crop_" + row);
            for (int stage = 1; stage <= 4; stage++) {
                cropStages[row - 1][stage - 1] = crop.getChild("stage_" + stage + "_crop_" + row);
            }
        }
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData p_root = modelData.getRoot();

        // bone: root
        ModelPartBuilder m_root_b = ModelPartBuilder.create();
        m_root_b.uv(0, 0).cuboid(-20.0000f, 0.3269f, -17.4615f, 40.0000f, 9.0000f, 30.0000f, new Dilation(0f));
        ModelPartData m_root = p_root.addChild("root", m_root_b, ModelTransform.of(5.5385f, 6.6731f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_root_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-24.0000f, -3.0000f, -24.0000f, 48.0000f, 6.0000f, 48.0000f, new Dilation(0f));
        m_root.addChild("root_c0", m_root_c0_b, ModelTransform.of(-0.0000f, -3.6731f, 5.5385f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_root_c1_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c1", m_root_c1_b, ModelTransform.of(-16.0000f, -0.4231f, -10.4615f, -1.570796f, 0.000000f, 0.000000f));
        ModelPartBuilder m_root_c2_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c2", m_root_c2_b, ModelTransform.of(-16.0000f, -0.4231f, 5.5385f, -1.570796f, 0.000000f, 0.000000f));
        ModelPartBuilder m_root_c3_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c3", m_root_c3_b, ModelTransform.of(-16.0000f, -0.4231f, 21.5385f, -1.570796f, 0.000000f, 0.000000f));
        ModelPartBuilder m_root_c4_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c4", m_root_c4_b, ModelTransform.of(-0.0000f, -0.4231f, 21.5385f, -1.570796f, 0.000000f, 0.000000f));
        ModelPartBuilder m_root_c5_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c5", m_root_c5_b, ModelTransform.of(-0.0000f, -0.4231f, 5.5385f, -1.570796f, 0.000000f, 0.000000f));
        ModelPartBuilder m_root_c6_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c6", m_root_c6_b, ModelTransform.of(-0.0000f, -0.4231f, -10.4615f, -1.570796f, 0.000000f, 0.000000f));
        ModelPartBuilder m_root_c7_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c7", m_root_c7_b, ModelTransform.of(16.0000f, -0.4231f, 21.5385f, -1.570796f, 0.000000f, 0.000000f));
        ModelPartBuilder m_root_c8_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c8", m_root_c8_b, ModelTransform.of(16.0000f, -0.4231f, 5.5385f, -1.570796f, 0.000000f, 0.000000f));
        ModelPartBuilder m_root_c9_b = ModelPartBuilder.create().uv(256, 0).cuboid(-8.0000f, -8.0000f, 0.0000f, 16.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_root.addChild("root_c9", m_root_c9_b, ModelTransform.of(16.0000f, -0.4231f, -10.4615f, -1.570796f, 0.000000f, 0.000000f));
        // bone: mill
        ModelPartBuilder m_mill_b = ModelPartBuilder.create();
        ModelPartData m_mill = m_root.addChild("mill", m_mill_b, ModelTransform.of(-0.0000f, 6.6769f, 19.5685f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_mill_c0_b = ModelPartBuilder.create().uv(0, 155).cuboid(-0.0000f, 11.0000f, -17.0000f, 2.0000f, 2.0000f, 44.0000f, new Dilation(0f));
        m_mill.addChild("mill_c0", m_mill_c0_b, ModelTransform.of(5.0000f, -12.0000f, -1.0300f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c1_b = ModelPartBuilder.create().uv(126, 264).cuboid(-4.0000f, 7.0000f, 25.0000f, 10.0000f, 10.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c1", m_mill_c1_b, ModelTransform.of(46.0000f, -12.0000f, -1.0300f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c2_b = ModelPartBuilder.create().uv(126, 264).cuboid(-4.0000f, 7.0000f, 25.0000f, 10.0000f, 10.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c2", m_mill_c2_b, ModelTransform.of(39.0000f, -12.0000f, -1.0300f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c3_b = ModelPartBuilder.create().uv(126, 264).cuboid(-4.0000f, 7.0000f, 25.0000f, 10.0000f, 10.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c3", m_mill_c3_b, ModelTransform.of(31.0000f, -12.0000f, -1.0300f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c4_b = ModelPartBuilder.create().uv(126, 264).cuboid(-6.0000f, 7.0000f, 25.0000f, 10.0000f, 10.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c4", m_mill_c4_b, ModelTransform.of(-46.0000f, -12.0000f, -1.0300f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c5_b = ModelPartBuilder.create().uv(126, 264).cuboid(-6.0000f, 7.0000f, 25.0000f, 10.0000f, 10.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c5", m_mill_c5_b, ModelTransform.of(-39.0000f, -12.0000f, -1.0300f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c6_b = ModelPartBuilder.create().uv(126, 264).cuboid(-6.0000f, 7.0000f, 25.0000f, 10.0000f, 10.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c6", m_mill_c6_b, ModelTransform.of(-31.0000f, -12.0000f, -1.0300f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c7_b = ModelPartBuilder.create().uv(164, 99).cuboid(3.0000f, 14.0000f, 25.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c7", m_mill_c7_b, ModelTransform.of(46.0000f, -10.0000f, -8.0300f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c8_b = ModelPartBuilder.create().uv(164, 99).cuboid(3.0000f, 14.0000f, 25.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c8", m_mill_c8_b, ModelTransform.of(39.0000f, -10.0000f, -8.0300f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c9_b = ModelPartBuilder.create().uv(164, 99).cuboid(3.0000f, 14.0000f, 25.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c9", m_mill_c9_b, ModelTransform.of(31.0000f, -10.0000f, -8.0300f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c10_b = ModelPartBuilder.create().uv(164, 99).cuboid(-6.0000f, 14.0000f, 25.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c10", m_mill_c10_b, ModelTransform.of(-46.0000f, -10.0000f, -8.0300f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c11_b = ModelPartBuilder.create().uv(164, 99).cuboid(-6.0000f, 14.0000f, 25.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c11", m_mill_c11_b, ModelTransform.of(-39.0000f, -10.0000f, -8.0300f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c12_b = ModelPartBuilder.create().uv(164, 99).cuboid(-6.0000f, 14.0000f, 25.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c12", m_mill_c12_b, ModelTransform.of(-31.0000f, -10.0000f, -8.0300f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_mill_c13_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -6.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c13", m_mill_c13_b, ModelTransform.of(-0.0000f, -2.5000f, 5.4700f, -1.570796f, 0.000000f, -1.570796f));
        ModelPartBuilder m_mill_c14_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -14.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c14", m_mill_c14_b, ModelTransform.of(-0.0000f, -2.5000f, 5.4700f, -1.570796f, 0.000000f, -1.570796f));
        ModelPartBuilder m_mill_c15_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -22.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c15", m_mill_c15_b, ModelTransform.of(1.0000f, -2.5000f, 5.4700f, -1.570796f, 0.000000f, -1.570796f));
        ModelPartBuilder m_mill_c16_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -6.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c16", m_mill_c16_b, ModelTransform.of(-0.0000f, -2.5000f, 5.4700f, -1.570796f, 0.000000f, 1.570796f));
        ModelPartBuilder m_mill_c17_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -14.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c17", m_mill_c17_b, ModelTransform.of(-0.0000f, -2.5000f, 5.4700f, -1.570796f, 0.000000f, 1.570796f));
        ModelPartBuilder m_mill_c18_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -22.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c18", m_mill_c18_b, ModelTransform.of(-1.0000f, -2.5000f, 5.4700f, -1.570796f, 0.000000f, 1.570796f));
        ModelPartBuilder m_mill_c19_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -6.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c19", m_mill_c19_b, ModelTransform.of(-0.0000f, -5.5000f, -2.5300f, 0.000000f, -1.570796f, -3.141593f));
        ModelPartBuilder m_mill_c20_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -14.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c20", m_mill_c20_b, ModelTransform.of(-0.0000f, -5.5000f, -2.5300f, 0.000000f, -1.570796f, -3.141593f));
        ModelPartBuilder m_mill_c21_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -22.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c21", m_mill_c21_b, ModelTransform.of(1.0000f, -5.5000f, -2.5300f, 0.000000f, -1.570796f, -3.141593f));
        ModelPartBuilder m_mill_c22_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -6.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c22", m_mill_c22_b, ModelTransform.of(-0.0000f, -5.5000f, -2.5300f, 0.000000f, 1.570796f, 3.141593f));
        ModelPartBuilder m_mill_c23_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -14.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c23", m_mill_c23_b, ModelTransform.of(-0.0000f, -5.5000f, -2.5300f, 0.000000f, 1.570796f, 3.141593f));
        ModelPartBuilder m_mill_c24_b = ModelPartBuilder.create().uv(164, 99).cuboid(-1.5000f, -1.5000f, -22.0000f, 3.0000f, 3.0000f, 2.0000f, new Dilation(0f));
        m_mill.addChild("mill_c24", m_mill_c24_b, ModelTransform.of(-1.0000f, -5.5000f, -2.5300f, 0.000000f, 1.570796f, 3.141593f));
        // bone: line_1
        ModelPartBuilder m_line_1_b = ModelPartBuilder.create();
        ModelPartData m_line_1 = m_root.addChild("line_1", m_line_1_b, ModelTransform.of(16.2500f, 3.3269f, -10.9615f, 0.000000f, 0.000000f, 0.000000f));
        // bone: crop_1
        ModelPartBuilder m_crop_1_b = ModelPartBuilder.create();
        ModelPartData m_crop_1 = m_line_1.addChild("crop_1", m_crop_1_b, ModelTransform.of(-1.7500f, 1.0000f, 1.5000f, 0.000000f, 0.000000f, 0.000000f));
        // bone: stage_1_crop_1
        ModelPartBuilder m_stage_1_crop_1_b = ModelPartBuilder.create();
        m_stage_1_crop_1_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_1_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_1_crop_1 = m_crop_1.addChild("stage_1_crop_1", m_stage_1_crop_1_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_1_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_1.addChild("stage_1_crop_1_c0", m_stage_1_crop_1_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_1_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_1.addChild("stage_1_crop_1_c1", m_stage_1_crop_1_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_2_crop_1
        ModelPartBuilder m_stage_2_crop_1_b = ModelPartBuilder.create();
        m_stage_2_crop_1_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_1_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_2_crop_1 = m_crop_1.addChild("stage_2_crop_1", m_stage_2_crop_1_b, ModelTransform.of(-0.3000f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_1_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_1.addChild("stage_2_crop_1_c0", m_stage_2_crop_1_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_1_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_1.addChild("stage_2_crop_1_c1", m_stage_2_crop_1_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_3_crop_1
        ModelPartBuilder m_stage_3_crop_1_b = ModelPartBuilder.create();
        m_stage_3_crop_1_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_1_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_3_crop_1 = m_crop_1.addChild("stage_3_crop_1", m_stage_3_crop_1_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_1_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_1.addChild("stage_3_crop_1_c0", m_stage_3_crop_1_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_1_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_1.addChild("stage_3_crop_1_c1", m_stage_3_crop_1_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: stage_4_crop_1
        ModelPartBuilder m_stage_4_crop_1_b = ModelPartBuilder.create();
        m_stage_4_crop_1_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_1_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_4_crop_1 = m_crop_1.addChild("stage_4_crop_1", m_stage_4_crop_1_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_1_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_1.addChild("stage_4_crop_1_c0", m_stage_4_crop_1_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_1_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_1.addChild("stage_4_crop_1_c1", m_stage_4_crop_1_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: crop_2
        ModelPartBuilder m_crop_2_b = ModelPartBuilder.create();
        ModelPartData m_crop_2 = m_line_1.addChild("crop_2", m_crop_2_b, ModelTransform.of(-15.9750f, 1.0000f, 1.5000f, 0.000000f, 0.000000f, 0.000000f));
        // bone: stage_1_crop_2
        ModelPartBuilder m_stage_1_crop_2_b = ModelPartBuilder.create();
        m_stage_1_crop_2_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_2_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_1_crop_2 = m_crop_2.addChild("stage_1_crop_2", m_stage_1_crop_2_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_2_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_2.addChild("stage_1_crop_2_c0", m_stage_1_crop_2_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_2_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_2.addChild("stage_1_crop_2_c1", m_stage_1_crop_2_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_2_crop_2
        ModelPartBuilder m_stage_2_crop_2_b = ModelPartBuilder.create();
        m_stage_2_crop_2_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_2_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_2_crop_2 = m_crop_2.addChild("stage_2_crop_2", m_stage_2_crop_2_b, ModelTransform.of(-0.3000f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_2_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_2.addChild("stage_2_crop_2_c0", m_stage_2_crop_2_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_2_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_2.addChild("stage_2_crop_2_c1", m_stage_2_crop_2_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_3_crop_2
        ModelPartBuilder m_stage_3_crop_2_b = ModelPartBuilder.create();
        m_stage_3_crop_2_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_2_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_3_crop_2 = m_crop_2.addChild("stage_3_crop_2", m_stage_3_crop_2_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_2_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_2.addChild("stage_3_crop_2_c0", m_stage_3_crop_2_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_2_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_2.addChild("stage_3_crop_2_c1", m_stage_3_crop_2_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: stage_4_crop_2
        ModelPartBuilder m_stage_4_crop_2_b = ModelPartBuilder.create();
        m_stage_4_crop_2_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_2_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_4_crop_2 = m_crop_2.addChild("stage_4_crop_2", m_stage_4_crop_2_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_2_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_2.addChild("stage_4_crop_2_c0", m_stage_4_crop_2_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_2_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_2.addChild("stage_4_crop_2_c1", m_stage_4_crop_2_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: crop_3
        ModelPartBuilder m_crop_3_b = ModelPartBuilder.create();
        ModelPartData m_crop_3 = m_line_1.addChild("crop_3", m_crop_3_b, ModelTransform.of(-30.7500f, 1.0000f, 1.5000f, 0.000000f, 0.000000f, 0.000000f));
        // bone: stage_1_crop_3
        ModelPartBuilder m_stage_1_crop_3_b = ModelPartBuilder.create();
        m_stage_1_crop_3_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_3_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_1_crop_3 = m_crop_3.addChild("stage_1_crop_3", m_stage_1_crop_3_b, ModelTransform.of(0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_3_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_3.addChild("stage_1_crop_3_c0", m_stage_1_crop_3_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_3_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_3.addChild("stage_1_crop_3_c1", m_stage_1_crop_3_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: stage_2_crop_3
        ModelPartBuilder m_stage_2_crop_3_b = ModelPartBuilder.create();
        m_stage_2_crop_3_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_3_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_2_crop_3 = m_crop_3.addChild("stage_2_crop_3", m_stage_2_crop_3_b, ModelTransform.of(0.3000f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_3_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_3.addChild("stage_2_crop_3_c0", m_stage_2_crop_3_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_3_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_3.addChild("stage_2_crop_3_c1", m_stage_2_crop_3_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: stage_3_crop_3
        ModelPartBuilder m_stage_3_crop_3_b = ModelPartBuilder.create();
        m_stage_3_crop_3_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_3_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_3_crop_3 = m_crop_3.addChild("stage_3_crop_3", m_stage_3_crop_3_b, ModelTransform.of(0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_3_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_3.addChild("stage_3_crop_3_c0", m_stage_3_crop_3_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_3_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_3.addChild("stage_3_crop_3_c1", m_stage_3_crop_3_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_4_crop_3
        ModelPartBuilder m_stage_4_crop_3_b = ModelPartBuilder.create();
        m_stage_4_crop_3_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_3_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_4_crop_3 = m_crop_3.addChild("stage_4_crop_3", m_stage_4_crop_3_b, ModelTransform.of(0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_3_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_3.addChild("stage_4_crop_3_c0", m_stage_4_crop_3_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_3_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_3.addChild("stage_4_crop_3_c1", m_stage_4_crop_3_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: line_2
        ModelPartBuilder m_line_2_b = ModelPartBuilder.create();
        ModelPartData m_line_2 = m_root.addChild("line_2", m_line_2_b, ModelTransform.of(16.2500f, 3.3269f, 3.5385f, 0.000000f, 0.000000f, 0.000000f));
        // bone: crop_4
        ModelPartBuilder m_crop_4_b = ModelPartBuilder.create();
        ModelPartData m_crop_4 = m_line_2.addChild("crop_4", m_crop_4_b, ModelTransform.of(-1.7500f, 1.0000f, 1.5000f, 0.000000f, 0.000000f, 0.000000f));
        // bone: stage_1_crop_4
        ModelPartBuilder m_stage_1_crop_4_b = ModelPartBuilder.create();
        m_stage_1_crop_4_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_4_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_1_crop_4 = m_crop_4.addChild("stage_1_crop_4", m_stage_1_crop_4_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_4_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_4.addChild("stage_1_crop_4_c0", m_stage_1_crop_4_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_4_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_4.addChild("stage_1_crop_4_c1", m_stage_1_crop_4_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_2_crop_4
        ModelPartBuilder m_stage_2_crop_4_b = ModelPartBuilder.create();
        m_stage_2_crop_4_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_4_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_2_crop_4 = m_crop_4.addChild("stage_2_crop_4", m_stage_2_crop_4_b, ModelTransform.of(-0.3000f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_4_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_4.addChild("stage_2_crop_4_c0", m_stage_2_crop_4_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_4_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_4.addChild("stage_2_crop_4_c1", m_stage_2_crop_4_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_3_crop_4
        ModelPartBuilder m_stage_3_crop_4_b = ModelPartBuilder.create();
        m_stage_3_crop_4_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_4_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_3_crop_4 = m_crop_4.addChild("stage_3_crop_4", m_stage_3_crop_4_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_4_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_4.addChild("stage_3_crop_4_c0", m_stage_3_crop_4_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_4_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_4.addChild("stage_3_crop_4_c1", m_stage_3_crop_4_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: stage_4_crop_4
        ModelPartBuilder m_stage_4_crop_4_b = ModelPartBuilder.create();
        m_stage_4_crop_4_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_4_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_4_crop_4 = m_crop_4.addChild("stage_4_crop_4", m_stage_4_crop_4_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_4_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_4.addChild("stage_4_crop_4_c0", m_stage_4_crop_4_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_4_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_4.addChild("stage_4_crop_4_c1", m_stage_4_crop_4_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: crop_5
        ModelPartBuilder m_crop_5_b = ModelPartBuilder.create();
        ModelPartData m_crop_5 = m_line_2.addChild("crop_5", m_crop_5_b, ModelTransform.of(-15.9750f, 1.0000f, 1.5000f, 0.000000f, 0.000000f, 0.000000f));
        // bone: stage_1_crop_5
        ModelPartBuilder m_stage_1_crop_5_b = ModelPartBuilder.create();
        m_stage_1_crop_5_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_5_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_1_crop_5 = m_crop_5.addChild("stage_1_crop_5", m_stage_1_crop_5_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_5_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_5.addChild("stage_1_crop_5_c0", m_stage_1_crop_5_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_5_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_5.addChild("stage_1_crop_5_c1", m_stage_1_crop_5_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_2_crop_5
        ModelPartBuilder m_stage_2_crop_5_b = ModelPartBuilder.create();
        m_stage_2_crop_5_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_5_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_2_crop_5 = m_crop_5.addChild("stage_2_crop_5", m_stage_2_crop_5_b, ModelTransform.of(-0.3000f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_5_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_5.addChild("stage_2_crop_5_c0", m_stage_2_crop_5_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_5_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_5.addChild("stage_2_crop_5_c1", m_stage_2_crop_5_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_3_crop_5
        ModelPartBuilder m_stage_3_crop_5_b = ModelPartBuilder.create();
        m_stage_3_crop_5_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_5_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_3_crop_5 = m_crop_5.addChild("stage_3_crop_5", m_stage_3_crop_5_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_5_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_5.addChild("stage_3_crop_5_c0", m_stage_3_crop_5_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_5_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_5.addChild("stage_3_crop_5_c1", m_stage_3_crop_5_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: stage_4_crop_5
        ModelPartBuilder m_stage_4_crop_5_b = ModelPartBuilder.create();
        m_stage_4_crop_5_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_5_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_4_crop_5 = m_crop_5.addChild("stage_4_crop_5", m_stage_4_crop_5_b, ModelTransform.of(-0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_5_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_5.addChild("stage_4_crop_5_c0", m_stage_4_crop_5_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_5_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_5.addChild("stage_4_crop_5_c1", m_stage_4_crop_5_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: crop_6
        ModelPartBuilder m_crop_6_b = ModelPartBuilder.create();
        ModelPartData m_crop_6 = m_line_2.addChild("crop_6", m_crop_6_b, ModelTransform.of(-30.7500f, 1.0000f, 1.5000f, 0.000000f, 0.000000f, 0.000000f));
        // bone: stage_1_crop_6
        ModelPartBuilder m_stage_1_crop_6_b = ModelPartBuilder.create();
        m_stage_1_crop_6_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_6_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_1_crop_6 = m_crop_6.addChild("stage_1_crop_6", m_stage_1_crop_6_b, ModelTransform.of(0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_6_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_6.addChild("stage_1_crop_6_c0", m_stage_1_crop_6_c0_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_1_crop_6_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_1_crop_6.addChild("stage_1_crop_6_c1", m_stage_1_crop_6_c1_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: stage_2_crop_6
        ModelPartBuilder m_stage_2_crop_6_b = ModelPartBuilder.create();
        m_stage_2_crop_6_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_6_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_2_crop_6 = m_crop_6.addChild("stage_2_crop_6", m_stage_2_crop_6_b, ModelTransform.of(0.3000f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_6_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_6.addChild("stage_2_crop_6_c0", m_stage_2_crop_6_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_stage_2_crop_6_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_2_crop_6.addChild("stage_2_crop_6_c1", m_stage_2_crop_6_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, 1.570796f, 0.000000f));
        // bone: stage_3_crop_6
        ModelPartBuilder m_stage_3_crop_6_b = ModelPartBuilder.create();
        m_stage_3_crop_6_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_6_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_3_crop_6 = m_crop_6.addChild("stage_3_crop_6", m_stage_3_crop_6_b, ModelTransform.of(0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_6_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_6.addChild("stage_3_crop_6_c0", m_stage_3_crop_6_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_3_crop_6_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_3_crop_6.addChild("stage_3_crop_6_c1", m_stage_3_crop_6_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: stage_4_crop_6
        ModelPartBuilder m_stage_4_crop_6_b = ModelPartBuilder.create();
        m_stage_4_crop_6_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, -4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_6_b.uv(0, 0).cuboid(-6.5000f, -3.7500f, 4.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        ModelPartData m_stage_4_crop_6 = m_crop_6.addChild("stage_4_crop_6", m_stage_4_crop_6_b, ModelTransform.of(0.2500f, -1.0000f, 0.5000f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_6_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_6.addChild("stage_4_crop_6_c0", m_stage_4_crop_6_c0_b, ModelTransform.of(-4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        ModelPartBuilder m_stage_4_crop_6_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(-6.5000f, -8.0000f, 0.0000f, 13.0000f, 16.0000f, 0.0000f, new Dilation(0f));
        m_stage_4_crop_6.addChild("stage_4_crop_6_c1", m_stage_4_crop_6_c1_b, ModelTransform.of(4.0000f, 4.2500f, 0.0000f, 0.000000f, -1.570796f, 0.000000f));
        // bone: cage
        ModelPartBuilder m_cage_b = ModelPartBuilder.create();
        m_cage_b.uv(0, 0).cuboid(-24.0000f, -3.0000f, -24.0000f, 3.0000f, 42.0000f, 3.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(-21.0000f, 36.0000f, -11.5000f, 42.0000f, 3.0000f, 7.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(-24.0000f, 36.0000f, -21.0000f, 3.0000f, 3.0000f, 42.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(-21.5000f, 1.2500f, -21.0000f, 0.0000f, 6.0000f, 42.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(21.5000f, 1.2500f, -21.0000f, 0.0000f, 6.0000f, 42.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(-21.0000f, 36.0000f, -24.0000f, 42.0000f, 3.0000f, 3.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(21.0000f, -3.0000f, -24.0000f, 3.0000f, 42.0000f, 3.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(21.0000f, 36.0000f, -21.0000f, 3.0000f, 3.0000f, 42.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(21.0000f, -3.0000f, 21.0000f, 3.0000f, 42.0000f, 3.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(-21.0000f, 36.0000f, 21.0000f, 42.0000f, 3.0000f, 3.0000f, new Dilation(0f));
        m_cage_b.uv(0, 0).cuboid(-24.0000f, -3.0000f, 21.0000f, 3.0000f, 42.0000f, 3.0000f, new Dilation(0f));
        ModelPartData m_cage = m_root.addChild("cage", m_cage_b, ModelTransform.of(-0.0000f, 2.3269f, 5.5385f, 0.000000f, 0.000000f, 0.000000f));
        ModelPartBuilder m_cage_c0_b = ModelPartBuilder.create().uv(0, 0).cuboid(8.0000f, 31.0000f, 8.0000f, 16.0000f, 8.0000f, 16.0000f, new Dilation(0f));
        m_cage.addChild("cage_c0", m_cage_c0_b, ModelTransform.of(-5.0000f, 0.0000f, 8.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_cage_c1_b = ModelPartBuilder.create().uv(0, 0).cuboid(8.0000f, 31.0000f, 8.0000f, 16.0000f, 8.0000f, 16.0000f, new Dilation(0f));
        m_cage.addChild("cage_c1", m_cage_c1_b, ModelTransform.of(-27.0000f, 0.0000f, 8.0000f, 0.000000f, 1.570796f, 0.000000f));
        ModelPartBuilder m_cage_c2_b = ModelPartBuilder.create().uv(0, 0).cuboid(-1.5000f, -7.0000f, -1.5000f, 3.0000f, 18.0000f, 2.0000f, new Dilation(0f));
        m_cage.addChild("cage_c2", m_cage_c2_b, ModelTransform.of(-17.5000f, 30.0000f, -21.7000f, 0.000000f, 0.000000f, 0.785398f));
        ModelPartBuilder m_cage_c3_b = ModelPartBuilder.create().uv(0, 0).cuboid(-1.5000f, -7.0000f, -1.5000f, 2.0000f, 18.0000f, 3.0000f, new Dilation(0f));
        m_cage.addChild("cage_c3", m_cage_c3_b, ModelTransform.of(-21.7000f, 30.0000f, -17.5000f, -0.785398f, 0.000000f, 0.000000f));
        ModelPartBuilder m_cage_c4_b = ModelPartBuilder.create().uv(0, 0).cuboid(-0.5000f, -7.0000f, -1.5000f, 2.0000f, 18.0000f, 3.0000f, new Dilation(0f));
        m_cage.addChild("cage_c4", m_cage_c4_b, ModelTransform.of(21.7000f, 30.0000f, -17.5000f, -0.785398f, 0.000000f, 0.000000f));
        ModelPartBuilder m_cage_c5_b = ModelPartBuilder.create().uv(0, 0).cuboid(-1.5000f, -7.0000f, -1.5000f, 3.0000f, 18.0000f, 2.0000f, new Dilation(0f));
        m_cage.addChild("cage_c5", m_cage_c5_b, ModelTransform.of(17.5000f, 30.0000f, -21.7000f, 0.000000f, 0.000000f, -0.785398f));
        ModelPartBuilder m_cage_c6_b = ModelPartBuilder.create().uv(0, 0).cuboid(-1.5000f, -7.0000f, -1.5000f, 2.0000f, 18.0000f, 3.0000f, new Dilation(0f));
        m_cage.addChild("cage_c6", m_cage_c6_b, ModelTransform.of(-21.6500f, 30.0000f, 17.5000f, 0.785398f, 0.000000f, 0.000000f));
        ModelPartBuilder m_cage_c7_b = ModelPartBuilder.create().uv(0, 0).cuboid(-1.5000f, -7.0000f, -0.5000f, 3.0000f, 18.0000f, 2.0000f, new Dilation(0f));
        m_cage.addChild("cage_c7", m_cage_c7_b, ModelTransform.of(-17.5000f, 30.0000f, 21.7500f, 0.000000f, 0.000000f, 0.785398f));
        ModelPartBuilder m_cage_c8_b = ModelPartBuilder.create().uv(0, 0).cuboid(-1.5000f, -7.0000f, -0.5000f, 3.0000f, 18.0000f, 2.0000f, new Dilation(0f));
        m_cage.addChild("cage_c8", m_cage_c8_b, ModelTransform.of(17.5000f, 30.0000f, 21.7500f, 0.000000f, 0.000000f, -0.785398f));
        ModelPartBuilder m_cage_c9_b = ModelPartBuilder.create().uv(0, 0).cuboid(-0.5000f, -7.0000f, -1.5000f, 2.0000f, 18.0000f, 3.0000f, new Dilation(0f));
        m_cage.addChild("cage_c9", m_cage_c9_b, ModelTransform.of(21.6500f, 30.0000f, 17.5000f, 0.785398f, 0.000000f, 0.000000f));


        return TexturedModelData.of(modelData, 512, 512);
    }

    /** The static building structure (walls, roof, windows). Always rendered. */
    public ModelPart getRoot() {
        return root;
    }

    /** The rotating windmill blades. Spin this continuously while the farm is active. */
    public ModelPart getMill() {
        return mill;
    }

    /**
     * Growth-stage sub-part for a crop row. Only one stage per row should be
     * set visible at a time (visible = growthStage(row) == stage).
     * @param row 0..5 (six crop rows, matching the original's crop_1..crop_6)
     * @param stage 0..3 (four growth stages, matching stage_1..stage_4)
     */
    public ModelPart getCropStage(int row, int stage) {
        return cropStages[row][stage];
    }

    @Override
    public void render(net.minecraft.client.util.math.MatrixStack matrices,
                        net.minecraft.client.render.VertexConsumer vertices,
                        int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay, color);
    }
}
