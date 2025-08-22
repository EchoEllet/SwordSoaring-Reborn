package net.p1nero.ss.events;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.p1nero.ss.SwordSoaringMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@EventBusSubscriber(modid = SwordSoaringMod.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event){
        if(event.getEntity() instanceof Player player) {
            EpicFightCapabilities.getUnparameterizedEntityPatch(player, PlayerPatch.class).ifPresent(playerPatch -> {
                playerPatch.getPlayerSkills().fireSkillEvents(SwordSoaringMod.MOD_ID, event);
            });
        }
    }

}