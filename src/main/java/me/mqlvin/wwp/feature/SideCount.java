package me.mqlvin.wwp.feature;

import me.mqlvin.wwp.WoolWarsPlus;
import me.mqlvin.wwp.util.ScoreboardUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.stream.Collectors;

public class SideCount {
    private int redCount = 4;
    private int blueCount = 4;
    private boolean roundActive = false;

    private static int renderX = 0;
    private static int renderY = 0;


    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text event) {
        roundActive = isInGame();
        if (!roundActive || !WoolWarsPlus.getConfig().showPlayerCount()) {
            return;
        }

        updateCount();

        updateRenderPosition();
        render(redCount, blueCount);
    }

    public void updateCount() {
        if (!roundActive) return;
        List<String> sbLines = ScoreboardUtils.getSidebarSuffixes(Minecraft.getMinecraft().theWorld.getScoreboard());

        if (sbLines.size() != 8) {
            return;
        }
        ; // not the right scoreboard or time

        String possibleRedCount = sbLines.stream().filter(s -> s.contains("§c§r")).findFirst().orElse(null);
        String possibleBlueCount = sbLines.stream().filter(s -> s.contains("§9§r")).findFirst().orElse(null);

        if (possibleRedCount == null || possibleBlueCount == null) {
            redCount = 4;
            blueCount = 4;
            return;
        } // if lines aren't there return

        try {
            possibleRedCount = possibleRedCount.substring(4); // removes emoji/colour code
            possibleBlueCount = possibleBlueCount.substring(4);
            redCount = Integer.parseInt(String.valueOf(possibleRedCount.charAt(0))); // there is a trailing space at the end
            blueCount = Integer.parseInt(String.valueOf(possibleBlueCount.charAt(0)));
        } catch (Exception ignore) {
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (event.message.getUnformattedText().startsWith(" ") && event.message.getUnformattedText().contains("Round")) {
            blueCount = 4;
            redCount = 4;
        }
    }


    public void updateRenderPosition() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth("4 : 4") + 2;

        switch (WoolWarsPlus.getConfig().getPlayerCountPos()) {
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

    public boolean isInGame() {
        List<String> sbLines = ScoreboardUtils.getSidebarPrefixes(Minecraft.getMinecraft().theWorld.getScoreboard());
        List<String> info = sbLines.stream().filter(s -> s.contains("⬤") || s.equals("State: §ePre Rou") || s.equals("State: §eActive ")).collect(Collectors.toList());

        return info.size() == 3; // valid state, red/blue team rounds won indicator
    }
}
