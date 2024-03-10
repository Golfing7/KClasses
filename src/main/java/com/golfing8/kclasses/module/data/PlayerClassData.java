package com.golfing8.kclasses.module.data;

import com.golfing8.kcommon.data.SenderSerializable;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player's progression through classes.
 */
public class PlayerClassData extends SenderSerializable {
    /**
     *  Stores the level of a class mapped from the class' ID.
     *  The class ID not being present indicates the player has made no progression.
     */
    private Map<String, Integer> classLevels = new HashMap<>();

    /**
     * Gets the level of the class.
     *
     * @param str the class ID.
     * @return the level.
     */
    public int getLevel(String str) {
        Preconditions.checkNotNull(str, "ID cannot be null");
        return classLevels.getOrDefault(str, 0);
    }

    /**
     * Sets the level of the class.
     *
     * @param str the class ID.
     * @param level the level.
     */
    public void setLevel(String str, int level) {
        Preconditions.checkNotNull(str, "ID cannot be null");
        classLevels.put(str, level);
    }
}
