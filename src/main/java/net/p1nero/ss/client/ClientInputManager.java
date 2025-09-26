package net.p1nero.ss.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaringConfig;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.client.keymapping.SwordSoaringKeyMappings;
import net.p1nero.ss.gameassets.SwordSoaringSkillCategories;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.mixin.ControlEngineAccessor;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.network.PacketRelay;
import net.p1nero.ss.network.packet.server.RequestVatanseverSwordBackPacket;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.client.CPChangeSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

@Mod.EventBusSubscriber(modid = SwordSoaringMod.MOD_ID, value = {Dist.CLIENT})
public class ClientInputManager {
    public static long lastPressTime;

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton event) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null){
            if(event.getButton() == SwordSoaringKeyMappings.SWITCH_MODE.getKey().getValue()){
                switchModeKeyPressed(event.getAction());
            }
            if(event.getButton() == SwordSoaringKeyMappings.SWORD_BACK.getKey().getValue()){
                swordBackKeyPressed(event.getAction());
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(TickEvent.ClientTickEvent event){
        if(event.phase.equals(TickEvent.Phase.END)){
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            while (SwordSoaringKeyMappings.TAKE_OFF.consumeClick()){
                long currentTime = System.currentTimeMillis();
                if(localPlayer != null && !localPlayer.onGround && currentTime - lastPressTime < SwordSoaringConfig.FLY_DELAY.get()) {
                    sendSkillPacket(SwordSoaringSkillSlots.SWORD_SOARING, SwordSoaringKeyMappings.TAKE_OFF);
                }
                lastPressTime = System.currentTimeMillis();
            }
            while (SwordSoaringKeyMappings.SWORD_SKILL.consumeClick()){
                sendSkillPacket(SwordSoaringSkillSlots.SWORD_CONTROLLER, SwordSoaringKeyMappings.SWORD_SKILL);
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event){
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null){
            if(event.getKey() == SwordSoaringKeyMappings.SWITCH_MODE.getKey().getValue()){
                switchModeKeyPressed(event.getAction());
            }
            if(event.getKey() == SwordSoaringKeyMappings.SWORD_BACK.getKey().getValue()){
                swordBackKeyPressed(event.getAction());
            }
        }
    }

    public static void swordBackKeyPressed(int action){
        if(action == 1){
            PacketRelay.sendToServer(PacketHandler.INSTANCE, new RequestVatanseverSwordBackPacket());
        }
    }

    public static void switchModeKeyPressed(int action){
        if(action == 1){
            LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
            if(localPlayerPatch != null){
                SkillContainer skillContainer = localPlayerPatch.getSkill(SwordSoaringSkillSlots.SWORD_SOARING);
                List<Skill> learnedSkills = localPlayerPatch.getSkillCapability().listAcquiredSkills().filter(skill ->
                    skill.getCategory() == SwordSoaringSkillCategories.SWORD_SOARING).toList();
                if(learnedSkills.isEmpty()){
                    return;
                }
                int index = learnedSkills.indexOf(skillContainer.getSkill());
                int next = (index + 1) % learnedSkills.size();
                Skill nextSkill = learnedSkills.get(next);
                skillContainer.setSkill(nextSkill);
                EpicFightNetworkManager.sendToServer(new CPChangeSkill(SwordSoaringSkillSlots.SWORD_SOARING, -1, false, nextSkill));
                localPlayerPatch.getOriginal().displayClientMessage(Component.translatable("tips.sword_soaring.style_change").append(nextSkill.getDisplayName()), true);
            }
        }
    }

    public static void sendSkillPacket(SkillSlot slot, KeyMapping key){
        LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
        if(localPlayerPatch != null){
            if(localPlayerPatch.getPlayerMode() == PlayerPatch.PlayerMode.EPICFIGHT && localPlayerPatch.getSkill(slot) != null && localPlayerPatch.getSkill(slot).sendCastRequest(localPlayerPatch, ClientEngine.getInstance().controlEngine).shouldReserveKey()){
                ControlEngineAccessor controlEngine = (ControlEngineAccessor) ClientEngine.getInstance().controlEngine;
                controlEngine.setReserveCounter(8);
                controlEngine.setReservedOrHoldingSkillSlot(slot);
                controlEngine.setReservedKey(key);
            }
        }
    }

}