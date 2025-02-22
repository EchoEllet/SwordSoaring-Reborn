package net.p1nero.ss.entity.vatansever_storm;

import net.minecraft.world.entity.Entity;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;

public class VatanseverStormEntityPatch extends AbstractArtifactSpiritPatch<VatanseverStormEntity> {

    @Override
    public void initAnimator(ClientAnimator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, VatanseverAnimations.VATANSEVER_IDLE);
        animator.setCurrentMotionsAsDefault();
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        this.currentLivingMotion = LivingMotions.IDLE;
        this.currentCompositeMotion = LivingMotions.IDLE;
    }

    /**
     * 自己人也杀
     */
    @Override
    public boolean isTeammate(Entity entityIn) {
        return false;
    }

}
