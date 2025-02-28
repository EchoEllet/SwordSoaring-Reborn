package net.p1nero.ss.entity.sword.fly_sword;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.animations.FlySwordAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;

public class FlySwordPatch extends AbstractArtifactSpiritPatch<FlySwordEntity> {
    private boolean played;
    private LivingEntity target;
    @Override
    public LivingEntity getTarget() {
        return target;
    }

    /**
     * 最好只用一次
     */
    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    public void tick(LivingEvent.LivingUpdateEvent event) {
        super.tick(event);
        if(target != null){
            this.getOriginal().setPos(target.position());
        }
    }

    /**
     * 播放初始动画
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientTick(LivingEvent.LivingUpdateEvent event) {
        super.clientTick(event);
        if(!played){
            if(this.isLogicalClient() && this.getOwnerPatch() != null){

            }
        }
    }

    @Override
    public void initAnimator(ClientAnimator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, FlySwordAnimations.FLY_SWORD_ATK_IDLE);
        animator.setCurrentMotionsAsDefault();
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        keepIdleMotion(considerInaction);
    }
}
