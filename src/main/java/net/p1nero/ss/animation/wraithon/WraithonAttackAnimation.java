package net.p1nero.ss.animation.wraithon;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

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
            LivingEntity target = entitypatch.getTarget();
            if (target != null) {
                entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
            }
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
    protected void bindPhaseState(Phase phase) {
        float preDelay = phase.preDelay;
        this.stateSpectrumBlueprint
                .newTimePair(phase.start, preDelay).addState(EntityState.PHASE_LEVEL, 1)
                .newTimePair(phase.start, phase.contact).addState(EntityState.CAN_SKILL_EXECUTION, false)
                .newTimePair(phase.start, phase.end).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.UPDATE_LIVING_MOTION, true)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true)
                .newTimePair(phase.start, phase.end).addState(EntityState.TURNING_LOCKED, false)
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
