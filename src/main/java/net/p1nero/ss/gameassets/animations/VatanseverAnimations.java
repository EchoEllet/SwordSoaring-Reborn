package net.p1nero.ss.gameassets.animations;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.animation.*;
import net.p1nero.ss.capability.SSCapabilityProvider;
import net.p1nero.ss.client.sound.SwordSoaringSounds;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.entity.vatansever.VatanseverArmature;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.skill.weapon_passive.ArtifactSpiritPassiveSkill;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VatanseverAnimations {
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_AUTO1;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_AUTO2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_AUTO3;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_AUTO3_B;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_AUTO4;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_AUTO4_B;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_INIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_FLY_BEGIN;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLAYER_STORM_START;
    public static AnimationManager.AnimationAccessor<AttackAnimation> PLAYER_SHOOT_L1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> PLAYER_SHOOT_L2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> PLAYER_SHOOT_L3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> PLAYER_SHOOT_R1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> PLAYER_SHOOT_R2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> PLAYER_SHOOT_R3;

    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_IDLE;
    public static AnimationManager.AnimationAccessor<SelectiveAnimation> VATANSEVER_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_WALK_F;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_WALK_F_STOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_WALK_B;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_WALK_B_STOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_RUN_STOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_DEATH;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_FALL;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_FLOAT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_FLY;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_FLY_STOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_SNEAK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_SNEAK_STOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VATANSEVER_SWIM;
    public static AnimationManager.AnimationAccessor<AttackAnimation> VATANSEVER_AUTO1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> VATANSEVER_AUTO2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> VATANSEVER_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> VATANSEVER_AUTO3_B;
    public static AnimationManager.AnimationAccessor<AttackAnimation> VATANSEVER_AUTO4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> VATANSEVER_AUTO4_B;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_INIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_FLY_BEGIN;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_STORM_START;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_SHOOT_L1;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_SHOOT_L2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_SHOOT_L3;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_SHOOT_R1;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_SHOOT_R2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> VATANSEVER_SHOOT_R3;

    public static void buildVatanseverAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<VatanseverArmature> vatanseverArmature = SwordSoaringArmatures.VATANSEVER_ARMATURE;
        List<AttackAnimation.JointColliderPair> leftJoints = List.of(AttackAnimation.JointColliderPair.of(vatanseverArmature.get().L1, SwordSoaringColliders.VATANSEVER),
                AttackAnimation.JointColliderPair.of(vatanseverArmature.get().L2, SwordSoaringColliders.VATANSEVER),
                AttackAnimation.JointColliderPair.of(vatanseverArmature.get().L3, SwordSoaringColliders.VATANSEVER));
        List<AttackAnimation.JointColliderPair> rightJoints = List.of(AttackAnimation.JointColliderPair.of(vatanseverArmature.get().R1, SwordSoaringColliders.VATANSEVER),
                AttackAnimation.JointColliderPair.of(vatanseverArmature.get().R2, SwordSoaringColliders.VATANSEVER),
                AttackAnimation.JointColliderPair.of(vatanseverArmature.get().R3, SwordSoaringColliders.VATANSEVER));
        ArrayList<AttackAnimation.JointColliderPair> allJoints = new ArrayList<>();
        allJoints.addAll(leftJoints);
        allJoints.addAll(rightJoints);
        AttackAnimation.JointColliderPair[] right = rightJoints.toArray(new AttackAnimation.JointColliderPair[0]);
        AttackAnimation.JointColliderPair[] left = leftJoints.toArray(new AttackAnimation.JointColliderPair[0]);
        AttackAnimation.JointColliderPair[] all = allJoints.toArray(new AttackAnimation.JointColliderPair[0]);

        VATANSEVER_IDLE = builder.nextAccessor("biped/vatansever/living/vatansever_idle", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_WALK_F = builder.nextAccessor("biped/vatansever/living/vatansever_walk", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_WALK_F_STOP = builder.nextAccessor("biped/vatansever/living/vatansever_walk_stop", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_WALK_B = builder.nextAccessor("biped/vatansever/living/vatansever_walk_b", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_WALK_B_STOP = builder.nextAccessor("biped/vatansever/living/vatansever_walk_b_stop", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_WALK = builder.nextAccessor("biped/vatansever/living/vatansever_walk_all", accessor -> new SelectiveAnimation((entityPatch) -> {
            if (entityPatch instanceof VatanseverEntityPatch vatanseverEntityPatch && vatanseverEntityPatch.getOwnerPatch() != null) {
                Vec3 view = vatanseverEntityPatch.getOwnerPatch().getOriginal().getViewVector(1.0F);
                Vec3 move = vatanseverEntityPatch.getOwnerPatch().getOriginal().getDeltaMovement();
                double dot = view.dot(move);
                return dot < 0.0 ? 1 : 0;
            }
            return 0;
        }, accessor, VATANSEVER_WALK_F, VATANSEVER_WALK_B));
        VATANSEVER_RUN = builder.nextAccessor("biped/vatansever/living/vatansever_run", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_RUN_STOP = builder.nextAccessor("biped/vatansever/living/vatansever_run_stop", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_SWIM = builder.nextAccessor("biped/vatansever/living/vatansever_swim", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_FALL = builder.nextAccessor("biped/vatansever/living/vatansever_fall", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_DEATH = builder.nextAccessor("biped/vatansever/living/vatansever_death", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_FLOAT = builder.nextAccessor("biped/vatansever/living/vatansever_float", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_FLY = builder.nextAccessor("biped/vatansever/living/vatansever_fly", accessor -> new StaticAnimation(true, accessor, vatanseverArmature)
                .addEvents(AnimationEvent.InPeriodEvent.create(0, 3, (entityPatch, self, params) -> flyVFX(entityPatch), AnimationEvent.Side.CLIENT)));
        VATANSEVER_FLY_STOP = builder.nextAccessor("biped/vatansever/living/vatansever_fly_stop", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_SNEAK = builder.nextAccessor("biped/vatansever/living/vatansever_sneak", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_SNEAK_STOP = builder.nextAccessor("biped/vatansever/living/vatansever_sneak_stop", accessor -> new StaticAnimation(true, accessor, vatanseverArmature));
        VATANSEVER_AUTO1 = builder.nextAccessor("biped/vatansever/vatansever_auto1", accessor -> new VatanseverAttackAnimation(0.15F, accessor, vatanseverArmature,
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.0F, 0.7F, 1.1F, 1.1F, Float.MAX_VALUE, vatanseverArmature.get().R1, SwordSoaringColliders.VATANSEVER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.0F, 0.7F, 1.1F, 1.1F, Float.MAX_VALUE, vatanseverArmature.get().R2, SwordSoaringColliders.VATANSEVER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.0F, 0.7F, 1.1F, 1.1F, Float.MAX_VALUE, vatanseverArmature.get().R3, SwordSoaringColliders.VATANSEVER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))));
        VATANSEVER_AUTO2 = builder.nextAccessor("biped/vatansever/vatansever_auto2", accessor -> new VatanseverAttackAnimation(0.15F, accessor, vatanseverArmature,
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.0F, 0.7F, 1.0F, 1.60F, Float.MAX_VALUE, vatanseverArmature.get().L1, SwordSoaringColliders.VATANSEVER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.0F, 0.7F, 1.0F, 1.60F, Float.MAX_VALUE, vatanseverArmature.get().L2, SwordSoaringColliders.VATANSEVER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.0F, 0.7F, 1.0F, 1.60F, Float.MAX_VALUE, vatanseverArmature.get().L3, SwordSoaringColliders.VATANSEVER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))));
        VATANSEVER_AUTO3 = builder.nextAccessor("biped/vatansever/vatansever_auto3", accessor -> new AttackAnimation(0.15F, accessor, vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 0.01F, 0.01F, 0.01F, 0.01F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, right)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.NO_SOUND.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))));

        VATANSEVER_AUTO3_B = builder.nextAccessor("biped/vatansever/vatansever_auto3_b", accessor -> new AttackAnimation(0.15F, accessor, vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 1.75F, 1.75F, 4.0F, 4.0F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, right)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SwordSoaringSounds.VATANSEVER_WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))));
        VATANSEVER_AUTO4 = builder.nextAccessor("biped/vatansever/vatansever_auto4", accessor -> new AttackAnimation(0.15F, accessor, vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 1.33F, 1.33F, 1.43F, 4.0F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, all)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SwordSoaringSounds.VATANSEVER_WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))));
        VATANSEVER_AUTO4_B = builder.nextAccessor("biped/vatansever/vatansever_auto4_b", accessor -> new AttackAnimation(0.15F, accessor, vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 1.33F, 1.33F, 1.43F, 4.0F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, all)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.NO_SOUND.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))));
        VATANSEVER_STORM_START = builder.nextAccessor("biped/vatansever/skill/vatansever_storm_start", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature));
        VATANSEVER_INIT = builder.nextAccessor("biped/vatansever/vatansever_init", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature)
                .addEvents(AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, staticAnimation, objects) ->
                        livingEntityPatch.playSound(EpicFightSounds.ENTITY_MOVE.get(), 0.0F, 0.0F), AnimationEvent.Side.CLIENT)));
        VATANSEVER_FLY_BEGIN = builder.nextAccessor("biped/vatansever/vatansever_fly_begin", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addEvents(AnimationEvent.InPeriodEvent.create(0, 1.2F, (entityPatch, self, params) -> {
                    flyVFX(entityPatch);
                    entityPatch.playSound(SoundEvents.FIRE_AMBIENT, 0.0F, 0.0F);
                }, AnimationEvent.Side.CLIENT))
                .addEvents(AnimationEvent.InTimeEvent.create(2.2F, (entityPatch, self, params) -> entityPatch.playSound(EpicFightSounds.ENTITY_MOVE.get(), 0.0F, 0.0F), AnimationEvent.Side.CLIENT)));

        VATANSEVER_SHOOT_L3 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_l3", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, summonFlySwordInTarget())
                .addEvents(AnimationEvent.InTimeEvent.create(0.3F, ((livingEntityPatch, staticAnimation, objects) -> shootVFX(livingEntityPatch, vatanseverArmature.get().L3)), AnimationEvent.Side.BOTH)));
        VATANSEVER_SHOOT_R3 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_r3", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, summonFlySwordInTarget())
                .addEvents(AnimationEvent.InTimeEvent.create(0.3F, ((livingEntityPatch, staticAnimation, objects) -> shootVFX(livingEntityPatch, vatanseverArmature.get().R2)), AnimationEvent.Side.BOTH)));
        VATANSEVER_SHOOT_L2 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_l2", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, summonFlySwordInTarget())
                .addEvents(AnimationEvent.InTimeEvent.create(0.3F, ((livingEntityPatch, staticAnimation, objects) -> shootVFX(livingEntityPatch, vatanseverArmature.get().L2)), AnimationEvent.Side.BOTH)));
        VATANSEVER_SHOOT_R2 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_r2", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, summonFlySwordInTarget())
                .addEvents(AnimationEvent.InTimeEvent.create(0.3F, ((livingEntityPatch, staticAnimation, objects) -> shootVFX(livingEntityPatch, vatanseverArmature.get().R2)), AnimationEvent.Side.BOTH)));
        VATANSEVER_SHOOT_L1 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_l1", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, summonFlySwordInTarget())
                .addEvents(AnimationEvent.InTimeEvent.create(0.3F, ((livingEntityPatch, staticAnimation, objects) -> shootVFX(livingEntityPatch, vatanseverArmature.get().L1)), AnimationEvent.Side.BOTH)));
        VATANSEVER_SHOOT_R1 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_r1", accessor -> new ActionAnimation(0.15F, accessor, vatanseverArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, summonFlySwordInTarget())
                .addEvents(AnimationEvent.InTimeEvent.create(0.3F, ((livingEntityPatch, staticAnimation, objects) -> shootVFX(livingEntityPatch, vatanseverArmature.get().R1)), AnimationEvent.Side.BOTH)));

        Armatures.ArmatureAccessor<HumanoidArmature> biped = Armatures.BIPED;
        PLAYER_AUTO1 = builder.nextAccessor("biped/vatansever/vatansever_auto1_owner", accessor -> new LinkArtifactSpiritAnimation(0.15F, 1.1F, accessor, biped, VATANSEVER_AUTO1)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.7F, ((livingEntityPatch, staticAnimation, objects) -> swingSounds(livingEntityPatch)), AnimationEvent.Side.BOTH)));
        PLAYER_AUTO2 = builder.nextAccessor("biped/vatansever/vatansever_auto2_owner", accessor -> new LinkArtifactSpiritAnimation(0.15F, 1.9F, accessor, biped, VATANSEVER_AUTO2)
                .newTimePair(1.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.7F, ((livingEntityPatch, staticAnimation, objects) -> {
                            swingSounds(livingEntityPatch);
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(1.56F, ((livingEntityPatch, staticAnimation, objects) -> {
                            if (SwordCountis(livingEntityPatch, 6)) {
                                groundSplit(livingEntityPatch, 3, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 6, 1.1F, 200);
                            }
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(1.65F, ((livingEntityPatch, staticAnimation, objects) -> {
                            if (SwordCountis(livingEntityPatch, 4)) {
                                groundSplit(livingEntityPatch, 3.8, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 6, 1.1F, 200);
                            }
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(1.74F, ((livingEntityPatch, staticAnimation, objects) -> {
                            if (SwordCountis(livingEntityPatch, 2)) {
                                groundSplit(livingEntityPatch, 5, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 6, 1.1F, 200);
                            }
                        }), AnimationEvent.Side.BOTH)));
        PLAYER_AUTO3 = builder.nextAccessor("biped/vatansever/vatansever_auto3_owner", accessor -> new LinkArtifactSpiritAnimation(0, 2.25F, accessor, biped, VATANSEVER_AUTO3)
                .newTimePair(0.0F, 3.0F)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(1F, ((livingEntityPatch, staticAnimation, objects) -> {
                            if (SwordCountis(livingEntityPatch, 5)) {
                                groundSplit(livingEntityPatch, 3, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 6, 1.1F, 200);
                            }
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(1.09F, ((livingEntityPatch, staticAnimation, objects) -> {
                            if (SwordCountis(livingEntityPatch, 3)) {
                                groundSplit(livingEntityPatch, 3.8, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 6, 1.1F, 200);
                            }
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(1.18F, ((livingEntityPatch, staticAnimation, objects) -> {
                            if (SwordCountis(livingEntityPatch, 1)) {
                                groundSplit(livingEntityPatch, 5, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 6, 1.1F, 200);
                            }
                        }), AnimationEvent.Side.BOTH)));
        PLAYER_AUTO3_B = builder.nextAccessor("biped/vatansever/vatansever_auto3_b_owner", accessor -> new LinkArtifactSpiritAnimation(0.15F, 4.0F, accessor, biped, VATANSEVER_AUTO3_B)
                .newTimePair(0.0F, 3.0F)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .addEvents(AnimationEvent.InTimeEvent.create(2.0F, ((livingEntityPatch, staticAnimation, objects) -> {
                    int n = 16;
                    for (int i = 0; i < n; ++i) {
                        groundSplit(livingEntityPatch, 5 + i * 3, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 10, 4, 100);
                    }
                }), AnimationEvent.Side.BOTH)));
        PLAYER_AUTO4 = builder.nextAccessor("biped/vatansever/vatansever_auto4_owner", accessor -> new LinkArtifactSpiritAnimation(0.15F, 4F, accessor, biped, VATANSEVER_AUTO4)
                .newTimePair(1.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .addEvents(AnimationEvent.InTimeEvent.create(1.38F, ((livingEntityPatch, staticAnimation, objects) -> {
                    groundSplit(livingEntityPatch, 4.2, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 20, 6, 1000);
                }), AnimationEvent.Side.BOTH)));
        PLAYER_AUTO4_B = builder.nextAccessor("biped/vatansever/vatansever_auto4_b_owner", accessor -> new LinkArtifactSpiritAnimation(0.15F, 4F, accessor, biped, VATANSEVER_AUTO4_B)
                .newTimePair(1.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .addEvents(AnimationEvent.InPeriodEvent.create(0.9F, 2.5F, (entityPatch, self, params) ->
                        {
                            swingSounds(entityPatch);
                            attractEntities(entityPatch, 15, getTotalAttackDamage(entityPatch), 3);
                        },
                        AnimationEvent.Side.BOTH))
                .addEvents(AnimationEvent.InTimeEvent.create(2.5F, ((livingEntityPatch, staticAnimation, objects) -> {
                    groundSplit(livingEntityPatch, 0, 0, 0, 0, getTotalAttackDamage(livingEntityPatch) * 3, 4, 1000);
                }), AnimationEvent.Side.BOTH)));
        PLAYER_INIT = builder.nextAccessor("biped/vatansever/vatansever_init_player", accessor -> new LinkArtifactSpiritAnimation(0.15F, 0, accessor, biped, VATANSEVER_INIT));
        PLAYER_FLY_BEGIN = builder.nextAccessor("biped/vatansever/vatansever_fly_begin_player", accessor -> new LinkArtifactSpiritAnimation(0.15F, accessor, biped, VATANSEVER_FLY_BEGIN)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch.getOriginal() instanceof ServerPlayer serverPlayer) {
                        serverPlayer.startFallFlying();
                    }
                }, AnimationEvent.Side.SERVER)));
        PLAYER_STORM_START = builder.nextAccessor("biped/vatansever/skill/vatansever_storm_start_player", accessor -> new LinkArtifactSpiritAnimation(0.15F, accessor, biped, VATANSEVER_STORM_START)
                .addEvents(AnimationEvent.InTimeEvent.create(1.0F, ((livingEntityPatch, staticAnimation, objects) -> {
                            groundSplit(livingEntityPatch, 0, 0, 0, 0, 0, 3, 2000);
                            createStorm(livingEntityPatch, 0, 0, 0, VatanseverStormAnimations.VATANSEVER_STORM_RISE_1);
                            createStorm(livingEntityPatch, 0, 0, 0, VatanseverStormAnimations.VATANSEVER_STORM_RISE_2);
                            createStorm(livingEntityPatch, 0, 0, 0, VatanseverStormAnimations.VATANSEVER_STORM_RISE_3);
                            createStorm(livingEntityPatch, 0, 0, 0, VatanseverStormAnimations.VATANSEVER_STORM_RISE_4);
                            Entity entity = livingEntityPatch.getOriginal();
                            if (entity.level() instanceof ServerLevel serverLevel) {
                                serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SwordSoaringSounds.VATANSEVER_STORM.get(), SoundSource.HOSTILE, 1.0F, 1.0F
                                );
                            }
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(2.2F, ((livingEntityPatch, staticAnimation, objects) -> {
                            createStorm(livingEntityPatch, 0, 0, 0, VatanseverStormAnimations.VATANSEVER_STORM_MIDDLE_2);
                            createStorm(livingEntityPatch, 0, 0, 0, VatanseverStormAnimations.VATANSEVER_STORM_MIDDLE);
                            createStorm(livingEntityPatch, 0, 0, 0, VatanseverStormAnimations.VATANSEVER_STORM_UP);
                            createStorm(livingEntityPatch, 0, 0, 0, VatanseverStormAnimations.VATANSEVER_STORM_DOWN);
                        }), AnimationEvent.Side.BOTH)));

        PLAYER_SHOOT_L3 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_l3", accessor -> new VatanseverPlayerShootAnimation(0.15F, 0.0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE,
                SwordSoaringColliders.SCAN_SCALE, vatanseverArmature.get().rootJoint, accessor, vatanseverArmature)
                .setArtifactSpiritAnimation(VATANSEVER_SHOOT_L3)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false));
        PLAYER_SHOOT_R3 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_r3", accessor -> new VatanseverPlayerShootAnimation(0.15F, 0.0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE,
                SwordSoaringColliders.SCAN_SCALE, vatanseverArmature.get().rootJoint, accessor, vatanseverArmature).setArtifactSpiritAnimation(VATANSEVER_SHOOT_R3)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false));
        PLAYER_SHOOT_L2 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_l2", accessor -> new VatanseverPlayerShootAnimation(0.15F, 0.0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE,
                SwordSoaringColliders.SCAN_SCALE, vatanseverArmature.get().rootJoint, accessor, vatanseverArmature).setArtifactSpiritAnimation(VATANSEVER_SHOOT_L2)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false));
        PLAYER_SHOOT_R2 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_r2", accessor -> new VatanseverPlayerShootAnimation(0.15F, 0.0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE,
                SwordSoaringColliders.SCAN_SCALE, vatanseverArmature.get().rootJoint, accessor, vatanseverArmature).setArtifactSpiritAnimation(VATANSEVER_SHOOT_R2)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false));
        PLAYER_SHOOT_L1 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_l1", accessor -> new VatanseverPlayerShootAnimation(0.15F, 0.0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE,
                SwordSoaringColliders.SCAN_SCALE, vatanseverArmature.get().rootJoint, accessor, vatanseverArmature).setArtifactSpiritAnimation(VATANSEVER_SHOOT_L1)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false));
        PLAYER_SHOOT_R1 = builder.nextAccessor("biped/vatansever/skill/vatansever_shoot_r1", accessor -> new VatanseverPlayerShootAnimation(0.15F, 0.0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE,
                SwordSoaringColliders.SCAN_SCALE, vatanseverArmature.get().rootJoint, accessor, vatanseverArmature).setArtifactSpiritAnimation(VATANSEVER_SHOOT_R1)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false));
    }

    public static AnimationEvent summonFlySwordInTarget() {
        return AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> {
            if (livingEntityPatch instanceof VatanseverEntityPatch vatanseverEntityPatch && vatanseverEntityPatch.getOwnerPatch() instanceof ServerPlayerPatch serverPlayerPatch) {
                if (vatanseverEntityPatch.getTarget() != null) {
                    vatanseverEntityPatch.getOwnerPatch().getOriginal().getCapability(SSCapabilityProvider.SS_PLAYER).ifPresent(ssPlayer -> {
                        //确保没有多余的剑
                        if (ssPlayer.getVatanseverShootEntities().size() == 6 - vatanseverEntityPatch.getLeftSwordCount()) {
                            FlySwordEntity flySwordEntity = new FlySwordEntity(vatanseverEntityPatch.getOwnerPatch().getOriginal(), 500, vatanseverEntityPatch.getTarget());
                            flySwordEntity.setAnimationToPlay(vatanseverEntityPatch.getOriginal().getRandom().nextBoolean() ? FlySwordAnimations.FLY_SWORD_ATK_1 : FlySwordAnimations.FLY_SWORD_ATK_2);
                            if (vatanseverEntityPatch.getOriginal().level().addFreshEntity(flySwordEntity)) {
                                ssPlayer.addVatanseverShootEntity(flySwordEntity);
                                SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                                if (manager.hasData(SwordSoaringDatakeys.SWORD_COUNT.get())) {
                                    manager.setDataSync(SwordSoaringDatakeys.SWORD_COUNT.get(), vatanseverEntityPatch.getLeftSwordCount() - 1, serverPlayerPatch.getOriginal());
                                }
                            }
                        }
                    });
                }
            }
        }, AnimationEvent.Side.SERVER);
    }

    private static void swingSounds(LivingEntityPatch<?> livingEntityPatch) {
        Entity entity = livingEntityPatch.getOriginal();
        if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SwordSoaringSounds.VATANSEVER_WHOOSH.get(), SoundSource.HOSTILE, 1.0F, 1.0F
            );
        }
    }

    private static void shootVFX(LivingEntityPatch<?> livingEntityPatch, Joint toolJoint) {
        LivingEntity entity = livingEntityPatch.getOriginal();
        OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBindedTransformFor(livingEntityPatch.getAnimator().getPose(1.0F), toolJoint);
        transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(entity.yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(rotation, transformMatrix, transformMatrix);
        Vec3 pos = new Vec3(transformMatrix.m30 + (float) entity.getX(), transformMatrix.m31 + (float) entity.getY(), transformMatrix.m32 + (float) entity.getZ());
        if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SwordSoaringSounds.VATANSEVER_WHOOSH_BIG.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        } else {
            createRandomSmokeLine(entity.level(), pos, 50);
        }
    }

    public static void createStorm(LivingEntityPatch<?> entityPatch, double xOffset, double yOffset, double zOffset, AnimationManager.AnimationAccessor<? extends StaticAnimation> staticAnimation) {
        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
            ServerPlayer serverPlayer = serverPlayerPatch.getOriginal();
            Vec3 pos = new Vec3(serverPlayer.getX() + xOffset, serverPlayer.getY() + yOffset, serverPlayer.getZ() + zOffset);
            VatanseverStormEntity stormEntity = new VatanseverStormEntity(serverPlayer.level(), serverPlayer, pos);
            serverPlayer.level().addFreshEntity(stormEntity);
            stormEntity.setYRot(serverPlayer.getYRot());
            EpicFightCapabilities.getEntityPatch(stormEntity, VatanseverStormEntityPatch.class).playAnimationSynchronized(staticAnimation, 0.05F);
        }

    }

    public static void groundSplit(LivingEntityPatch<?> entityPatch, double viewOffset, double xOffset, double yOffset, double zOffset, float damage, float radius, int particleCount) {
        LivingEntity entity = entityPatch.getOriginal();
        Vec3 pos = entity.position();
        Vec3 dir = entity.getViewVector(1).normalize().scale(viewOffset);
        Vec3 target = pos.add(dir.x + xOffset, -1 + yOffset, dir.z + zOffset);
        Vec3 damagetarget = pos.add(dir.x + xOffset, yOffset, dir.z + zOffset);
        if (entity.level() instanceof ServerLevel level) {
            LevelUtil.circleSlamFracture(entity, level, target, radius);
            dealAreaDamage(level, damagetarget, entity, damage, radius);
        } else {
            createRandomSmokeLine(entity.level(), target, particleCount);
        }
    }

    private static void jet(VatanseverEntityPatch vatanseverEntityPatch, Joint toolJoint, int particleCount) {
        VatanseverEntity vatanseverEntity = vatanseverEntityPatch.getOriginal();
        if (vatanseverEntity.getOwner() == null) {
            return;
        }
        Level world = vatanseverEntity.level();
        // 获取骨骼变换矩阵
        OpenMatrix4f transformMatrix = vatanseverEntityPatch.getArmature().getBindedTransformFor(vatanseverEntityPatch.getAnimator().getPose(1.0F), toolJoint);

        // 初始变换（位置偏移和基础旋转）
        transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(vatanseverEntityPatch.getOriginal().yBodyRot + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(rotation, transformMatrix, transformMatrix);

        // 生成粒子
        for (int i = 0; i < 5 * particleCount; i++) {
            world.addParticle(ParticleTypes.CLOUD, transformMatrix.m30 + (float) vatanseverEntity.getX(), transformMatrix.m31 + (float) vatanseverEntity.getY(), transformMatrix.m32 + (float) vatanseverEntity.getZ(), 0, 0, 0);
        }
        for (int i = 0; i < 3 * particleCount; i++) {
            world.addParticle(
                    ParticleTypes.END_ROD, transformMatrix.m30 + (float) vatanseverEntity.getX(), transformMatrix.m31 + (float) vatanseverEntity.getY(), transformMatrix.m32 + (float) vatanseverEntity.getZ(), 0, 0, 0
            );
        }
    }

    public static void flyVFX(LivingEntityPatch<?> entityPatch) {
        int particleCount = 1;
        if (entityPatch instanceof VatanseverEntityPatch vatanseverEntityPatch) {
            for (Joint joint : SwordSoaringArmatures.VATANSEVER_ARMATURE.get().joints) {
                jet(vatanseverEntityPatch, joint, particleCount);
            }
        }
    }

    public static void attractEntities(LivingEntityPatch<?> entityPatch, float attractRadius, float damage, float damageRadius) {
        LivingEntity source = entityPatch.getOriginal();
        Vec3 sourcePos = source.position();
        if (source.level() instanceof ServerLevel level) {

            AABB area = new AABB(sourcePos.x - attractRadius, sourcePos.y - attractRadius, sourcePos.z - attractRadius,
                    sourcePos.x + attractRadius, sourcePos.y + attractRadius, sourcePos.z + attractRadius);

            source.level().getEntitiesOfClass(Entity.class, area).forEach(entity -> {
                if (entity == source) return;
                if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) return;

                Vec3 entityPos = entity.position();
                Vec3 delta = sourcePos.subtract(entityPos);
                double distance = delta.length();

                if (distance > 1.0) {
                    Vec3 direction = delta.normalize();
                    double speed = 0.3;
                    entity.setDeltaMovement(entity.getDeltaMovement().add(direction.scale(speed)));
                } else {
                    Vec3 safePos = sourcePos.subtract(delta.normalize().scale(1.0));
                    entity.setPos(safePos.x, safePos.y, safePos.z);
                    entity.setDeltaMovement(Vec3.ZERO);
                }
                if (entity.distanceTo(source) < damageRadius) {
                    entity.hurt(entity.damageSources().indirectMagic(source, source), damage);
                }
            });
        } else {
            createRandomSmokeLine(source.level(), sourcePos, 10);
        }
    }

    private static final double MIN_SPEED1 = 0.1;
    private static final double MAX_SPEED1 = 0.5;

    private static void createRandomSmokeLine(Level level, Vec3 center, int particleCount) {
        RandomSource random1 = level.random;

        for (int i = 0; i < particleCount; i++) {
            double t = (double) i / (particleCount - 1);
            double distance = t * 5.0;

            double angle = random1.nextDouble() * 2 * Math.PI;
            double pitch = random1.nextDouble() * Math.PI - Math.PI / 2;

            double offsetX = Math.cos(angle) * Math.cos(pitch);
            double offsetY = Math.sin(pitch);
            double offsetZ = Math.sin(angle) * Math.cos(pitch);

            double x = center.x() + offsetX * distance;
            double y = center.y() + offsetY * distance;
            double z = center.z() + offsetZ * distance;
            double speed = MIN_SPEED1 + random1.nextDouble() * (MAX_SPEED1 - MIN_SPEED1);
            level.addParticle(ParticleTypes.SMOKE, x, y, z, offsetX * speed, offsetY * speed, offsetZ * speed);
        }
    }

    public static void dealAreaDamage(ServerLevel level, Vec3 center, Entity source, float damage, float radius) {
        if (radius <= 0) return;
        AABB area = new AABB(
                center.x() - radius,
                center.y() - radius,
                center.z() - radius,
                center.x() + radius,
                center.y() + radius,
                center.z() + radius
        );
        //来源实体过滤
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, entity ->
                entity.isAlive() && entity.distanceToSqr(center) <= radius * radius && !(entity instanceof Player player && player.isCreative()) && entity != source && !(entity instanceof AbstractArtifactSpiritEntity));
        //线程安全迭代
        for (LivingEntity entity : new ArrayList<>(entities)) {
            if (entity.invulnerableTime >= 0 && source != null) {
                entity.invulnerableTime = 0;
                entity.hurt(entity.damageSources().indirectMagic(source, source), damage * 0.5F);
                entity.invulnerableTime = 0;
                entity.hurt(entity.damageSources().indirectMagic(source, source), damage);
                entity.invulnerableTime = 0;
            }
        }
    }

    public static float getTotalAttackDamage(LivingEntityPatch<?> entityPatch) {
        LivingEntity owner = entityPatch.getOriginal();
        double baseDamage = owner.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return (float) baseDamage;
    }

    public static VatanseverEntityPatch getVatanseverPatch(LivingEntityPatch<?> ownerPatch) {
        if (ownerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
            if (serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE) != null) {
                SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                if (manager.hasData(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get())) {
                    Entity entity = serverPlayerPatch.getOriginal().level().getEntity(manager.getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get()));
                    if (entity != null) {
                        return EpicFightCapabilities.getEntityPatch(entity, VatanseverEntityPatch.class);
                    }
                }
            }
        }
        return null;
    }

    public static boolean SwordCountis(LivingEntityPatch<?> ownerPatch, float count) {
        if (getVatanseverPatch(ownerPatch) == null) {
            return false;
        } else {
            VatanseverEntityPatch vatanseverEntityPatch = getVatanseverPatch(ownerPatch);
            return vatanseverEntityPatch.getLeftSwordCount() >= count;
        }
    }
}
