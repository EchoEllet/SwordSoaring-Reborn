package net.p1nero.ss.animation;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

/**
 * 借碰撞箱
 */
public class AutoDiscardActionAnimation extends ActionAnimation {


    public AutoDiscardActionAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, armature);
    }

    public AutoDiscardActionAnimation(float transitionTime, float postDelay, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, postDelay, accessor, armature);
    }

    public AutoDiscardActionAnimation(float transitionTime, float postDelay, String path, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, postDelay, path, armature);
    }

    @Override
    public void end(LivingEntityPatch<?> entityPatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        super.end(entityPatch, nextAnimation, isEnd);
        if(!entityPatch.isLogicalClient()){
            entityPatch.getOriginal().discard();
        }
    }
}

