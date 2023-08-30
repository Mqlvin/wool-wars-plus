package me.mqlvin.wwp.util;

public class KillMessageUtils {
    public static boolean isKillMessage(String msg) {

        return ((msg.startsWith("§r§9") && msg.contains("§r§c")) || (msg.startsWith("§r§c") && msg.contains("§r§9"))) ||           // "Player killed by Player" e.g. melee
                ((msg.startsWith("§r§9") || msg.startsWith("§r§c")) && msg.contains("died."));                                     // "Player died." e.g. walking off
    }
}
