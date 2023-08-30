package me.mqlvin.wwp.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldTracker {
    private String serverIp = "null";

    private boolean worldPolled = false;
    private boolean awaitingLocraw = false;

    private JsonObject locraw = null;

    // 0xF if invalid/expired. otherwise false 0x0 and true 0x1 are true representatives.
    private byte inGameCache = 0xF;


    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        worldPolled = false;
        awaitingLocraw = false;
        inGameCache = 0xF;

        if(Minecraft.getMinecraft().getCurrentServerData() == null) serverIp = "null";
        else serverIp = Minecraft.getMinecraft().getCurrentServerData().serverIP;

        locraw = null;
    }

    @SubscribeEvent
    public void onFirstChunkLoad(ChunkEvent.Load event) {
        if(!isOnHypixel()) return;

        if(!worldPolled) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");

            worldPolled = true;
            awaitingLocraw = true;
        }
    }

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        if(!isOnHypixel()) return;

        if(awaitingLocraw) {
            try {
                locraw = new JsonParser().parse(event.message.getUnformattedText()).getAsJsonObject();
                event.setCanceled(true);
                awaitingLocraw = false;
            } catch(Exception ignore) {}
        }
    }


    // returns null if expired or not on server
    private JsonObject getLocraw() {
        if(!isOnHypixel()) return null;
        return this.locraw;
    }



    public boolean isOnHypixel() {
        return serverIp.endsWith("hypixel.net") || serverIp.endsWith("hypixel.io");
    }

    public boolean inWoolWarsGame() {
        if(inGameCache == 0x0) return false; // if its cached that: they arent in game - cache resets on world load
        if(getLocraw() == null) return false;

        if(inGameCache == 0xF) { // calculate if in game
            if(getLocraw().has("server") && getLocraw().has("gametype")) {

                if(!getLocraw().get("server").getAsString().contains("lobby") && getLocraw().get("gametype").getAsString().equals("WOOL_GAMES")) inGameCache = 0x1;
                else inGameCache = 0x0;
            }
        }

        return inGameCache == 0x1;
    }






    // {"server":"dynamiclobby25C","gametype":"WOOL_GAMES","lobbyname":"woollobby1"}
    // {"server":"mini1002T","gametype":"WOOL_GAMES","mode":"wool_wars_two_four","map":"Horde"}
}
