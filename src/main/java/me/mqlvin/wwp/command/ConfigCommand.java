package me.mqlvin.wwp.command;

import me.mqlvin.wwp.WoolWarsPlus;
import me.mqlvin.wwp.util.ScoreboardUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;

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
    public void processCommand(ICommandSender sender, String[] args) {
        WoolWarsPlus.showGui();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
