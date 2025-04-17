package net.p1nero.ss.gameassets.animations;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.p1nero.ss.Config;
import net.p1nero.ss.animation.wraithon.WraithonActionAnimation;
import net.p1nero.ss.animation.wraithon.WraithonAttackAnimation;
import net.p1nero.ss.client.CameraShake;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.wraithon.WraithonArmature;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import net.p1nero.ss.util.AnimationUtils;
import net.p1nero.ss.util.vfx.ParticleVFX;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class WraithonAnimations {
    public static AnimationManager.AnimationAccessor<ActionAnimation> BIPE_KNOCK_FLY;

    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_IDLE;

    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_WALK;

    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_JUMP_R;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_JUMP_L;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_JUMP_B;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_KNOCKDOWN;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_R_40;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_R_60;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_R_80;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_R_100;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_R_120;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_R_140;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_R_160;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_R_180;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_L_40;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_L_60;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_L_80;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_L_100;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_L_120;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_L_140;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_L_160;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_ROTATE_L_180;

    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_LEG_1;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_LEG_2;
    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_LEG_3;

    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_1;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_2;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_3;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_4;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_5;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_6;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_7;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_8;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_9;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_10;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_11;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_12;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_13;

    public static AnimationManager.AnimationAccessor<WraithonActionAnimation> WRAITHON_SKILL_1;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_SKILL_2;


    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_JUMP_R_ATK;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_JUMP_L_ATK;
    public static AnimationManager.AnimationAccessor<WraithonAttackAnimation> WRAITHON_JUMP_B_ATK;

    public static List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> DEBUG_ANIM_LIST;

    private static final int WRAITHON_ATK_PARTICLE_COUNT = 0;


    public static void buildWraithonAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<WraithonArmature> armature = SwordSoaringArmatures.WRAITHON_ARMATURE;
        Armatures.ArmatureAccessor<HumanoidArmature> bipe = Armatures.BIPED;

        BIPE_KNOCK_FLY = builder.nextAccessor("biped/living/knock_fly", (accessor -> new ActionAnimation(0.2F, accessor, bipe)));


        Supplier<AttackAnimation.JointColliderPair[]> supplier = () -> {
            List<AttackAnimation.JointColliderPair> atkJoints = List.of(AttackAnimation.JointColliderPair.of(armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK_1),
                    AttackAnimation.JointColliderPair.of(armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK_2));
            return atkJoints.toArray(new AttackAnimation.JointColliderPair[0]);
        };

        WRAITHON_IDLE = builder.nextAccessor("wraithon/wraithon_idle", (accessor -> new StaticAnimation(0.0F, true, accessor, armature)));

        WRAITHON_WALK = builder.nextAccessor("wraithon/wraithon_walk", (accessor -> new StaticAnimation(0.15F,true, accessor, armature)));

        WRAITHON_KNOCKDOWN = builder.nextAccessor("wraithon/wraithon_knockdown", (accessor -> new WraithonActionAnimation(0.15F,  accessor, armature)));
        WRAITHON_ROTATE_R_40 = builder.nextAccessor("wraithon/wraithon_rotate_r_40", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_R_60 = builder.nextAccessor("wraithon/wraithon_rotate_r_60", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_R_80 = builder.nextAccessor("wraithon/wraithon_rotate_r_80", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_R_100 = builder.nextAccessor("wraithon/wraithon_rotate_r_100", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_R_120 = builder.nextAccessor("wraithon/wraithon_rotate_r_120", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_R_140 = builder.nextAccessor("wraithon/wraithon_rotate_r_140", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_R_160 = builder.nextAccessor("wraithon/wraithon_rotate_r_160", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_R_180 = builder.nextAccessor("wraithon/wraithon_rotate_r_180", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L_40 = builder.nextAccessor("wraithon/wraithon_rotate_l_40", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L_60 = builder.nextAccessor("wraithon/wraithon_rotate_l_60", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L_80 = builder.nextAccessor("wraithon/wraithon_rotate_l_80", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L_100 = builder.nextAccessor("wraithon/wraithon_rotate_l_100", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L_120 = builder.nextAccessor("wraithon/wraithon_rotate_l_120", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L_140 = builder.nextAccessor("wraithon/wraithon_rotate_l_140", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L_160 = builder.nextAccessor("wraithon/wraithon_rotate_l_160", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L_180 = builder.nextAccessor("wraithon/wraithon_rotate_l_180", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_JUMP_R = builder.nextAccessor("wraithon/wraithon_jump_r", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_JUMP_L = builder.nextAccessor("wraithon/wraithon_jump_l", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));
        WRAITHON_JUMP_B = builder.nextAccessor("wraithon/wraithon_jump_b", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)));


        WRAITHON_LEG_1 = builder.nextAccessor("wraithon/wraithon_legattack_1", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)
                .addEvents(autoWraithonShockAtk(53, 55, SwordSoaringArmatures.WRAITHON_ARMATURE.get().leg_F_3_R, 5F)
                        , (autoWraithonShockAtk(53, 55, SwordSoaringArmatures.WRAITHON_ARMATURE.get().leg_F_3_L, 5F)))));
        WRAITHON_LEG_2 = builder.nextAccessor("wraithon/wraithon_legattack_2", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)
                .addEvents((autoWraithonShockAtk(52, 55, SwordSoaringArmatures.WRAITHON_ARMATURE.get().leg_F_3_R, 5F)))));
        WRAITHON_LEG_3 = builder.nextAccessor("wraithon/wraithon_legattack_3", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)
                .addEvents((autoWraithonShockAtk(42, 45, SwordSoaringArmatures.WRAITHON_ARMATURE.get().leg_F_3_L, 5F)))));


        WRAITHON_JUMP_R_ATK = builder.nextAccessor("wraithon/wraithon_jump_r_atk", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(101, 112))
                .addEvents(autoWraithonGroundSplit(101, 112, 6F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(101, 112, 0, 0))));
        WRAITHON_JUMP_L_ATK = builder.nextAccessor("wraithon/wraithon_jump_l_atk", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(102, 113))
                .addEvents(autoWraithonGroundSplit(102, 113, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(102, 113, 0, 0))));
        WRAITHON_JUMP_B_ATK = builder.nextAccessor("wraithon/wraithon_jump_b_atk", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(111, 115))
                .addEvents(autoWraithonGroundSplit(111, 115, 6F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(111, 115, 0, 0))));


        WRAITHON_1 = builder.nextAccessor("wraithon/wraithon_attack_1", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(67, 80), createSimplePhase(136, 150))
                .addEvents(autoWraithonGroundSplit(67, 80, 2.5F, 1), autoWraithonGroundSplit(136, 150, 2.5F, 1))
                .addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(67, 80, 136, 150))));

        WRAITHON_2 = builder.nextAccessor("wraithon/wraithon_attack_2", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(66, 80), createSimplePhase(141, 150))
                .addEvents(autoWraithonGroundSplit(66, 80, 2.5F, 1), autoWraithonGroundSplit(141, 150, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(66, 80, 141, 150))));

        WRAITHON_3 = builder.nextAccessor("wraithon/wraithon_attack_3", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(70, 73))
                .addEvents(autoWraithonGroundSplit(70, 73, 5F, 0))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(70, 73, 0, 0))));

        WRAITHON_4 = builder.nextAccessor("wraithon/wraithon_attack_4", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(63, 75), createSimplePhase(162, 173))
                .addEvents(autoWraithonGroundSplit(63, 75, 2.5F, 1), autoWraithonGroundSplit(162, 173, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(63, 75, 162, 173))));

        WRAITHON_5 = builder.nextAccessor("wraithon/wraithon_attack_5", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(53, 65))
                .addEvents(autoWraithonGroundSplit(53, 65, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(53, 65, 0, 0))));

        WRAITHON_6 = builder.nextAccessor("wraithon/wraithon_attack_6", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(47, 60), createSimplePhase(124, 140))
                .addEvents(autoWraithonGroundSplit(47, 60, 2.5F, 1), autoWraithonGroundSplit(124, 140, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(47, 60, 124, 140))));

        WRAITHON_7 = builder.nextAccessor("wraithon/wraithon_attack_7", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(64, 78), createSimplePhase(164, 175))
                .addEvents(autoWraithonGroundSplit(64, 78, 2.5F, 1), autoWraithonGroundSplit(164, 175, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(64, 78, 164, 175))));

        WRAITHON_8 = builder.nextAccessor("wraithon/wraithon_attack_8", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(62, 73), createSimplePhase(152, 160))
                .addEvents(autoWraithonGroundSplit(62, 73, 2.5F, 1), autoWraithonGroundSplit(152, 160, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(62, 73, 152, 160))));

        WRAITHON_9 = builder.nextAccessor("wraithon/wraithon_attack_9", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(51, 60))
                .addEvents(autoWraithonGroundSplit(51, 60, 5F, 0))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(51, 60, 0, 0))));

        WRAITHON_10 = builder.nextAccessor("wraithon/wraithon_attack_10", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(49, 63))
                .addEvents(autoWraithonGroundSplit(49, 63, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(49, 63, 0, 0))));

        WRAITHON_11 = builder.nextAccessor("wraithon/wraithon_attack_11", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(66, 75))
                .addEvents(autoWraithonGroundSplit(66, 75, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(66, 75, 0, 0))));

        WRAITHON_12 = builder.nextAccessor("wraithon/wraithon_attack_12", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(62, 73), createSimplePhase(142, 152))
                .addEvents(autoWraithonGroundSplit(62, 73, 2.5F, 1), autoWraithonGroundSplit(142, 152, 2.5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(62, 73, 142, 152))));

        WRAITHON_13 = builder.nextAccessor("wraithon/wraithon_attack_13", (accessor -> new WraithonAttackAnimation(0.15F, accessor, armature,
                createSimplePhase(70, 75))
                .addEvents(autoWraithonGroundSplit(70, 75, 5F, 1))
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(70, 75, 0, 0))));

        WRAITHON_SKILL_1 = builder.nextAccessor("wraithon/wraithon_skill_1", (accessor -> new WraithonActionAnimation(0.15F, accessor, armature)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(72 / 60F, ((livingEntityPatch, staticAnimation, objects) -> {
                            Joint joint = armature.get().hand_l;
                            Vec3 center = AnimationUtils.getJointWorldPos(livingEntityPatch, joint);
                            ParticleVFX.createJointDirectionalParticles(livingEntityPatch, joint, ParticleTypes.LARGE_SMOKE, 50, 2, 4, 200);
                            LivingEntity livingEntity = livingEntityPatch.getOriginal();
                            livingEntity.playSound(SoundEvents.GENERIC_EXPLODE, 5, 1);
                            if (livingEntity.level().isClientSide) {
                                CameraShake.shake(30, 20, 4.2f, center, 40);
                            }
                        }), AnimationEvent.Side.BOTH))));

        WRAITHON_SKILL_2 = builder.nextAccessor("wraithon/wraithon_skill_2", (accessor -> {
            WraithonAttackAnimation animation = new WraithonAttackAnimation(0.15F, accessor, armature, createSimplePhase(80, 87))
                    .addEvents(
                            AnimationEvent.InTimeEvent.create(84 / 60F, ((livingEntityPatch, staticAnimation, objects) -> {
                                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                float radius = 9;
                                float time = livingEntityPatch.getAnimator().getPlayerFor(null).getElapsedTime();
                                for (float i = 0; i <= 0.005; i = i + 0.0001F) {
                                    time = time - i;
                                    if (time > 84 / 60F) {
                                        Vec3 pos = wraithonGroundSplitjointRayDetection(livingEntityPatch, SwordSoaringArmatures.WRAITHON_ARMATURE.get().weapon, time, 2F, false, 1);
                                        if (pos == null) {
                                            return;
                                        }
                                        ParticleVFX.createDirectionalParticles(livingEntityPatch, pos, ParticleTypes.LARGE_SMOKE, 25, 1, 2, 200);
                                        livingEntity.playSound(EpicFightSounds.GROUND_SLAM.get(), 5, 1);
                                        destroyHemisphere(livingEntity.level(), BlockPos.containing(new Vec3(pos.x, livingEntity.getY(), pos.z)), 15);
                                        if (livingEntity.level().isClientSide) {
                                            CameraShake.shake(30, 3, 4.2f, pos, 40);
                                        }
                                        if (livingEntity.level() instanceof ServerLevel level) {
                                            LevelUtil.circleSlamFracture(livingEntity, level, pos, radius, true, false, false);
                                            AABB area = new AABB(pos.x() - radius, pos.y() - radius, pos.z() - radius, pos.x() + radius, pos.y() + radius, pos.z() + radius);
                                            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, entity -> entity.isAlive() && entity.distanceToSqr(pos) <= radius * radius && !(entity instanceof Player player && player.isCreative()) && entity != livingEntity && !(entity instanceof AbstractArtifactSpiritEntity) && !(livingEntity instanceof AbstractArtifactSpiritEntity artifactSpiritEntity && entity.equals(artifactSpiritEntity.getOwner())));
                                            for (LivingEntity entity : new ArrayList<>(entities)) {
                                                if (entity != null) {
                                                    LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                                    if (entityPatch != null) {
                                                        if (entityPatch.getArmature() instanceof HumanoidArmature) {
                                                            entityPatch.playAnimationSynchronized(WraithonAnimations.BIPE_KNOCK_FLY, 0.2F);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }), AnimationEvent.Side.BOTH));
            if (FMLEnvironment.dist == Dist.CLIENT) {
                animation.addProperty(ClientAnimationProperties.TRAIL_EFFECT, getWraithonTrails(80, 87, 0, 0));
            }
            return animation;
        }));

        DEBUG_ANIM_LIST = List.of(WRAITHON_1, WRAITHON_2, WRAITHON_3, WRAITHON_4, WRAITHON_5, WRAITHON_6, WRAITHON_7, WRAITHON_8, WRAITHON_9, WRAITHON_10, WRAITHON_11, WRAITHON_12, WRAITHON_13, WRAITHON_ROTATE_R_40, WRAITHON_ROTATE_R_60, WRAITHON_ROTATE_R_80, WRAITHON_ROTATE_R_100, WRAITHON_ROTATE_R_120, WRAITHON_ROTATE_R_140, WRAITHON_ROTATE_R_160, WRAITHON_ROTATE_R_180, WraithonAnimations.WRAITHON_ROTATE_L_40, WraithonAnimations.WRAITHON_ROTATE_L_60, WraithonAnimations.WRAITHON_ROTATE_L_80, WraithonAnimations.WRAITHON_ROTATE_L_100, WraithonAnimations.WRAITHON_ROTATE_L_120, WraithonAnimations.WRAITHON_ROTATE_L_140, WraithonAnimations.WRAITHON_ROTATE_L_160, WraithonAnimations.WRAITHON_ROTATE_L_180);
    }

    private static List<TrailInfo> getWraithonTrails(int startFrame1, int endFrame1, int startFrame2, int endFrame2) {
        float start1 = startFrame1 / 60F;
        float end1 = endFrame1 / 60F;
        float start2 = startFrame2 / 60F;
        float end2 = endFrame2 / 60F;
        List<TrailInfo> wraithonTrails = new ArrayList<>();
        wraithonTrails.add(TrailInfo.builder()
                .r(1.0F).b(1.0F).g(1.0F)
                .startPos(new Vec3(0.0, 3.5, 0))
                .endPos(new Vec3(0, 7, 0))
                .time(start1, end1)
                .lifetime(6)
                .interpolations(6)
                .updateInterval(1)
                .joint(SwordSoaringArmatures.WRAITHON_ARMATURE.get().weapon.getName())
                .itemSkinHand(InteractionHand.MAIN_HAND)
                .texture("sword_soaring:textures/particle/wraithon.png")
                .type((SimpleParticleType) ForgeRegistries.PARTICLE_TYPES.getValue(ResourceLocation.parse(Config.TRAIL_PARTICLE_TYPE.get())))
                .create());
        wraithonTrails.add(TrailInfo.builder()
                .r(1.0F).b(1.0F).g(1.0F)
                .startPos(new Vec3(0.0, 3.5, 0))
                .endPos(new Vec3(0, 7, 0))
                .time(start2, end2)
                .lifetime(6)
                .interpolations(6)
                .updateInterval(1)
                .joint(SwordSoaringArmatures.WRAITHON_ARMATURE.get().weapon.getName())
                .itemSkinHand(InteractionHand.MAIN_HAND)
                .texture("sword_soaring:textures/particle/wraithon.png")
                .type((SimpleParticleType) ForgeRegistries.PARTICLE_TYPES.getValue(ResourceLocation.parse(Config.TRAIL_PARTICLE_TYPE.get())))
                .create());
        return wraithonTrails;
    }

    private static AnimationEvent.InPeriodEvent<?> autoWraithonShockAtk(int startFrame, int endFrame, Joint joint, float radius) {
        float start = startFrame / 60F;
        float end = endFrame / 60F;
        return AnimationEvent.InPeriodEvent.create(start, end, (entityPatch, self, params) -> {
            wraithonShockAtk(entityPatch, radius, joint, start);
        }, AnimationEvent.Side.BOTH);
    }

    private static AnimationEvent.InPeriodEvent<?> autoWraithonGroundSplit(int startFrame, int endFrame, float radius, int particleCount) {
        float start = startFrame / 60F;
        float end = endFrame / 60F;
        return AnimationEvent.InPeriodEvent.create(start, end, (entityPatch, self, params) -> {
            wraithonGroundSplit(entityPatch, radius, WRAITHON_ATK_PARTICLE_COUNT, start);
        }, AnimationEvent.Side.BOTH);
    }

    private static AttackAnimation.Phase createSimplePhase(int startFrame, int endFrame) {
        float start = startFrame / 60F;
        float end = endFrame / 60F;
        Supplier<AttackAnimation.JointColliderPair[]> supplier = () -> {
            Armatures.ArmatureAccessor<WraithonArmature> armature = SwordSoaringArmatures.WRAITHON_ARMATURE;
            List<AttackAnimation.JointColliderPair> atkJoints = List.of(AttackAnimation.JointColliderPair.of(armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK_1),
                    AttackAnimation.JointColliderPair.of(armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK_2));
            return atkJoints.toArray(new AttackAnimation.JointColliderPair[0]);
        };
        return new AttackAnimation.Phase(0, start, start, end, end, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, supplier.get()
        );
    }

    private static void wraithonShockAtk(LivingEntityPatch<?> entityPatch, float radius, Joint joint, float starttime) {
        LivingEntity livingEntity = entityPatch.getOriginal();
        float damage = (float) livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.3F;
        float time = entityPatch.getAnimator().getPlayerFor(null).getElapsedTime();
        for (float i = 0; i <= 0.005; i = i + 0.0001F) {
            time = time - i;
            if (time > starttime) {
                Vec3 pos = wraithonShockjointRayDetection(entityPatch, joint, time, 2F, false);

                if (pos != null) {
                    if (livingEntity.level() instanceof ServerLevel level) {
                        for (int dy = -1; dy <= 0; dy++) {
                            Vec3 newPos = pos.add(0, dy, 0);
                            LevelUtil.circleSlamFracture(livingEntity, level, newPos, radius, false, false, false);
                            AABB area = new AABB(pos.x() - radius, pos.y() - radius, pos.z() - radius, pos.x() + radius, pos.y() + radius, pos.z() + radius);
                            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, entity -> entity.isAlive() && entity.distanceToSqr(pos) <= radius * radius && !(entity instanceof Player player && player.isCreative()) && entity != livingEntity && !(entity instanceof AbstractArtifactSpiritEntity) && !(livingEntity instanceof AbstractArtifactSpiritEntity artifactSpiritEntity && entity.equals(artifactSpiritEntity.getOwner())));
                            for (LivingEntity entity : new ArrayList<>(entities)) {
                                if (entity.invulnerableTime >= 0) {
                                    entity.invulnerableTime = 0;
                                    entity.hurt(entity.damageSources().mobAttack(livingEntity), damage * 0.5F);
                                    entity.invulnerableTime = 0;
                                    LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                    if (livingEntityPatch != null) {
                                        if (livingEntityPatch.getArmature() instanceof HumanoidArmature) {
                                            livingEntityPatch.playAnimationSynchronized(WraithonAnimations.BIPE_KNOCK_FLY, 0.2F);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private static void wraithonGroundSplit(LivingEntityPatch<?> entityPatch, float radius, int particleCount, float starttime) {
        LivingEntity entity = entityPatch.getOriginal();
        float time = entityPatch.getAnimator().getPlayerFor(null).getElapsedTime();
        for (float i = 0; i <= 0.005; i = i + 0.0001F) {
            time = time - i;
            if (time > starttime) {
                Vec3 pos = wraithonGroundSplitjointRayDetection(entityPatch, SwordSoaringArmatures.WRAITHON_ARMATURE.get().weapon, time, 2F, true, particleCount);
                if (pos != null) {
                    if (entity.level() instanceof ServerLevel level) {
                        for (int dy = -1; dy <= 1; dy++) {
                            Vec3 newPos = pos.add(0, dy, 0);
                            LevelUtil.circleSlamFracture(entity, level, newPos, radius, true, true, false);
                        }
                    }
                }
            }
        }
    }

    private static Vec3 wraithonShockjointRayDetection(LivingEntityPatch<?> livingEntityPatch, Joint joint, float time, float distance, boolean defaultEndpoint) {
        LivingEntity entity = livingEntityPatch.getOriginal();
        Pose pose = livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation().get().getRawPose(time);
        OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBindedTransformFor(pose, joint);
        WraithonEntityPatch wraithonEntityPatch = (WraithonEntityPatch) livingEntityPatch;
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(180.0F + wraithonEntityPatch.getOriginal().getCorrectYRot(1.0F)), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f rotatedMatrix = new OpenMatrix4f();
        OpenMatrix4f.mul(rotation, transformMatrix, rotatedMatrix);

        float sign = Math.signum(distance);
        float absoluteDistance = Math.abs(distance);
        for (int i = 0; i * 0.1 < absoluteDistance; i++) {
            OpenMatrix4f currentTransform = new OpenMatrix4f(rotatedMatrix);
            currentTransform.translate(new Vec3f(0.0F, i * 0.1 * sign, 0F));
            Vec3 pos = new Vec3(
                    currentTransform.m30 * WraithonEntityPatch.SCALE + (float) entity.getX(),
                    currentTransform.m31 * WraithonEntityPatch.SCALE + (float) entity.getY(),
                    currentTransform.m32 * WraithonEntityPatch.SCALE + (float) entity.getZ()
            );
            BlockPos center = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
            if (checkRadiusBlocks(entity, center, 1)) {
                return pos;
            }
        }
        if (defaultEndpoint) {
            OpenMatrix4f endTransform = new OpenMatrix4f(rotatedMatrix);
            endTransform.translate(new Vec3f(0.0F, absoluteDistance, 0.0F));
            Vec3 endPos = new Vec3(
                    endTransform.m30 * WraithonEntityPatch.SCALE + (float) entity.getX(),
                    endTransform.m31 * WraithonEntityPatch.SCALE + (float) entity.getY(),
                    endTransform.m32 * WraithonEntityPatch.SCALE + (float) entity.getZ()
            );
            return endPos;
        }
        return null;
    }

    private static Vec3 wraithonGroundSplitjointRayDetection(LivingEntityPatch<?> livingEntityPatch, Joint joint, float time, float distance, boolean defaultEndpoint, int particleCount) {
        LivingEntity entity = livingEntityPatch.getOriginal();
        Pose pose = livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation().get().getRawPose(time);
        OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBindedTransformFor(pose, joint);
        WraithonEntityPatch wraithonEntityPatch = (WraithonEntityPatch) livingEntityPatch;
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(180.0F + wraithonEntityPatch.getOriginal().getCorrectYRot(1.0F)), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f rotatedMatrix = new OpenMatrix4f();
        OpenMatrix4f.mul(rotation, transformMatrix, rotatedMatrix);

        float sign = Math.signum(distance);
        float absoluteDistance = Math.abs(distance);
        for (int i = 0; i * 0.1 < absoluteDistance; i++) {
            OpenMatrix4f currentTransform = new OpenMatrix4f(rotatedMatrix);
            currentTransform.translate(new Vec3f(0.0F, 4.7 + i * 0.1 * sign, 0.0F));
            Vec3 pos = new Vec3(
                    currentTransform.m30 * WraithonEntityPatch.SCALE + (float) entity.getX(),
                    currentTransform.m31 * WraithonEntityPatch.SCALE + (float) entity.getY(),
                    currentTransform.m32 * WraithonEntityPatch.SCALE + (float) entity.getZ()
            );
            if (particleCount > 0) {
                ParticleVFX.createRandomInSphereParticles(entity.level(), pos, ParticleTypes.LARGE_SMOKE, 0.8, 0.01, 0.05, particleCount);
            }
            BlockPos center = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
            if (checkRadiusBlocks(entity, center, 1)) {
                return pos;
            }
        }
        if (defaultEndpoint) {
            OpenMatrix4f endTransform = new OpenMatrix4f(rotatedMatrix);
            endTransform.translate(new Vec3f(0.0F, 4.7 + absoluteDistance, 0.0F));
            Vec3 endPos = new Vec3(
                    endTransform.m30 * WraithonEntityPatch.SCALE + (float) entity.getX(),
                    endTransform.m31 * WraithonEntityPatch.SCALE + (float) entity.getY(),
                    endTransform.m32 * WraithonEntityPatch.SCALE + (float) entity.getZ()
            );
            return endPos;
        }
        return null;
    }

    private static boolean checkRadiusBlocks(LivingEntity entity, BlockPos center, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = center.offset(x, y, z);
                    if (entity.level().getBlockState(checkPos).isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void destroyHemisphere(Level world, BlockPos center, int radius) {
        if (world.isClientSide) return;
        int x0 = center.getX();
        int y0 = center.getY();
        int z0 = center.getZ();
        int radiusSquared = radius * radius;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = 0; dy <= radius; dy++) {
                    int x = x0 + dx;
                    int y = y0 + dy;
                    int z = z0 + dz;
                    if (dx * dx + dy * dy + dz * dz <= radiusSquared) {
                        BlockPos pos = new BlockPos(x, y, z);
                        world.destroyBlock(pos, false);
                    }
                }
            }
        }
    }


}
