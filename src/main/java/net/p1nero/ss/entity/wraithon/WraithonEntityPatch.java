package net.p1nero.ss.entity.wraithon;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.client.sound.SwordSoaringSounds;
import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import net.p1nero.ss.util.AnimationUtils;
import org.joml.Vector3f;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;

import javax.annotation.Nullable;

public class WraithonEntityPatch extends MobPatch<WraithonEntity> {

    public static final float SCALE = 2.0F;
    //旋转目标
    @Nullable
    private Entity rotateTarget;
    //旋转到距离目标夹角多少时停止
    private float rangeDegree;

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
    public void tick(LivingEvent.LivingTickEvent event) {
        super.tick(event);
        syncPartEntities();

        if(this.getEntityState().inaction() && !this.getAnimator().getPlayerFor(null).getAnimation().get().isLinkAnimation()){
            Vector3f euler = new Vector3f();
            JointTransform transform = this.getAnimator().getPose(1.0F).get("ROT");

            if(transform != null){
                transform.rotation().getEulerAnglesXYZ(euler);
                float animYRot = (float) Math.toDegrees(euler.y);
                System.out.print(animYRot);
                float yModelRot = this.getOriginal().getYRotBeforeRotation() + animYRot;
                this.getOriginal().setYRot(yModelRot);
                this.getOriginal().setYBodyRot(yModelRot);
                this.getOriginal().setYHeadRot(yModelRot);
                this.getOriginal().yRotO = yModelRot;
                this.getOriginal().yBodyRotO = yModelRot;
                this.getOriginal().yHeadRotO = yModelRot;
            }
        }
    }

    @Override
    protected void serverTick(LivingEvent.LivingTickEvent event) {
        super.serverTick(event);
        checkRotation();
    }

    public void syncPartEntities(){
        for(WraithonPartEntity part: this.getOriginal().getWraithonParts()) {
            if(part == null){
                continue;
            }
            Vec3 newPos = AnimationUtils.getJointWorldPos(this, part.joint);
            part.moveTo(newPos.add(part.getYOffset()));
        }
    }

    public boolean isTargetInDegree(Entity target){
        return isTargetInDegree(target, 10);
    }

    /**
     * 判断目标是否在一定角度范围内
     * @param target 目标
     * @param rangeDegree 角度范围
     */
    public boolean isTargetInDegree(Entity target, float rangeDegree){
        Vec3 targetPos = target.position();
        Vec3 selfPos = this.getOriginal().position();
        float yRot = this.getYRot();
        double theta = MathUtils.getYRotOfVector(targetPos.subtract(selfPos));
        theta = (theta + 360) % 360;
        float bossAngle = (yRot % 360 + 360) % 360;
        double delta = theta - bossAngle;
        return Math.abs(delta) < rangeDegree;
    }

    /**
     * 右转向敌人
     */
    public void turnRight(Entity target){
        turnRight(target, 10);
    }

    /**
     * 右转向敌人
     * @param target 敌人
     * @param rangeDegree 距离多少时停止
     */
    public void turnRight(Entity target, float rangeDegree){
        this.rotateTarget = target;
        this.rangeDegree = rangeDegree;
        this.playAnimationSynchronized(WraithonAnimations.WRAITHON_ROTATE_R, 0.15F);
    }

    public void turnLeft(Entity target){
        turnLeft(target, 10);
    }

    public void turnLeft(Entity target, float rangeDegree){
        this.rotateTarget = target;
        this.rangeDegree = rangeDegree;
        this.playAnimationSynchronized(WraithonAnimations.WRAITHON_ROTATE_L, 0.15F);
    }

    public void checkRotation(){
        if(rotateTarget == null) {
            return;
        }

        if(isTargetInDegree(rotateTarget, rangeDegree)){
            //打断动画
            this.playAnimationSynchronized(WraithonAnimations.WRAITHON_IDLE, 0.15F);
        }

    }

    @Override
    public OpenMatrix4f getMatrix(float partialTicks) {
        if(getEntityState().inaction()){
            return MathUtils.getModelMatrixIntegral(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, this.original.xRotO, this.original.getXRot(), this.getOriginal().getYRotBeforeRotation(), this.getOriginal().getYRotBeforeRotation(), partialTicks, 1.0F, 1.0F, 1.0F).scale(SCALE, SCALE, SCALE);
        }
        return super.getMatrix(partialTicks).scale(SCALE, SCALE, SCALE);
    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        if(getEntityState().inaction()){
            return MathUtils.getModelMatrixIntegral(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, this.original.xRotO, this.original.getXRot(), this.getOriginal().getYRotBeforeRotation(), this.getOriginal().getYRotBeforeRotation(), partialTicks, 1.0F, 1.0F, 1.0F).scale(SCALE, SCALE, SCALE);
        }
        return super.getModelMatrix(partialTicks).scale(SCALE, SCALE, SCALE);
    }

    @Override
    public SoundEvent getWeaponHitSound(InteractionHand hand) {
        return EpicFightSounds.BLADE_HIT.get();
    }
    @Override
    public SoundEvent getSwingSound(InteractionHand hand) {
        return SwordSoaringSounds.VATANSEVER_WHOOSH_BIG.get();
    }
    @Override
    public HitParticleType getWeaponHitParticle(InteractionHand hand) {
        return EpicFightParticles.HIT_BLADE.get();
    }

    @Override
    public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
        return null;
    }
}
