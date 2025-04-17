package net.p1nero.ss.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.wraithon.WraithonEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossBar {
    public static final Map<UUID, Integer> BOSSES = new HashMap<>();//一个BossBar对应一个实体的id，在实体构建的时候发包来设置

    public static boolean renderBossBar(GuiGraphics guiGraphics, LerpingBossEvent bossEvent, int x, int y){
        ResourceLocation frontBarLocation;
        ResourceLocation bgBarLocation;
        Entity boss = null;

        if (BOSSES.isEmpty()) {
            return false;
        }

        if(BOSSES.containsKey(bossEvent.getId()) && Minecraft.getInstance().level != null){
            boss = Minecraft.getInstance().level.getEntity(BOSSES.get(bossEvent.getId()));
        }

        if(boss instanceof WraithonEntity){
            frontBarLocation = ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "textures/gui/bossbar/wraithon_front.png");
            bgBarLocation = ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "textures/gui/bossbar/wraithon_bg.png");
        } else {
            return false;
        }
        drawBar(guiGraphics, x, y, bossEvent, bgBarLocation, frontBarLocation);
        //画名字
//        Component component = bossEvent.getName();
//        Component health = Component.literal(String.format("(%d / %d)", (int)((WraithonEntity) boss).getHealth(), (int)((WraithonEntity) boss).getMaxHealth()));
//        int textWidth = Minecraft.getInstance().font.width(component);
//        int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
//        int textY = y - 9;
//        guiGraphics.drawString(Minecraft.getInstance().font, component, textX, textY, 16777215);
        return true;
    }

    public static void drawBar(GuiGraphics guiGraphics, int x, int y, LerpingBossEvent event, ResourceLocation bgBarLocation, ResourceLocation frontBarLocation) {
        int progress = Mth.lerpInt(event.getProgress(), 0, 256);
        //画背景
        guiGraphics.blit(bgBarLocation, x - 10, y, 256, 64, 0, 1500, 4096, 1024, 4096, 4096);
        //画血量
        if (progress > 0) {
            guiGraphics.blit(frontBarLocation, x - 10, y, progress, 64, 0, 1500, Mth.lerpInt(event.getProgress(),0, 4096), 1024, 4096, 4096);
        }
    }

}
