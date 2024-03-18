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
import com.golfing8.kcommon.util.SetExpFix;
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
        this.setMaxPage(getMaxPage(kClass.getMaxLevel()));
    }

    @Override
    protected Menu loadMenu(MenuBuilder menuBuilder) {
        int playerLevel = classData.getLevel(kClass.get_key());
        menuBuilder.globalPlaceholders(
                Placeholder.curly("PLAYER_LEVEL", playerLevel + 1),
                Placeholder.curly("CLASS", kClass.getDisplayName()),
                Placeholder.curly("CLASS_NAME", kClass.getDisplayName())
        );

        int levelBasis = getPage() * (getLastSize() - 9);
        for (int i = levelBasis; i < levelBasis + getLastSize() - 9 && i <= kClass.getMaxLevel(); i++) {
            final int level = i;
            ItemStackBuilder levelBuilder = generateLevelItem(i);
            menuBuilder.setAt(i - levelBasis, levelBuilder.buildFromTemplate());
            menuBuilder.addAction(i - levelBasis, (event) -> {
                Player player = (Player) event.getWhoClicked();
                // Check if the player is at the correct level
                if (playerLevel >= level) {
                    module.getAlreadyAchievedLevelMsg().send(player, "LEVEL", level + 1);
                    return;
                }

                if (playerLevel + 1 < level) {
                    module.getCantUnlockYetMsg().send(player, "LEVEL", level + 1);
                    return;
                }

                // Charge the player XP
                int playerXP = SetExpFix.getTotalExperience(player);
                int levelPrice = kClass.getLevelPrice(level, classData.getPrestigeCount());
                if (playerXP < levelPrice) {
                    module.getCantAffordLevelMsg().send(player, "PLAYER_XP", StringUtil.parseCommas(playerXP));
                    return;
                }
                SetExpFix.setTotalExperience(player, playerXP - levelPrice);

                classData.setLevel(kClass.get_key(), level);
                if (kClass.getSpecialLevels().containsKey(level)) {
                    kClass.getSpecialLevels().get(level).getReachedMsg().send(getPlayer());
                } else {
                    module.getReachedLevelMsg().send(getPlayer(), "LEVEL", level + 1);
                }
                ClassUpgradeMenu classUpgradeMenu = new ClassUpgradeMenu(getParentSection(), getPlayer(), kClass);
                classUpgradeMenu.setPage(getPage());
                classUpgradeMenu.open();
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
            builder = classData.getLevel(kClass.get_key()) < level ? module.getLockedIconFormat() : module.getUnlockedIconFormat();
        }
        builder = new ItemStackBuilder(builder);
        builder.addPlaceholders(
                Placeholder.curly("COST", StringUtil.parseCommas(kClass.getLevelPrice(level, classData.getPrestigeCount()))),
                Placeholder.curly("LEVEL", level + 1)
        );
        return builder;
    }
}
