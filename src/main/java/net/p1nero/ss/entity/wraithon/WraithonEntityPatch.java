package net.p1nero.ss.entity.wraithon;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.animation.wraithon.WraithonActionAnimation;
import net.p1nero.ss.client.sound.SwordSoaringSounds;
import net.p1nero.ss.entity.wraithon.ai.WraithonChaseGoal;
import net.p1nero.ss.entity.wraithon.ai.WraithonCombatBehaviors;
import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import net.p1nero.ss.util.AnimationUtils;
import org.joml.Vector3f;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;

import java.util.Arrays;
import java.util.List;

public class WraithonEntityPatch extends MobPatch<WraithonEntity> {

    public static final float SCALE = 2.0F;
    public final List<AnimationManager.AnimationAccessor<WraithonActionAnimation>> leftRotAnimations = List.of(WraithonAnimations.WRAITHON_ROTATE_L_40, WraithonAnimations.WRAITHON_ROTATE_L_60, WraithonAnimations.WRAITHON_ROTATE_L_80, WraithonAnimations.WRAITHON_ROTATE_L_100, WraithonAnimations.WRAITHON_ROTATE_L_120, WraithonAnimations.WRAITHON_ROTATE_L_140, WraithonAnimations.WRAITHON_ROTATE_L_160, WraithonAnimations.WRAITHON_ROTATE_L_180);
    public final List<AnimationManager.AnimationAccessor<WraithonActionAnimation>> rightRotAnimations = List.of(WraithonAnimations.WRAITHON_ROTATE_R_40, WraithonAnimations.WRAITHON_ROTATE_R_60, WraithonAnimations.WRAITHON_ROTATE_R_80, WraithonAnimations.WRAITHON_ROTATE_R_100, WraithonAnimations.WRAITHON_ROTATE_R_120, WraithonAnimations.WRAITHON_ROTATE_R_140, WraithonAnimations.WRAITHON_ROTATE_R_160, WraithonAnimations.WRAITHON_ROTATE_R_180);

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
//        this.original.goalSelector.addGoal(1, new WraithonChaseGoal(this, 5));
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        if (this.original.getHealth() <= 0.0F) {
            this.currentLivingMotion = LivingMotions.DEATH;
        } else if (this.state.inaction() && considerInaction) {
            this.currentLivingMotion = LivingMotions.IDLE;
        } else if (this.original.getVehicle() != null) {
            this.currentLivingMotion = LivingMotions.MOUNT;
        } else if (!(this.original.getDeltaMovement().y < -0.550000011920929) && !this.isAirborneState()) {
            if (this.original.walkAnimation.speed() > 0.16F) {
                if (this.original.isAggressive()) {
                    this.currentLivingMotion = LivingMotions.CHASE;
                } else {
                    this.currentLivingMotion = LivingMotions.WALK;
                }
            } else {
                this.currentLivingMotion = LivingMotions.IDLE;
            }
        } else {
            this.currentLivingMotion = LivingMotions.FALL;
        }

