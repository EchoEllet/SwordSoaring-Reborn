package net.p1nero.ss.animation.wraithon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class WraithonActionAnimation extends ActionAnimation {
    public WraithonActionAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, armature);
    }

    public WraithonActionAnimation(float transitionTime, float postDelay, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, armature);
    }








    @Override
    protected void move(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        if (this.validateMovement(entitypatch, animation)) {
            if ((Boolean)this.getState(EntityState.INACTION, entitypatch, entitypatch.getAnimator().getPlayerFor(this.getAccessor()).getElapsedTime())) {
                LivingEntity livingentity = (LivingEntity)entitypatch.getOriginal();
                Vec3 vec3o = this.getCoordVector(entitypatch, animation);
                Vec3 vec3 = vec3o.scale(WraithonEntityPatch.SCALE);
                livingentity.move(MoverType.SELF, vec3);
            }

        }
    }

}
