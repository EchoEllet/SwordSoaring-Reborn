package net.p1nero.ss.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.client.gui.TargetIndicator;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(TargetIndicator.class)
public class TargetIndicatorMixin {

    @Inject(method = "shouldDraw", at = @At("HEAD"), cancellable = true)
    private void ss$shouldDraw(LivingEntity entity, LivingEntityPatch<?> entitypatch, LocalPlayerPatch playerpatch, float partialTicks, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof AbstractArtifactSpiritEntity) {
            cir.setReturnValue(false);
        }
    }
}
