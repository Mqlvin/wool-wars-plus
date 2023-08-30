package me.mqlvin.wwp.command;

import me.mqlvin.wwp.WoolWarsPlus;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class ConfigCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "wwp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/wwp";
    }

    @Override
    public List getCommandAliases() {
        List<String> alias = new ArrayList<>();
        alias.add("ww+");
        alias.add("woolwars+");
        alias.add("woolwarsplus");
        return alias;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        WoolWarsPlus.showGui();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
