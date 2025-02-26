package net.p1nero.ss.entity.sword.screen_sword;

import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;

public class ScreenSwordPatch extends AbstractArtifactSpiritPatch<ScreenSword> {

    @Override
    public void initAnimator(ClientAnimator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, ScreenSwordAnimations.SCREEN_SWORD_IDLE);
        animator.setCurrentMotionsAsDefault();
    }

}
