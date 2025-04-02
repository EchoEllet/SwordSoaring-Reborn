package net.p1nero.ss.animation.wraithon;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.wraithon.WraithonArmature;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.AttackEndEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

public class WraithonAttackAnimation extends AttackAnimation {
    public WraithonAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL,false);
    }

    public WraithonAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL,false);
    }

    public WraithonAttackAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(transitionTime, accessor, armature, phases);
        this.addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL,false);
    }

    public WraithonAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL,false);
    }

    public WraithonAttackAnimation(float convertTime, String path, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(convertTime, path, armature, phases);
        this.addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true);
        this.addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL,false);
    }
    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
        entitypatch.setLastAttackSuccess(false);
        if(entitypatch instanceof WraithonEntityPatch wraithonEntityPatch){
            wraithonEntityPatch.getOriginal().updateYRotBeforeRotation();
        }
    }

    @Override
    public void linkTick(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> linkAnimation) {
        super.linkTick(entitypatch, linkAnimation);
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this.getAccessor());
        float elapsedTime = player.getElapsedTime();
        EntityState state = ((DynamicAnimation)linkAnimation.get()).getState(entitypatch, elapsedTime);
        if (!entitypatch.isLogicalClient() && entitypatch instanceof MobPatch<?> mobpatch) {
            if (state.getLevel() == 1 && !state.turningLocked()) {
                ((Mob)mobpatch.getOriginal()).getNavigation().stop();
                ((LivingEntity)entitypatch.getOriginal()).attackAnim = 2.0F;
                LivingEntity target = entitypatch.getTarget();

            }
        }

        if (!entitypatch.isLogicalClient()) {
            this.attackTick(entitypatch, linkAnimation);
        }

    }

    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this.getAccessor());
        float prevElapsedTime = player.getPrevElapsedTime();
        float elapsedTime = player.getElapsedTime();

        getJointWorldYRotation(entitypatch, SwordSoaringArmatures.WRAITHON_ARMATURE.get().root, elapsedTime);


        EntityState prevState = ((DynamicAnimation)animation.get()).getState(entitypatch, prevElapsedTime);
        EntityState state = ((DynamicAnimation)animation.get()).getState(entitypatch, elapsedTime);
        Phase phase = this.getPhaseByTime(((DynamicAnimation)animation.get()).isLinkAnimation() ? 0.0F : elapsedTime);
        if (state.getLevel() == 1 && !state.turningLocked() && entitypatch instanceof MobPatch<?> mobpatch) {
            ((Mob)mobpatch.getOriginal()).getNavigation().stop();
            ((LivingEntity)entitypatch.getOriginal()).attackAnim = 2.0F;
        }

        if (prevState.attacking() || state.attacking() || prevState.getLevel() <= 2 && state.getLevel() > 2) {
            if (!prevState.attacking() || phase != this.getPhaseByTime(prevElapsedTime) && (state.attacking() || prevState.getLevel() <= 2 && state.getLevel() > 2)) {
                entitypatch.playSound(this.getSwingSound(entitypatch, phase), 10,0.0F, 0.0F);
                entitypatch.removeHurtEntities();
            }

            this.hurtCollidingEntities(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        }

    }
    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);

        if (entitypatch instanceof ServerPlayerPatch playerpatch) {
            if (isEnd) {
                playerpatch.getEventListener().triggerEvents(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, new AttackEndEvent(playerpatch, this.getAccessor()));
            }
        }
        if (entitypatch instanceof HumanoidMobPatch<?> mobpatch) {
            if (entitypatch.isLogicalClient()) {
                Mob entity = (Mob)mobpatch.getOriginal();
                if (entity.getTarget() != null && !entity.getTarget().isAlive()) {
                    entity.setTarget((LivingEntity)null);
                }
            }
        }

    }

    public static float getJointWorldYRotation(LivingEntityPatch<?> entityPatch, Joint joint, Float time ) {
        Animator animator = entityPatch.getAnimator();
        Pose pose = animator.getPlayerFor(null).getAnimation().get().getRawPose(time);
        // 1. 获取模型到世界的变换矩阵
        Vec3 entityPos = entityPatch.getOriginal().position();
        OpenMatrix4f modelMatrix = entityPatch.getModelMatrix(1.0F);

        // 构建包含实体位置、旋转和模型修正的变换矩阵
        OpenMatrix4f modelToWorld = OpenMatrix4f.createTranslation((float)entityPos.x, (float)entityPos.y, (float)entityPos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)) // 修正模型初始朝向
                .mulBack(modelMatrix); // 应用实体自身旋转

        // 2. 获取关节的局部变换并转换到世界空间
        OpenMatrix4f jointLocal = new OpenMatrix4f(entityPatch.getArmature().getBindedTransformFor(pose, joint));
        OpenMatrix4f jointWorld = jointLocal.mulFront(modelToWorld);

        // 3. 提取纯旋转矩阵（移除平移和缩放）
        OpenMatrix4f rotationOnly = new OpenMatrix4f(jointWorld);
        rotationOnly.removeTranslation(); // 移除平移
        rotationOnly.removeScale();       // 移除缩放

        // 4. 将旋转矩阵转换为四元数
        Quaternionf quat = rotationOnly.toQuaternion();

        // 5. 从四元数计算绕世界Y轴的旋转角度（yRot）
        float yawRadians = (float) Math.atan2(
                2.0f * (quat.w() * quat.y() + quat.x() * quat.z()),
                1.0f - 2.0f * (quat.y() * quat.y() + quat.z() * quat.z())
        );

        // 转换为角度并标准化到 [0, 360)
        float yawDegrees = (float) Math.toDegrees(yawRadians);
        yawDegrees = (yawDegrees % 360 + 360) % 360;

        return yawDegrees;
    }





    @Override
    protected void bindPhaseState(Phase phase) {
        float preDelay = phase.preDelay;
        this.stateSpectrumBlueprint
                .newTimePair(phase.start, preDelay).addState(EntityState.PHASE_LEVEL, 1)
                .newTimePair(phase.start, phase.contact).addState(EntityState.CAN_SKILL_EXECUTION, false)
                .newTimePair(phase.start, phase.end).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.UPDATE_LIVING_MOTION, true)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true)
                .newTimePair(0, Float.MAX_VALUE).addState(EntityState.TURNING_LOCKED, true)
                .newTimePair(preDelay, phase.contact).addState(EntityState.ATTACKING, true).addState(EntityState.PHASE_LEVEL, 2)
                .newTimePair(phase.contact, phase.end).addState(EntityState.PHASE_LEVEL, 3);
    }



    @Override
    protected void move(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        if (this.validateMovement(entitypatch, animation)) {
            if ((Boolean)this.getState(EntityState.INACTION, entitypatch, entitypatch.getAnimator().getPlayerFor(this.getAccessor()).getElapsedTime())) {
                LivingEntity livingentity = (LivingEntity)entitypatch.getOriginal();
                Vec3 vec3o = this.getCoordVector(entitypatch, animation);
                Vec3 vec3 = vec3o.scale(WraithonEntityPatch.SCALE);
                livingentity.move(MoverType.SELF, vec3);
            }

        }
    }




}
