package com.afkfarms.mod.farm;

import net.minecraft.item.Item;

/**
 * A single possible drop produced by a farm, mirroring the Bedrock
 * `collection.resources[]` entries in the original addon's FARM_DATA_TABLE
 * (e.g. { itemId: "minecraft:wheat", chance: 1.0 }).
 */
public record FarmResource(Item item, float chance) {
}
