package me.mqlvin.wwp.feature;

import me.mqlvin.wwp.WoolWarsPlus;
import me.mqlvin.wwp.util.KillMessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SideCount {
    private int redCount = 4;
    private int blueCount = 4;
    private boolean roundStarted = false;

    private static int renderX = 0;
    private static int renderY = 0;



    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text event) {
        if(WoolWarsPlus.getActiveGame() == null) return;
        if(!roundStarted || !WoolWarsPlus.getConfig().showPlayerCount()) return;

        updateRenderPosition();
        render(redCount, blueCount);
    }


    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(event.type == 2 && event.message.getUnformattedText().startsWith("§e§lCENTER UNLOCKS IN") && !roundStarted) {
            // first time in a round that game started
            roundStarted = true;

            // for accurate info before round start
            WoolWarsPlus.getActiveGame().calculateTeamPlayers();
            resetCount();
            return;
        }

        if(event.type != 0 && !WoolWarsPlus.getWorldTracker().inWoolWarsGame()) return;


        // if round is starting or game ended
        if(event.message.getUnformattedText().startsWith("   ") && event.message.getUnformattedText().contains("Round #")) {
            roundStarted = false;

            // wait for all players to reappear in tab
            WoolWarsPlus.getActiveGame().calculateTeamPlayers();
            resetCount(); // reset count here so it says 4 : 4 or whatever in the cage
        }

        if(!KillMessageUtils.isKillMessage(event.message.getFormattedText())) return;

        if(event.message.getFormattedText().startsWith("§r§c")) redCount -= 1;
        else blueCount -= 1;
    }

    public void resetCount() {
        blueCount = WoolWarsPlus.getActiveGame().getPlayersOnBlue().size();
        redCount = WoolWarsPlus.getActiveGame().getPlayersOnRed().size();
    }



    public void updateRenderPosition() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth("4 : 4") + 2;

        switch(WoolWarsPlus.getConfig().getPlayerCountPos()) {
            case TOP_LEFT: {
                renderX = 10;
                renderY = 10;
                break;
            }
            case TOP_RIGHT: {
                renderX = width - 10 - stringWidth;
                renderY = 10;
                break;
            }
            case TOP_MIDDLE: {
                renderX = width / 2 - stringWidth / 2 + 1;
                renderY = 10;
                break;
            }
            case UNDER_CROSSHAIR: {
                renderX = width / 2 - stringWidth / 2 + 1;
                renderY = height / 2 + 10;
                break;
            }
            case BOTTOM_LEFT: {
                renderX = 10;
                renderY = height - 10 - 7;
                break;
            }
            case BOTTOM_RIGHT: {
                renderX = width - 10 - stringWidth;
                renderY = height - 10 - 7;
                break;
            }
        }
    }


    public void render(int r, int b) {
        String score = EnumChatFormatting.RED.toString() + EnumChatFormatting.BOLD + r + EnumChatFormatting.GRAY + " : " + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + b;

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(score, renderX, renderY, 0xFFFFFFFF);
    }
}
