package com.golfing8.kclasses.module.menu;

import com.golfing8.kclasses.module.ClassesModule;
import com.golfing8.kclasses.module.data.PlayerClassData;
import com.golfing8.kclasses.module.struct.KClass;
import com.golfing8.kcommon.menu.Menu;
import com.golfing8.kcommon.menu.MenuBuilder;
import com.golfing8.kcommon.menu.PagedMenuContainer;
import com.golfing8.kcommon.menu.PlayerMenuContainer;
import com.golfing8.kcommon.module.Module;
import com.golfing8.kcommon.struct.placeholder.Placeholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

/**
 * The main selection menu for classes.
 */
public class ClassSelectMenu extends PlayerMenuContainer {
    private final PlayerClassData classData;

    public ClassSelectMenu(Player player) {
        super(player);
        ClassesModule module = ClassesModule.get();
        this.classData = module.getOrCreate(player.getUniqueId(), PlayerClassData.class);
    }

    @Override
    protected Menu loadMenu() {
        ClassesModule module = ClassesModule.get();
        ConfigurationSection section = module.getMainConfig().getConfigurationSection("main-menu");
        MenuBuilder builder = new MenuBuilder(section);
        for (KClass kClass : module.getClasses()) {
            builder.bindTo(kClass.get_key(), (event) -> {
                new ClassUpgradeMenu(module.getMainConfig().getConfigurationSection("upgrade-menu"), getPlayer(), kClass).open();
            });
            builder.specialPlaceholders(kClass.get_key(),
                    Arrays.asList(
                            Placeholder.curly("LEVEL", classData.getLevel(kClass.get_key()) + 1),
                            Placeholder.curly("CLASS_NAME", kClass.getDisplayName())
                    ));
        }
        return builder.buildSimple();
    }
}
