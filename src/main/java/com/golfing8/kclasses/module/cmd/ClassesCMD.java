package com.golfing8.kclasses.module.cmd;

import com.golfing8.kclasses.module.ClassesModule;
import com.golfing8.kclasses.module.menu.ClassSelectMenu;
import com.golfing8.kcommon.command.CommandContext;
import com.golfing8.kcommon.command.MCommand;
import com.golfing8.kcommon.command.requirement.RequirementPlayer;

/**
 * Opens the class selection menu.
 */
public class ClassesCMD extends MCommand<ClassesModule> {

    @Override
    protected void onRegister() {
        addRequirement(RequirementPlayer.getInstance());
    }

    @Override
    protected void execute(CommandContext context) {
        new ClassSelectMenu(context.getPlayer()).open();
    }
}
