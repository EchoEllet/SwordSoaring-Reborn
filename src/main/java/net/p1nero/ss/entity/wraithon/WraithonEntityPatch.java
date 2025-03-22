package net.p1nero.ss.entity.wraithon;

import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;

public class WraithonEntityPatch extends MobPatch<WraithonEntity> {

    @Override
    protected void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, WraithonAnimations.WRAITHON_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, WraithonAnimations.WRAITHON_WALK);
    }

    @Override
    public void updateMotion(boolean b) {
        commonAggressiveMobUpdateMotion(b);
    }

    @Override
    public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
        return null;
    }
}
