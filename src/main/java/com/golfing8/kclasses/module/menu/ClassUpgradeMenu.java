package com.golfing8.kclasses.module.menu;

import com.golfing8.kclasses.module.ClassesModule;
import com.golfing8.kclasses.module.data.PlayerClassData;
import com.golfing8.kclasses.module.struct.KClass;
import com.golfing8.kclasses.module.struct.KClassLevel;
import com.golfing8.kcommon.menu.Menu;
import com.golfing8.kcommon.menu.MenuBuilder;
import com.golfing8.kcommon.menu.PagedMenuContainer;
import com.golfing8.kcommon.struct.item.ItemStackBuilder;
import com.golfing8.kcommon.struct.placeholder.Placeholder;
import com.golfing8.kcommon.util.PlayerUtil;
import com.golfing8.kcommon.util.StringUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Allows players to upgrade their class. Upgrades cost XP points.
 */
public class ClassUpgradeMenu extends PagedMenuContainer {
    private final KClass kClass;
    private final PlayerClassData classData;
    private final ClassesModule module;

    public ClassUpgradeMenu(ConfigurationSection section, Player player, KClass kClass) {
        super(section, player);
        this.kClass = kClass;
        this.module = ClassesModule.get();
        this.classData = module.getOrCreate(player.getUniqueId(), PlayerClassData.class);
    }

    @Override
    protected Menu loadMenu(MenuBuilder menuBuilder) {
        menuBuilder.globalPlaceholders(
                Placeholder.curly("PLAYER_LEVEL", classData.getLevel(kClass.get_key())),
                Placeholder.curly("CLASS", kClass.getDisplayName())
        );

        int levelBasis = getPage() * (getLastSize() - 9);
        for (int i = levelBasis; i < levelBasis + getLastSize() - 9 && i <= kClass.getMaxLevel(); i++) {
            final int level = i;
            ItemStackBuilder levelBuilder = generateLevelItem(i);
            menuBuilder.setAt(i - levelBasis, levelBuilder.buildFromTemplate());
            menuBuilder.addAction(i - levelBasis, (event) -> {
                //TODO Take XP from player
                classData.setLevel(kClass.get_key(), level);
                if (kClass.getSpecialLevels().containsKey(level)) {
                    kClass.getSpecialLevels().get(level).getReachedMsg().send(getPlayer());
                } else {
                    module.getReachedLevelMsg().send(getPlayer(), "LEVEL", level);
                }
            });
        }
        return menuBuilder.buildSimple();
    }

    /**
     * Generates the level item for the given level.
     *
     * @param level the level.
     * @return the item.
     */
    private ItemStackBuilder generateLevelItem(int level) {
        ItemStackBuilder builder;
        if (kClass.getSpecialLevels().containsKey(level)) {
            KClassLevel classLevel = kClass.getSpecialLevels().get(level);
            builder = classData.getLevel(kClass.get_key()) < level ? classLevel.getLockedIcon() : classLevel.getUnlockedIcon();
        } else {
            builder = classData.getLevel(kClass.get_key()) < level ? kClass.getLockedIconFormat() : kClass.getUnlockedIconFormat();
        }
        builder.addPlaceholders(
                Placeholder.curly("PRICE", StringUtil.parseCommas(kClass.getLevelPrice(level))),
                Placeholder.curly("LEVEL", level)
        );
        return builder;
    }
}
