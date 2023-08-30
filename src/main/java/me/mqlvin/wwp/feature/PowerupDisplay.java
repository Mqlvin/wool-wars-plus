package me.mqlvin.wwp.feature;

import me.mqlvin.wwp.WoolWarsPlus;
import me.mqlvin.wwp.util.WaypointUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;

/*
            fx: strength "Strength Buff", speed "Speed Boost", jump "Jump Boost", health "Instant Health", haste "Haste Buff"
            melee: bow "Bow", sword "x Sword", pickaxe "x Pickaxe"
            armour: boots, leggings, chesty, helmet --> "x Boots Leggings "
*/

public class PowerupDisplay {
    public static final HashMap<String, String> POWERUP_FORMAT = new HashMap<String, String>() {{
        put("§l§6Strength Buff", "§4§l⚔ Strength Buff");
        put("§l§6Speed Boost", "§b§l⚡ Speed");
        put("§l§6Jump Boost", "§a§l↟↟ Jump Boost");
        put("§l§6Instant Health", "§c§l✚ Instant Health");
        put("§l§6Haste Buff", "§6§l⚒ Haste Buff");
        put("§l§6Bow", "§e§l☄ Bow");
        put("§l§6Stone Pickaxe", "§7§l⚒ Stone Pickaxe");
        put("§l§6Stone Sword", "§7§l⚒ Stone Sword");
        put("§l§6Iron Boots", "§f§l♦ Iron Boots");
        put("§l§6Chainmail Leggings", "§7§l♦ Chainmail Leggings");
        put("§l§6Chainmail Chestplate", "§7§l♦ Chainmail Chestplate");
        put("§l§6Chainmail Helmet", "§7§l♦ Chainmail Helmet");
    }};
    private static final ArrayList<EntityLivingBase> powerups = new ArrayList<>();

    @SubscribeEvent
    public void nametagRender(RenderLivingEvent.Specials.Pre event) {
        if(!WoolWarsPlus.getConfig().showItemNametags()) return;
        if(!POWERUP_FORMAT.containsKey(event.entity.getName())) return; // if it isn't a coloured hologram, ignore

        powerups.add(event.entity);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.disableTexture2D();
        final boolean showDistance = WoolWarsPlus.getConfig().showNametagDistance();
        powerups.forEach(en -> {
            WaypointUtils.renderWaypointText(POWERUP_FORMAT.get(en.getName()), showDistance, en.posX, en.posY + 3, en.posZ, event.partialTicks);
        });
        GlStateManager.disableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();

        powerups.clear();
    }
}
