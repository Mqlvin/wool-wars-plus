package me.mqlvin.wwp.config;

import me.mqlvin.wwp.WoolWarsPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiConfig extends GuiScreen {
    private static final int distanceTop = 70;

    @Override
    public void initGui() {
        super.initGui();
        int width = Minecraft.getMinecraft().displayWidth;


        GuiButton b_showHolograms = new GuiButton(0x01, width / 4 - 100, distanceTop + 40,
                EnumChatFormatting.WHITE + "Show item holograms: " + (WoolWarsPlus.getConfig().showItemNametags() ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")
        );
        GuiButton b_showHologramDistance = new GuiButton(0x02, width / 4 - 100, distanceTop + 63,
                EnumChatFormatting.WHITE + "Show item distance: " + (WoolWarsPlus.getConfig().showNametagDistance() ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")
        );
        GuiButton b_showSides = new GuiButton(0x03, width / 4 - 100, distanceTop + 95,
                EnumChatFormatting.WHITE + "Show player side count: " + (WoolWarsPlus.getConfig().showPlayerCount() ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")
        );
        GuiButton b_sidesPosition = new GuiButton(0x04, width / 4 - 100, distanceTop + 118,
                EnumChatFormatting.WHITE + "Current side count position: " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + WoolWarsPlus.getConfig().getPlayerCountPos().getFormattedName()
        );

        this.buttonList.add(b_showHolograms);
        this.buttonList.add(b_showHologramDistance);
        this.buttonList.add(b_showSides);
        this.buttonList.add(b_sidesPosition);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int width = Minecraft.getMinecraft().displayWidth;
        int textWidth = fontRendererObj.getStringWidth(EnumChatFormatting.WHITE + EnumChatFormatting.BOLD.toString() + "WoolWars+" + EnumChatFormatting.WHITE + " Config");
        fontRendererObj.drawStringWithShadow(EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD.toString() + "WoolWars+" + EnumChatFormatting.WHITE + " Config", width / 4 - textWidth / 2, distanceTop, 0xFFFFFFFF);

        WoolWarsPlus.getSideCount().render(4, 3);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(button.id == 0x01) {
            WoolWarsPlus.getConfig().toggleShowItemNametags();
            button.displayString = EnumChatFormatting.WHITE + "Show item holograms: " + (WoolWarsPlus.getConfig().showItemNametags() ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
        } else
        if(button.id == 0x02) {
            WoolWarsPlus.getConfig().toggleShowNametagDistance();
            button.displayString = EnumChatFormatting.WHITE + "Show item distance: " + (WoolWarsPlus.getConfig().showNametagDistance() ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
        } else
        if(button.id == 0x03) {
            WoolWarsPlus.getConfig().toggleShowPlayerCount();
            button.displayString = EnumChatFormatting.WHITE + "Show player side count: " + (WoolWarsPlus.getConfig().showPlayerCount() ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
        } else
        if(button.id == 0x04) {
            WoolWarsPlus.getConfig().scrollPlayerCountPos();
            button.displayString = EnumChatFormatting.WHITE + "Side count position: " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + WoolWarsPlus.getConfig().getPlayerCountPos().getFormattedName();
            WoolWarsPlus.getSideCount().updateRenderPosition();
        }

        WoolWarsPlus.getConfig().saveConfig();
    }
}
