package net.p1nero.ss.animation.wraithon;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.p1nero.ss.entity.wraithon.WraithonArmature;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.TimePairList;
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
    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(animation);
        TimePairList coordUpdateTime = (TimePairList)this.getProperty(AnimationProperty.ActionAnimationProperty.COORD_UPDATE_TIME).orElse((TimePairList) null);
        boolean inUpdateTime = coordUpdateTime == null || coordUpdateTime.isTimeInPairs(player.getElapsedTime());
        boolean getRawCoord = (Boolean)this.getProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(!inUpdateTime);
        TransformSheet transformSheet = (TransformSheet)entitypatch.getAnimator().getVariables().getSharedVariable(ACTION_ANIMATION_COORD);
        MoveCoordFunctions.MoveCoordSetter moveCoordsetter = getRawCoord ? MoveCoordFunctions.RAW_COORD : (MoveCoordFunctions.MoveCoordSetter)this.getProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK).orElse((MoveCoordFunctions.MoveCoordSetter) null);
        if (moveCoordsetter != null) {
            moveCoordsetter.set((DynamicAnimation)animation.get(), entitypatch, transformSheet);
        }

        boolean hasNoGravity = ((LivingEntity)entitypatch.getOriginal()).isNoGravity();
        boolean moveVertical = (Boolean)this.getProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL).orElse(this.getProperty(AnimationProperty.ActionAnimationProperty.COORD).isPresent());
        MoveCoordFunctions.MoveCoordGetter moveGetter = getRawCoord ? MoveCoordFunctions.MODEL_COORD : (MoveCoordFunctions.MoveCoordGetter)this.getProperty(AnimationProperty.ActionAnimationProperty.COORD_GET).orElse(MoveCoordFunctions.MODEL_COORD);
        Vec3f move = moveGetter.get((DynamicAnimation)animation.get(), entitypatch, transformSheet, player.getPrevElapsedTime(), player.getElapsedTime());
        float MyRot = -entitypatch.getYRot();
        float radians = (float) Math.toRadians(MyRot);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);
        float originalX = move.x;
        float originalZ = move.z;
        float newX = originalX * cos - originalZ * sin;
        float newZ = originalX * sin + originalZ * cos;
        move = new Vec3f(newX, move.y, newZ); // 更新为旋转后的向量
        LivingEntity livingentity = (LivingEntity)entitypatch.getOriginal();
        Vec3 motion = livingentity.getDeltaMovement();
        Vec3f finalMove = move;
        Vec3f finalMove1 = move;
        this.getProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME).ifPresentOrElse((noGravityTime) -> {
            if (noGravityTime.isTimeInPairs(((DynamicAnimation)animation.get()).isLinkAnimation() ? 0.0F : player.getElapsedTime())) {
                livingentity.setDeltaMovement(motion.x, 0.0, motion.z);
            } else {
                finalMove.y = 0.0F;
            }

        }, () -> {
            if (moveVertical && finalMove1.y > 0.0F && !hasNoGravity) {
                double gravity = livingentity.getAttribute((Attribute) ForgeMod.ENTITY_GRAVITY.get()).getValue();
                livingentity.setDeltaMovement(motion.x, motion.y < 0.0 ? motion.y + gravity : 0.0, motion.z);
            }

        });
        if (!moveVertical) {
            move.y = 0.0F;
        }

        if (inUpdateTime) {
            this.getProperty(AnimationProperty.ActionAnimationProperty.ENTITY_YROT_PROVIDER).ifPresent((entityYRotProvider) -> {
                float yRot = entityYRotProvider.get((DynamicAnimation)animation.get(), entitypatch);
                entitypatch.setYRot(yRot);
            });
        }

        return move.toDoubleVector();
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
