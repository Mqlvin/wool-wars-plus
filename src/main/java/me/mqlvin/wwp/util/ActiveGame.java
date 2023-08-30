package me.mqlvin.wwp.util;

import com.google.common.collect.Ordering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents an active game of wool wars.
 */

public class ActiveGame {
    private static final String redTeamPrefix = "§c§lR §r§c";
    private static final String blueTeamPrefix = "§9§lB §r§9";

    private final List<String> playersOnRed;
    private final List<String> playersOnBlue;

    public ActiveGame() {
        this.playersOnRed = new ArrayList<>();
        this.playersOnBlue = new ArrayList<>();
    }




    public void calculateTeamPlayers() {
        if(Minecraft.getMinecraft().ingameGUI == null) return;
        try {
            Ordering<NetworkPlayerInfo> f = ReflectionHelper.getPrivateValue(GuiPlayerTabOverlay.class, Minecraft.getMinecraft().ingameGUI.getTabList(),"field_175252_a");
            List<NetworkPlayerInfo> list = f.sortedCopy(Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap());
            list.forEach(e -> {
                if(e.getPlayerTeam().getColorPrefix().equals(redTeamPrefix)) playersOnRed.add(e.getGameProfile().getName());
                if(e.getPlayerTeam().getColorPrefix().equals(blueTeamPrefix)) playersOnBlue.add(e.getGameProfile().getName());
            });
        } catch(Exception e) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(
                    EnumChatFormatting.WHITE.toString() + EnumChatFormatting.BOLD + "WW+" +
                            EnumChatFormatting.GRAY + " | " +
                            EnumChatFormatting.RED + "Error parsing player tab list (" + e.getMessage() + ")"
            ));
        }
    }

    public List<String> getPlayersOnRed() {
        return playersOnRed;
    }

    public List<String> getPlayersOnBlue() {
        return playersOnBlue;
    }
}
