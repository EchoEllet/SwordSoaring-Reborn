package net.p1nero.ss.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.entity.sword.wan.WanEntity;
import net.p1nero.ss.entity.vatansever.VatanseverArmature;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.particle.AbstractTrailParticle;
import yesman.epicfight.client.particle.AnimationTrailParticle;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

@Mixin(value = AnimationTrailParticle.class)
public abstract class TrailParticleMixin<T extends EntityPatch<?>> extends AbstractTrailParticle<T> {

    @Shadow(remap = false) @Final protected Joint joint;

    protected TrailParticleMixin(ClientLevel level, T entitypatch, TrailInfo trailInfo) {
        super(level, entitypatch, trailInfo);
    }

    @Inject(method = "canContinue", at = @At("HEAD"), cancellable = true, remap = false)
    private void sword_soaring$render(CallbackInfoReturnable<Boolean> cir){
        if(this.owner instanceof VatanseverEntityPatch vatanseverEntityPatch && vatanseverEntityPatch.getArmature() instanceof VatanseverArmature vatanseverArmature){
            for(Joint joint : vatanseverArmature.getInvalidJoints(vatanseverEntityPatch)){
                if(joint.getId() == this.joint.getId()){
                    cir.setReturnValue(false);
                }
            }
        }
        if(this.owner.getOriginal() instanceof BabylonEntity babylonEntity){
            if(!babylonEntity.hasJoint(joint)){
                cir.setReturnValue(false);
            }
        }
        if(this.owner.getOriginal() instanceof WanEntity wanEntity){
            if(!wanEntity.hasJoint(joint)){
                cir.setReturnValue(false);
            }
        }
    }
}
