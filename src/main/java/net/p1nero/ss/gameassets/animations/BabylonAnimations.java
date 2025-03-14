package net.p1nero.ss.gameassets.animations;

import net.minecraft.core.particles.ParticleTypes;
import net.p1nero.ss.animation.BabylonMultiPhaseAttackAnimation;
import net.p1nero.ss.client.sound.SwordSoaringSounds;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.util.AnimationUtils;
import net.p1nero.ss.util.vfx.ParticleVFX;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public class BabylonAnimations {

    public static AnimationManager.AnimationAccessor<StaticAnimation> BABYLON_IDLE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BABYLON_SHOOT_START;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BABYLON_SHOOT_LOOP;
    public static AnimationManager.AnimationAccessor<ActionAnimation> BABYLON_SUMMON_PLAYER;

    public static void buildBabylonAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<BabylonArmature> babylonArmature = SwordSoaringArmatures.BABYLON_ARMATURE;

        BABYLON_IDLE = builder.nextAccessor("babylon/babylon_idle", (accessor -> new StaticAnimation(true, accessor, babylonArmature)));
        
        BABYLON_SHOOT_START = builder.nextAccessor("babylon/babylon_shoot_start", accessor -> new BabylonMultiPhaseAttackAnimation(0.15F, accessor, babylonArmature, AnimationUtils.getPhases(babylonArmature.get().joints, 1.33F, 3.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F));

        BABYLON_SHOOT_LOOP = builder.nextAccessor( "babylon/babylon_shoot_go", accessor -> new BabylonMultiPhaseAttackAnimation(0.15F, accessor, babylonArmature, AnimationUtils.getPhases(babylonArmature.get().joints, 1.33F, 3.0F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F));

        Armatures.ArmatureAccessor<HumanoidArmature> biped = Armatures.BIPED;
        BABYLON_SUMMON_PLAYER = builder.nextAccessor("babylon/babylon_shoot_owner", accessor -> new ActionAnimation(0.15F, 2.4F, accessor, biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F)
                .addEvents(AnimationEvent.InTimeEvent.create(1.3F, ((livingEntityPatch, staticAnimation, objects) -> {
                    ParticleVFX.createSphereParticles(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal().getEyePosition(), ParticleTypes.END_ROD, 5, 0.1, 0.2, 100);
                    livingEntityPatch.playSound(SwordSoaringSounds.VATANSEVER_WHOOSH_BIG.get(), 0.0F, 0.0F);
                }), AnimationEvent.Side.BOTH))
                .newTimePair(0.0F, 2.4F)
                .addStateRemoveOld(EntityState.ATTACK_RESULT, (source -> AttackResult.ResultType.MISSED))
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false));

    }

}