        this.currentCompositeMotion = this.currentLivingMotion;
    }

    @Override
    public void tick(LivingEvent.LivingTickEvent event) {
        super.tick(event);
        syncPartEntities();
        if (this.getEntityState().inaction() && !this.getAnimator().getPlayerFor(null).getAnimation().get().isLinkAnimation()) {
            Vector3f euler = new Vector3f();
            JointTransform transform = this.getAnimator().getPose(1.0F).get("ROT");

            if (transform != null) {
                transform.rotation().getEulerAnglesYXZ(euler);
                float animYRot = (float) Math.toDegrees(euler.y);

                float yModelRot = this.getOriginal().getCorrectYRot(1.0F) + animYRot;
                this.getOriginal().setYRot(yModelRot);
                this.getOriginal().setYBodyRot(yModelRot);
                this.getOriginal().setYHeadRot(yModelRot);
                this.getOriginal().yRotO = yModelRot;
                this.getOriginal().yBodyRotO = yModelRot;
                this.getOriginal().yHeadRotO = yModelRot;
            }
        }
    }

    public void syncPartEntities() {
        for (WraithonPartEntity part : this.getOriginal().getWraithonParts()) {
            if (part == null) {
                continue;
            }
            Vec3 newPos = AnimationUtils.getJointWorldPos(this, part.joint);
            part.moveTo(newPos.add(part.getYOffset()));
        }
    }

    public boolean isTargetInDegree(Entity target) {
        return isTargetInDegree(target, -10, 10);
    }

    /**
     * 判断目标是否在一定角度范围内
     *
     * @param target 目标
     */
    public boolean isTargetInDegree(Entity target, float min, float max) {
        Vec3 targetPos = target.position();
        Vec3 selfPos = this.getOriginal().position();
        double theta = MathUtils.getYRotOfVector(targetPos.subtract(selfPos));
        double delta = theta - MathUtils.getYRotOfVector(this.getOriginal().getViewVector(1.0F));
        return delta > min && delta < max;
    }

    public void turnRight() {
        Vec3 targetPos = this.getTarget().position();
        Vec3 selfPos = this.getOriginal().position();
        double theta = MathUtils.getYRotOfVector(targetPos.subtract(selfPos));
        double delta = theta - MathUtils.getYRotOfVector(this.getOriginal().getViewVector(1.0F));
        if (delta > 30 && delta < 180) {
            turnRight(delta);
        }
    }

    public void turnLeft() {
        Vec3 targetPos = this.getTarget().position();
        Vec3 selfPos = this.getOriginal().position();
        double theta = MathUtils.getYRotOfVector(targetPos.subtract(selfPos));
        double delta = theta - MathUtils.getYRotOfVector(this.getOriginal().getViewVector(1.0F));
        if (delta < -30 && delta > -180) {
            turnLeft(-delta);
        }
    }

    /**
     * 右转向敌人
     */
    public void turnRight(double degree) {
        if(degree < 30) {
            return;
        }
        this.getOriginal().setRotating(true);

        // 定义与leftRotAnimations对应的角度值
        int[] angles = {40, 60, 80, 100, 120, 140, 160, 180};

        // 生成分界点数组（相邻角度的中间值）
        int[] breakpoints = new int[angles.length-1];
        for (int i = 0; i < breakpoints.length; i++) {
            breakpoints[i] = (angles[i] + angles[i+1]) / 2;
        }

        // 四舍五入后计算索引
        int roundedDegree = (int) Math.round(degree);
        int index = Arrays.binarySearch(breakpoints, roundedDegree);
        int insertionPoint = index >= 0 ? index + 1 : -(index + 1);
        this.playAnimationSynchronized(rightRotAnimations.get(insertionPoint), 0.15F);
    }

    public void turnLeft(double degree) {
        if(degree < 30) {
            return;
        }
        this.getOriginal().setRotating(true);

        // 定义与leftRotAnimations对应的角度值
        int[] angles = {40, 60, 80, 100, 120, 140, 160, 180};

        // 生成分界点数组（相邻角度的中间值）
        int[] breakpoints = new int[angles.length-1];
        for (int i = 0; i < breakpoints.length; i++) {
            breakpoints[i] = (angles[i] + angles[i+1]) / 2;
        }

        // 四舍五入后计算索引
        int roundedDegree = (int) Math.round(degree);
        int index = Arrays.binarySearch(breakpoints, roundedDegree);
        int insertionPoint = index >= 0 ? index + 1 : -(index + 1);

        this.playAnimationSynchronized(leftRotAnimations.get(insertionPoint), 0.15F);
    }

    public boolean isRotating() {
        return this.getOriginal().isRotating();
    }

    @Override
    public AttackResult attack(EpicFightDamageSource damageSource, Entity target, InteractionHand hand) {
        //计算附加伤害
        if (this.getOriginal().fireContainer.isFullState()) {
            super.attack(EpicFightDamageSources.copy(this.getOriginal().damageSources().inFire()), target, hand);
        } else if (this.getOriginal().explosionContainer.isFullState()) {
            super.attack(EpicFightDamageSources.copy(this.getOriginal().damageSources().explosion(this.getOriginal(), this.getOriginal())), target, hand);
        } else if (this.getOriginal().magicContainer.isFullState()) {
            super.attack(EpicFightDamageSources.copy(this.getOriginal().damageSources().indirectMagic(this.getOriginal(), this.getOriginal())), target, hand);
        } else if (this.getOriginal().projectileContainer.isFullState()) {
            super.attack(EpicFightDamageSources.copy(this.getOriginal().damageSources().mobProjectile(this.getOriginal(), this.getOriginal())), target, hand);
        } else if (this.getOriginal().outsideContainer.isFullState()) {
            super.attack(EpicFightDamageSources.copy(this.getOriginal().damageSources().outOfBorder()), target, hand);
        }

        return super.attack(damageSource, target, hand);
    }

    @Override
    public OpenMatrix4f getMatrix(float partialTicks) {
        return MathUtils.getModelMatrixIntegral(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, this.original.xRotO, this.original.getXRot(), this.getOriginal().getCorrectYRot(partialTicks), this.getOriginal().getCorrectYRot(partialTicks), partialTicks, 1.0F, 1.0F, 1.0F).scale(SCALE, SCALE, SCALE);
    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        return MathUtils.getModelMatrixIntegral(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, this.original.xRotO, this.original.getXRot(), this.getOriginal().getCorrectYRot(partialTicks), this.getOriginal().getCorrectYRot(partialTicks), partialTicks, 1.0F, 1.0F, 1.0F).scale(SCALE, SCALE, SCALE);
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
