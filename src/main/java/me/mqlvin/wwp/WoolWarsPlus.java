package me.mqlvin.wwp;

import me.mqlvin.wwp.command.ConfigCommand;
import me.mqlvin.wwp.config.GuiConfig;
import me.mqlvin.wwp.config.WWPConfig;
import me.mqlvin.wwp.feature.PowerupDisplay;
import me.mqlvin.wwp.feature.SideCount;
import me.mqlvin.wwp.util.ActiveGame;
import me.mqlvin.wwp.util.WorldTracker;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = WoolWarsPlus.MODID, version = WoolWarsPlus.VERSION)
public class WoolWarsPlus {
    public static final String MODID = "wwplus";
    public static final String VERSION = "1.0";


    public static WWPConfig config = new WWPConfig();

    private static final WorldTracker worldTracker = new WorldTracker();

    private static ActiveGame activeGame = null;


    private static final SideCount featureSD = new SideCount();
    private static final PowerupDisplay featurePD = new PowerupDisplay();


    private static int lastScreenWidth = 0;
    private static int lastScreenHeight = 0;
    private static boolean showGui = false;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new ConfigCommand());
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(featurePD);
        MinecraftForge.EVENT_BUS.register(getWorldTracker());
        MinecraftForge.EVENT_BUS.register(featureSD);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOW) // we have to do ClientChatReceivedEvent as we have to wait for the locraw to come in
    public void onPossibleLocraw(ClientChatReceivedEvent event) {
        if(!getWorldTracker().inWoolWarsGame()) {
            try {
                MinecraftForge.EVENT_BUS.unregister(activeGame);
            } catch(Exception ignored) {} // user hadn't played ww yet
            activeGame = null;
        } else {
            activeGame = new ActiveGame();
            MinecraftForge.EVENT_BUS.register(activeGame);
        }
    }

    public static void showGui() {
        showGui = true;
    }

    public static ActiveGame getActiveGame() {
        return activeGame;
    }

    public static WWPConfig getConfig() {
        return config;
    }

    public static WorldTracker getWorldTracker() {
        return worldTracker;
    }

    @SubscribeEvent // checks if screen size has changed
    public void onTickCheckScreenSize(TickEvent.ClientTickEvent e){
        if(showGui) {
            showGui = false;
            Minecraft.getMinecraft().displayGuiScreen(new GuiConfig());
        }

        if(lastScreenHeight != Minecraft.getMinecraft().displayHeight || lastScreenWidth != Minecraft.getMinecraft().displayWidth) {
            featureSD.updateRenderPosition();
        }
        lastScreenWidth = Minecraft.getMinecraft().displayWidth;
        lastScreenHeight = Minecraft.getMinecraft().displayHeight;
    }



    public static SideCount getSideCount() {
        return featureSD;
    }
}
