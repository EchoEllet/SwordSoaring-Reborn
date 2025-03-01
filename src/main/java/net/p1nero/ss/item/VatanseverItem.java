package net.p1nero.ss.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.item.WeaponItem;

public class VatanseverItem extends WeaponItem {
    public VatanseverItem(Tier tier, int damageIn, float speedIn, Properties builder) {
        super(tier, damageIn, speedIn, builder);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            if (pPlayer.onGround && !pPlayer.isFallFlying() && !pPlayer.isInWater() && !pPlayer.hasEffect(MobEffects.LEVITATION)) {
                ServerPlayerPatch serverPlayerPatch = EpicFightCapabilities.getEntityPatch(pPlayer, ServerPlayerPatch.class);
                if(serverPlayerPatch.isBattleMode() && !serverPlayerPatch.getEntityState().inaction()){
                    serverPlayerPatch.playAnimationSynchronized(VatanseverAnimations.PLAYER_FLY_BEGIN, 0.15F);
                }
            }
        } else if(!pPlayer.onGround && pPlayer.isFallFlying()){
            Vec3 view = pPlayer.getViewVector(1.0F);
            pPlayer.push(view.x, view.y, view.z);
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.level.isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                entity.gameEvent(GameEvent.ELYTRA_FREE_FALL);
            }
        }
        return true;
    }
}
