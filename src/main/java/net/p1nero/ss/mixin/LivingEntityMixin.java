package net.p1nero.ss.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.p1nero.ss.item.VatanseverItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.gameasset.EpicFightSounds;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract boolean hasEffect(MobEffect pEffect);

    @Shadow
    public abstract ItemStack getMainHandItem();

    @Shadow
    public abstract boolean isAlive();

    @Shadow
    protected int fallFlyTicks;

    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "updateFallFlying", at = @At("HEAD"), cancellable = true)
    private void sword_soaring$updateFallFlying(CallbackInfo ci) {
        if (this.getMainHandItem().getItem() instanceof VatanseverItem) {
            boolean flag = this.getSharedFlag(7);
            if (flag && !this.onGround && !this.isPassenger() && !this.hasEffect(MobEffects.LEVITATION)) {
                flag = this.getMainHandItem().elytraFlightTick((LivingEntity) (Object) this, this.fallFlyTicks);
            } else {
                flag = false;
            }

            if (!this.level.isClientSide) {
                this.setSharedFlag(7, flag);
            }
            ci.cancel();
        }
    }

    @Inject(method = "playBlockFallSound", at = @At("HEAD"), cancellable = true)
    private void sword_soaring$playBlockFallSound(CallbackInfo ci) {
        if (this.getMainHandItem().getItem() instanceof VatanseverItem) {
            ci.cancel();
        }
    }

    @Inject(method = "getFallDamageSound", at = @At("HEAD"), cancellable = true)
    private void sword_soaring$getFallDamageSound(int pHeight, CallbackInfoReturnable<SoundEvent> cir) {
        if (this.getMainHandItem().getItem() instanceof VatanseverItem) {
            cir.setReturnValue(EpicFightSounds.NO_SOUND);
        }
    }

}
