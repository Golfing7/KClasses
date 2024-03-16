package com.golfing8.kclasses.module;

import com.golfing8.kclasses.module.data.PlayerClassData;
import com.golfing8.kclasses.module.struct.KClass;
import com.golfing8.kcommon.config.lang.LangConf;
import com.golfing8.kcommon.config.lang.Message;
import com.golfing8.kcommon.module.Module;
import com.golfing8.kcommon.module.ModuleInfo;
import lombok.Getter;

import java.util.Set;

/**
 * Module controlling the classes feature.
 */
@ModuleInfo(
        name = "classes"
)
public class ClassesModule extends Module {
    @LangConf @Getter
    private Message reachedLevelMsg = new Message("&aUnlocked level &e{LEVEL}&a!");
    @Override
    public void onEnable() {
        addDataManager("class-data", PlayerClassData.class);
    }

    @Override
    public void onDisable() {

    }

    /**
     * Gets all classes that are defined on the server.
     *
     * @return all classes.
     */
    public Set<KClass> getClasses() {

    }
}
