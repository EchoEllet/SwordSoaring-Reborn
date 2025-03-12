package net.p1nero.ss.entity.sword.gate_of_babylon;

import net.p1nero.ss.gameassets.animations.BabylonAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;

public class BabylonPatch extends AbstractBabylonPatch<BabylonEntity> {
    @Override
    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, BabylonAnimations.BABYLON_IDLE);
    }


}
