package net.p1nero.ss.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;
import net.p1nero.ss.entity.wraithon.WraithonEntity;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class GGEventHandler {
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        if (stack.getItem() == SwordSoaringItems.MERLIN_GG.get()) {
            if (!player.level().isClientSide) {
                List<Entity> entities = player.level().getEntities(player,
                        player.getBoundingBox().inflate(50.0D));

                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity && entity != player) {

                        if (entity != null) {
                            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, WraithonEntityPatch.class);
                            if (livingEntityPatch != null){
                                livingEntityPatch.playAnimationSynchronized(WraithonAnimations.WRAITHON_1,0.0001F);
                            }
                        }
                    }
                }

                player.getCooldowns().addCooldown(stack.getItem(), 20);
            }
        }
    }
}
