package net.p1nero.ss.entity.wraithon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;

import javax.annotation.Nullable;

public class WraithonEntityPatch extends MobPatch<WraithonEntity> {

    public final float SCALE = 2.0F;
    //旋转目标，
    @Nullable
    private Entity turningTarget;

    @Override
    protected void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, WraithonAnimations.WRAITHON_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, WraithonAnimations.WRAITHON_WALK);
    }

    @Override
    protected void initAI() {
        super.initAI();
        this.original.goalSelector.addGoal(0, new AnimatedAttackGoal<>(this, WraithonCombatBehaviors.PHASE1.build(this)));
    }

    @Override
    public void updateMotion(boolean b) {
        commonAggressiveMobUpdateMotion(b);
    }

    @Override
    protected void serverTick(LivingEvent.LivingTickEvent event) {
        super.serverTick(event);
        checkRotation();
        //TODO 同步各受击碰撞箱的位置
    }

    public boolean isTargetInRightSide(Entity target){
        return isTargetInRightSide(target, 180);
    }

    public boolean isTargetInRightSide(Entity target, float rangeDegree){
        Vec3 targetPos = target.position();
        Vec3 selfPos = this.getOriginal().position();
        float yRot = this.getYRot();
        double dx = targetPos.x - selfPos.x;
        double dz = targetPos.z - selfPos.z;
        double theta = Math.toDegrees(Math.atan2(dz, dx));
        theta = (theta + 360) % 360;
        float bossAngle = (yRot % 360 + 360) % 360;
        double delta = theta - bossAngle;
        delta = (delta + 180) % 360 - 180;
        return delta >= -rangeDegree && delta <= 0;
    }

    /**
     * 右转向敌人
     */
    public void turnRight(Entity target){
        turnRight(target, 10);
    }

    public void turnRight(Entity target, float rangeDegree){
        this.turningTarget = target;
        this.playAnimationSynchronized(WraithonAnimations.WRAITHON_ROTATE_R, 0.15F);
    }

    public void turnLeft(Entity target){
        turnLeft(target, 10);
    }

    public void turnLeft(Entity target, float rangeDegree){
        this.turningTarget = target;
        this.playAnimationSynchronized(WraithonAnimations.WRAITHON_ROTATE_L, 0.15F);
    }

    public void checkRotation(){
        if(turningTarget == null) {
            return;
        }
        boolean isTurningRight = this.getAnimator().getPlayerFor(null).getAnimation().get().equals(WraithonAnimations.WRAITHON_ROTATE_R.get());

        if(isTargetInRightSide(turningTarget)){
            //打断动画
            this.playAnimationSynchronized(WraithonAnimations.WRAITHON_IDLE, 0.15F);
        }


    }

    @Override
    public OpenMatrix4f getMatrix(float partialTicks) {
        return super.getMatrix(partialTicks).scale(SCALE, SCALE, SCALE);
    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        return super.getModelMatrix(partialTicks).scale(SCALE, SCALE, SCALE);
    }

    @Override
    public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
        return null;
    }
}
