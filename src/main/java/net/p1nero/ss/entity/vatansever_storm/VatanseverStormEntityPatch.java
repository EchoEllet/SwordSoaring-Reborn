package net.p1nero.ss.entity.vatansever_storm;

import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;

public class VatanseverStormEntityPatch extends AbstractArtifactSpiritPatch<VatanseverStormEntity> {

    @Override
    protected void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, VatanseverAnimations.VATANSEVER_IDLE);
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        keepIdleMotion(considerInaction);
    }

}
