package com.afkfarms.mod.farm;

import net.minecraft.item.Item;

import java.util.List;
import java.util.Map;

/**
 * Data-driven description of a farm's production behaviour.
 * <p>
 * This is the direct equivalent of one entry in the Bedrock addon's
 * {@code FARM_DATA_TABLE} (scripts/shared/lookup_tables.js): it defines what
 * the farm produces, how fast, how much fuel it costs, and which items can be
 * used to "boost" (temporarily double) its production speed.
 * <p>
 * Every farm block in the mod (wheat, carrot, cobblestone, mob farms, ...)
 * is backed by one {@link FarmType} instance. Only "id" needs to be unique;
 * everything else can be freely tuned to match the original addon's balance.
 */
public record FarmType(
        String id,
        List<FarmResource> resources,
        int xpGain,
        int fuelCostPerItem,
        double secondsRate,
        Map<Item, Integer> boostTable
) {
    /** Effective seconds-per-item, halved while the farm has an active boost. */
    public double productionRate(boolean boosted) {
        return secondsRate * (boosted ? 0.5 : 1.0);
    }
}
