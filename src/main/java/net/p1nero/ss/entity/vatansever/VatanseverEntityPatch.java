package net.p1nero.ss.entity.vatansever;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.utils.math.OpenMatrix4f;

public class VatanseverEntityPatch extends AbstractArtifactSpiritPatch<VatanseverEntity> {

    @Override
    public void initAnimator(ClientAnimator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, VatanseverAnimations.VATANSEVER_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, VatanseverAnimations.VATANSEVER_WALK);
        animator.addLivingAnimation(LivingMotions.RUN, VatanseverAnimations.VATANSEVER_RUN);
        animator.addLivingAnimation(LivingMotions.CHASE, VatanseverAnimations.VATANSEVER_RUN);
        animator.addLivingAnimation(LivingMotions.JUMP, VatanseverAnimations.VATANSEVER_FALL);
        animator.addLivingAnimation(LivingMotions.FALL, VatanseverAnimations.VATANSEVER_FALL);
        animator.addLivingAnimation(LivingMotions.FLOAT, VatanseverAnimations.VATANSEVER_FLOAT);
        animator.addLivingAnimation(LivingMotions.SWIM, VatanseverAnimations.VATANSEVER_SWIM);
        animator.addLivingAnimation(LivingMotions.DEATH, VatanseverAnimations.VATANSEVER_DEATH);
        animator.addLivingAnimation(LivingMotions.FLY, VatanseverAnimations.VATANSEVER_FLY);
        animator.addLivingAnimation(LivingMotions.KNEEL, VatanseverAnimations.VATANSEVER_SNEAK);
        animator.addLivingAnimation(LivingMotions.SNEAK, VatanseverAnimations.VATANSEVER_SNEAK);
        animator.setCurrentMotionsAsDefault();
    }

    public boolean isOwnerFallFlying(){
        if(getOwnerPatch() != null){
            return getOwnerPatch().getOriginal().isFallFlying();
        }
        return false;
    }

    /**
     * 自己人也杀
     */
    @Override
    public boolean isTeammate(Entity entityIn) {
        return false;
    }

    /**
     * 同步旋转（byd卡顿也同步上了）
     */
    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        if(getOwnerPatch() != null && isOwnerFallFlying() && isLogicalClient()){
            return getOwnerPatch().getModelMatrix(partialTicks);
        }
        return super.getModelMatrix(partialTicks);
    }

}
