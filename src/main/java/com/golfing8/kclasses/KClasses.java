package com.golfing8.kclasses;

import com.golfing8.kcommon.KPlugin;
import lombok.Getter;

/**
 * Main controlling class for the classes system
 */
public class KClasses extends KPlugin {
    @Getter
    private static KClasses instance;

    @Override
    public void onEnableInner() {
        instance = this;
    }
}
