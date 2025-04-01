package net.p1nero.ss.animation.wraithon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import org.joml.Vector3f;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class WraithonActionAnimation extends ActionAnimation {
    public WraithonActionAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT, false);
        this.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false);
        this.addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL, false);
    }

    public WraithonActionAnimation(float transitionTime, float postDelay, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT, false);
        this.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false);
        this.addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL, false);
    }

    @Override
    public void begin(LivingEntityPatch<?> entityPatch) {
        super.begin(entityPatch);
        if (entityPatch instanceof WraithonEntityPatch wraithonEntityPatch) {
            wraithonEntityPatch.getOriginal().setYRotBeforeRotation();
        }
    }

    @Override
    public void end(LivingEntityPatch<?> entityPatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        super.end(entityPatch, nextAnimation, isEnd);
        if(entityPatch instanceof WraithonEntityPatch wraithonEntityPatch){
            Vector3f euler = new Vector3f();
            this.getCoord().getInterpolatedTransform(this.getTotalTime()).rotation().getEulerAnglesXYZ(euler);
            float yModelRot = (float) (wraithonEntityPatch.getOriginal().getYRotBeforeRotation() + Math.toDegrees(euler.z));
            entityPatch.getOriginal().setYRot(yModelRot);
            entityPatch.getOriginal().setYBodyRot(yModelRot);
            entityPatch.getOriginal().setYHeadRot(yModelRot);
            entityPatch.getOriginal().yRotO = yModelRot;
            entityPatch.getOriginal().yBodyRotO = yModelRot;
            entityPatch.getOriginal().yHeadRotO = yModelRot;
        }
    }

    @Override
    protected void move(LivingEntityPatch<?> entityPatch, AssetAccessor<? extends DynamicAnimation> animation) {
        if (this.validateMovement(entityPatch, animation)) {
            if (this.getState(EntityState.INACTION, entityPatch, entityPatch.getAnimator().getPlayerFor(this.getAccessor()).getElapsedTime())) {
                LivingEntity livingentity = entityPatch.getOriginal();
                Vec3 vec3o = this.getCoordVector(entityPatch, animation);
                Vec3 vec3 = vec3o.scale(WraithonEntityPatch.SCALE);
                livingentity.move(MoverType.SELF, vec3);
            }
        }
    }

}
