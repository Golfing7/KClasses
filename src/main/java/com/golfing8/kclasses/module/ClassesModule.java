package com.golfing8.kclasses.module;

import com.golfing8.kclasses.module.cmd.ClassesCMD;
import com.golfing8.kclasses.module.data.PlayerClassData;
import com.golfing8.kclasses.module.struct.KClass;
import com.golfing8.kcommon.config.generator.Conf;
import com.golfing8.kcommon.config.lang.LangConf;
import com.golfing8.kcommon.config.lang.Message;
import com.golfing8.kcommon.module.Module;
import com.golfing8.kcommon.module.ModuleInfo;
import com.golfing8.kcommon.struct.item.ItemStackBuilder;
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
    @LangConf @Getter
    private Message cantAffordLevelMsg = new Message("&cYou can't afford this level. You only have {PLAYER_XP} xp!");
    @LangConf @Getter
    private Message alreadyAchievedLevelMsg = new Message("&cYou already unlocked that level!");
    @LangConf @Getter
    private Message cantUnlockYetMsg = new Message("&cYou can't unlock that level yet!");

    /** The format of the locked icon */
    @Conf @Getter
    private ItemStackBuilder lockedIconFormat;
    /** The format of the unlocked icon */
    @Conf @Getter
    private ItemStackBuilder unlockedIconFormat;

    /** The cost to prestige for the first time */
    @Conf @Getter
    private int prestigeCost;
    /** The multiplier applied to all costs after each prestige */
    @Conf @Getter
    private double prestigeCostMultiplier;

    @Override
    public void onEnable() {
        addDataManager("class-data", PlayerClassData.class);

        addCommand(new ClassesCMD());
    }

    @Override
    public void onDisable() {

    }

    /**
     * Gets the class from its ID.
     *
     * @param id the id.
     * @return the class.
     */
    public KClass getKClass(String id) {

    }

    /**
     * Gets all classes that are defined on the server.
     *
     * @return all classes.
     */
    public Set<KClass> getClasses() {

    }
}
