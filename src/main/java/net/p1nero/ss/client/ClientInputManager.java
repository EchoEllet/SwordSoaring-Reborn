package net.p1nero.ss.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.client.keymapping.SwordSoaringKeyMappings;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.mixin.ControlEngineAccessor;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillSlot;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID, value = {Dist.CLIENT})
public class ClientInputManager {

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseInputEvent event) {

        if(Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null){
            //重击按下
            if(event.getButton() == SwordSoaringKeyMappings.TAKE_OFF.getKey().getValue()){
                takeOffKeyPressed(event.getAction());
            }

        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event){
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null){
            if(event.getKey() == SwordSoaringKeyMappings.TAKE_OFF.getKey().getValue()){
                takeOffKeyPressed(event.getAction());
            }
        }
    }

    public static void takeOffKeyPressed(int action){
        if(action == 1){
            sendSkillPacket(SwordSoaringSkillSlots.SWORD_SOARING, SwordSoaringKeyMappings.TAKE_OFF);
        }
    }

    public static void sendSkillPacket(SkillSlot slot, KeyMapping key){
        LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
        if(localPlayerPatch != null){
            if(localPlayerPatch.isBattleMode() && localPlayerPatch.getSkill(slot) != null && localPlayerPatch.getSkill(slot).sendExecuteRequest(localPlayerPatch, ClientEngine.getInstance().controllEngine).shouldReserverKey()){
                ControlEngineAccessor controlEngine = (ControlEngineAccessor) ClientEngine.getInstance().controllEngine;
                controlEngine.setReserveCounter(8);
                controlEngine.setReservedOrChargingSkillSlot(slot);
                controlEngine.setReservedKey(key);
            }
        }
    }

}