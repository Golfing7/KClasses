package com.golfing8.kclasses.module.struct;

import com.golfing8.kcommon.config.adapter.CASerializable;
import com.golfing8.kcommon.config.lang.Message;
import com.golfing8.kcommon.struct.item.ItemStackBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a special level of a class.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KClassLevel implements CASerializable {
    /** Command rewards given to players when they achieve this level */
    private List<String> rewards; //TODO Replace with proper reward library
    /** The message to be sent when the player reaches this level, can be null */
    private Message reachedMsg;
    /** The icon to show to players when this level is locked. */
    private ItemStackBuilder lockedIcon;
    /** The icon to show to players when this level is unlocked. */
    private ItemStackBuilder unlockedIcon;
}
