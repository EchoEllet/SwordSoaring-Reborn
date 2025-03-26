package net.p1nero.ss.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.ray.RayEntity;
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
        Level world = player.level();

        if (stack.getItem() == SwordSoaringItems.MERLIN_GG.get() && !world.isClientSide) {
            // 清除周围现有的 WraithonEntity
            List<WraithonEntity> existingWraithons = world.getEntitiesOfClass(
                    WraithonEntity.class,
                    player.getBoundingBox().inflate(50.0D)
            );

            for (WraithonEntity wraithon : existingWraithons) {
                wraithon.discard(); // 移除实体
            }

            // 生成并定位新的 WraithonEntity
            Vec3 spawnPos = new Vec3(-1.1, -59, -12);
            WraithonEntity newWraithon = new WraithonEntity(
                    SwordSoaringEntities.WRAITHON.get(),
                    world
            );

            newWraithon.moveTo(
                    spawnPos.x(),
                    spawnPos.y(),
                    spawnPos.z(),
                    newWraithon.getYRot(),
                    newWraithon.getXRot()
            );
            world.addFreshEntity(newWraithon);

            // 对新生成的实体播放动画
            LivingEntityPatch<WraithonEntity> entityPatch = EpicFightCapabilities.getEntityPatch(
                    newWraithon,
                    WraithonEntityPatch.class
            );

            if (entityPatch != null) {
                entityPatch.playAnimationSynchronized(
                        WraithonAnimations.WRAITHON_1,
                        0.0001F
                );
            }

            // 设置冷却
            player.getCooldowns().addCooldown(stack.getItem(), 20);
        }
    }
}
