package com.afkfarms.mod.farm;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;
import java.util.Map;

/**
 * Concrete {@link FarmType} instances, ported field-for-field from the Bedrock addon's
 * FARM_DATA_TABLE (scripts/shared/lookup_tables.js). Only "wheat" is wired up to a block
 * for this first pass; the others can be added the same way once the framework is validated.
 */
public final class FarmTypes {
    private FarmTypes() {
    }

    // secondsRate: 30 -> one resource roll every 30s per fuel unit (halved while boosted).
    // fuelCostPerItem: 1 -> matches original's `fuelCost: 1`.
    public static final FarmType WHEAT = new FarmType(
            "wheat",
            List.of(
                    new FarmResource(Items.WHEAT, 1.0f),
                    new FarmResource(Items.WHEAT_SEEDS, 0.75f)
            ),
            0,
            1,
            30.0,
            Map.of(Items.BONE_MEAL, 80)
    );
}
