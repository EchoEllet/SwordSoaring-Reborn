package net.p1nero.ss.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.wraithon.WraithonEntity;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class MerlinSuperGG extends SwordItem {
    public MerlinSuperGG(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player player, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = player.getItemInHand(pUsedHand);
        Level world = player.level();

        if (stack.getItem() == SwordSoaringItems.MERLIN_GG.get() && !world.isClientSide) {
            List<WraithonEntity> existingWraithons = world.getEntitiesOfClass(
                    WraithonEntity.class,
                    player.getBoundingBox().inflate(50.0D)
            );
            for (WraithonEntity wraithon : existingWraithons) {
                wraithon.discard();
            }
            Vec3 spawnPos = new Vec3(-1.1, -59, -12);
            WraithonEntity newWraithon = new WraithonEntity(
                    SwordSoaringEntities.WRAITHON.get(),
                    world
            );
            newWraithon.moveTo(
                    spawnPos.x(),
                    spawnPos.y(),
                    spawnPos.z(),
                    0,
                    newWraithon.getXRot()
            );
            world.addFreshEntity(newWraithon);

            LivingEntityPatch<WraithonEntity> entityPatch = EpicFightCapabilities.getEntityPatch(
                    newWraithon,
                    WraithonEntityPatch.class
            );
            if (entityPatch != null) {
                entityPatch.playAnimation(
                        WraithonAnimations.WRAITHON_3,
                        0.0F
                );
            }
            player.getCooldowns().addCooldown(stack.getItem(), 20);
        }
        return super.use(pLevel, player, pUsedHand);
    }
}
