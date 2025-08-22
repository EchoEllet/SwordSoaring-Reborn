package net.p1nero.ss.entity.sword.wan;

import net.p1nero.ss.entity.sword.gate_of_babylon.AbstractBabylonPatch;
import net.p1nero.ss.gameassets.animations.VatanseverStormAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;

public class WanPatch extends AbstractBabylonPatch<WanEntity> {

    public WanPatch(WanEntity entity) {
        super(entity);
    }

    @Override
    protected void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, VatanseverStormAnimations.VATANSEVER_STORM_IDLE);
    }

    @Override
    public boolean shouldUseOwnerAttack() {
        return true;
    }

}
