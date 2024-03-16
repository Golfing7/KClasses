package com.golfing8.kclasses.module.struct;

import com.golfing8.kcommon.config.adapter.CASerializable;
import com.golfing8.kcommon.struct.item.ItemStackBuilder;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * A track of progressing levels that unlock rewards as you go.
 */
@Getter
public class KClass implements CASerializable {
    /** Key of the item in the config */
    private String _key;
    /** The name displayed when referencing this class */
    private String displayName;
    /** The special levels of this class */
    private Map<Integer, KClassLevel> specialLevels;
    /** The max level of this class. All levels can be written as 0, maxLevel inclusive. */
    private int maxLevel;
    /** The initial price of the first level */
    private long initialPrice;
    /** The multiplier to use per-level */
    private double priceMultiplier;
    /** The price to add to each subsequent level */
    private long priceAddend;
    /** The format of the locked icon */
    private ItemStackBuilder lockedIconFormat;
    /** The format of the unlocked icon */
    private ItemStackBuilder unlockedIconFormat;

    /**
     * Gets the price of the given level.
     * <p>
     * Prices are calculated like so:
     * {@code initialPrice * (priceMultiplier ^ (level)) + (priceAddend * (level))}
     * </p>
     *
     * @param level the level.
     * @return the price of the level.
     * @throws IllegalArgumentException if the level is less than 1 or greater than {@link #maxLevel}.
     */
    public long getLevelPrice(int level) {
        Preconditions.checkArgument(0 <= level && level <= maxLevel, "Level must be in range 0, " + maxLevel + " was " + level);

        return (long) (initialPrice * (Math.pow(priceMultiplier, level)) + priceAddend * (level));
    }
}
