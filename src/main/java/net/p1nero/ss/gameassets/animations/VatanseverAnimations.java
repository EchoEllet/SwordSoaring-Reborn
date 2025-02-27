package net.p1nero.ss.gameassets.animations;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.animation.LinkArtifactSpiritAnimation;
import net.p1nero.ss.entity.vatansever.VatanseverArmature;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
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
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VatanseverAnimations {
    public static StaticAnimation PLAYER_AUTO1;
    public static StaticAnimation PLAYER_AUTO2;
    public static StaticAnimation PLAYER_AUTO3;
    public static StaticAnimation PLAYER_AUTO3_B;
    public static StaticAnimation PLAYER_AUTO4;
    public static StaticAnimation PLAYER_AUTO4_B;
    public static StaticAnimation PLAYER_INIT;
    public static StaticAnimation PLAYER_FLY_BEGIN;
    public static StaticAnimation PLAYER_STORM_START;

    public static StaticAnimation VATANSEVER_IDLE;
    public static StaticAnimation VATANSEVER_WALK;
    public static StaticAnimation VATANSEVER_WALK_F;
    public static StaticAnimation VATANSEVER_WALK_F_STOP;
    public static StaticAnimation VATANSEVER_WALK_B;
    public static StaticAnimation VATANSEVER_WALK_B_STOP;
    public static StaticAnimation VATANSEVER_RUN;
    public static StaticAnimation VATANSEVER_RUN_STOP;
    public static StaticAnimation VATANSEVER_DEATH;
    public static StaticAnimation VATANSEVER_FALL;
    public static StaticAnimation VATANSEVER_FLOAT;
    public static StaticAnimation VATANSEVER_FLY;
    public static StaticAnimation VATANSEVER_FLY_STOP;
    public static StaticAnimation VATANSEVER_SNEAK;
    public static StaticAnimation VATANSEVER_SNEAK_STOP;
    public static StaticAnimation VATANSEVER_SWIM;
    public static StaticAnimation VATANSEVER_AUTO1;
    public static StaticAnimation VATANSEVER_AUTO2;
    public static StaticAnimation VATANSEVER_AUTO3;
    public static StaticAnimation VATANSEVER_AUTO3_B;
    public static StaticAnimation VATANSEVER_AUTO4;
    public static StaticAnimation VATANSEVER_AUTO4_B;
    public static StaticAnimation VATANSEVER_INIT;
    public static StaticAnimation VATANSEVER_FLY_BEGIN;
    public static StaticAnimation VATANSEVER_STORM_START;

    public static void buildVatanseverAnim() {
        VatanseverArmature vatanseverArmature = SwordSoaringArmatures.vatanseverArmature;
        List<Pair<Joint, Collider>> left = List.of(Pair.of(vatanseverArmature.L1, SwordSoaringColliders.VATANSEVER),
                Pair.of(vatanseverArmature.L2, SwordSoaringColliders.VATANSEVER),
                Pair.of(vatanseverArmature.L3, SwordSoaringColliders.VATANSEVER));
        List<Pair<Joint, Collider>> right = List.of(Pair.of(vatanseverArmature.R1, SwordSoaringColliders.VATANSEVER),
                Pair.of(vatanseverArmature.R2, SwordSoaringColliders.VATANSEVER),
                Pair.of(vatanseverArmature.R3, SwordSoaringColliders.VATANSEVER));
        ArrayList<Pair<Joint, Collider>> all = new ArrayList<>();
        all.addAll(left);
        all.addAll(right);

        VATANSEVER_IDLE = new StaticAnimation(true, "biped/vatansever/living/vatansever_idle", vatanseverArmature);
        VATANSEVER_WALK_F = new StaticAnimation(true, "biped/vatansever/living/vatansever_walk", vatanseverArmature);
        VATANSEVER_WALK_F_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_walk_stop", vatanseverArmature);
        VATANSEVER_WALK_B = new StaticAnimation(true, "biped/vatansever/living/vatansever_walk_b", vatanseverArmature);
        VATANSEVER_WALK_B_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_walk_b_stop", vatanseverArmature);
        VATANSEVER_WALK = new SelectiveAnimation((entityPatch) -> {
            if (entityPatch instanceof VatanseverEntityPatch vatanseverEntityPatch && vatanseverEntityPatch.getOwnerPatch() != null) {
                Vec3 view = vatanseverEntityPatch.getOwnerPatch().getOriginal().getViewVector(1.0F);
                Vec3 move = vatanseverEntityPatch.getOwnerPatch().getOriginal().getDeltaMovement();
                double dot = view.dot(move);
                return dot < 0.0 ? 1 : 0;
            }
            return 0;
        }, VATANSEVER_WALK_F, VATANSEVER_WALK_B);
        VATANSEVER_RUN = new StaticAnimation(true, "biped/vatansever/living/vatansever_run", vatanseverArmature);
        VATANSEVER_RUN_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_run_stop", vatanseverArmature);
        VATANSEVER_SWIM = new StaticAnimation(true, "biped/vatansever/living/vatansever_swim", vatanseverArmature);
        VATANSEVER_FALL = new StaticAnimation(true, "biped/vatansever/living/vatansever_fall", vatanseverArmature);
        VATANSEVER_DEATH = new StaticAnimation(true, "biped/vatansever/living/vatansever_death", vatanseverArmature);
        VATANSEVER_FLOAT = new StaticAnimation(true, "biped/vatansever/living/vatansever_float", vatanseverArmature);
        VATANSEVER_FLY = new StaticAnimation(true, "biped/vatansever/living/vatansever_fly", vatanseverArmature)
                .addEvents(AnimationEvent.TimePeriodEvent.create(0, 3, (entityPatch, self, params) -> flyVFX(entityPatch), AnimationEvent.Side.CLIENT));
        VATANSEVER_FLY_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_fly_stop", vatanseverArmature);
        VATANSEVER_SNEAK = new StaticAnimation(true, "biped/vatansever/living/vatansever_sneak", vatanseverArmature);
        VATANSEVER_SNEAK_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_sneak_stop", vatanseverArmature);
        VATANSEVER_AUTO1 = new AttackAnimation(0.01F, "biped/vatansever/vatansever_auto1", vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 0.7F, 0.7F, 1.1F, 1.1F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, right)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO2 = new AttackAnimation(0.01F, "biped/vatansever/vatansever_auto2", vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 0.7F, 0.7F, 1.0F, 1.60F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, left)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)),
                new AttackAnimation.Phase(1.63F, 1.7F, 1.2F, 1.9F, 1.9F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, left)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO3 = new AttackAnimation(0.01F, "biped/vatansever/vatansever_auto3", vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 0.9F, 0.9F, 1.2F, 1.2F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, right)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO3_B = new AttackAnimation(0.01F, "biped/vatansever/vatansever_auto3_b", vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 0.9F, 0.9F, 1.2F, 1.2F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, right)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO4 = new AttackAnimation(0.01F, "biped/vatansever/vatansever_auto4", vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 1.33F, 1.33F, 1.43F, 4.0F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, all)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO4_B = new AttackAnimation(0.01F, "biped/vatansever/vatansever_auto4_b", vatanseverArmature,
                new AttackAnimation.Phase(0.0F, 1.33F, 1.33F, 1.43F, 4.0F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, all)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_STORM_START = new ActionAnimation(0.15F, "biped/vatansever/skill/vatansever_storm_start", vatanseverArmature);
        VATANSEVER_INIT = new ActionAnimation(0.15F, "biped/vatansever/vatansever_init", vatanseverArmature);
        VATANSEVER_FLY_BEGIN = new ActionAnimation(0.15F, "biped/vatansever/vatansever_fly_begin", vatanseverArmature)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addEvents(AnimationEvent.TimePeriodEvent.create(0, 3, (entityPatch, self, params) -> flyVFX(entityPatch), AnimationEvent.Side.CLIENT));

        HumanoidArmature biped = Armatures.BIPED;
        PLAYER_AUTO1 = new LinkArtifactSpiritAnimation(0.15F, 1.1F, "biped/vatansever/vatansever_auto1_owner", biped, VATANSEVER_AUTO1)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false);
        PLAYER_AUTO2 = new LinkArtifactSpiritAnimation(0.15F, 1.9F, "biped/vatansever/vatansever_auto2_owner", biped, VATANSEVER_AUTO2)
                .newTimePair(1.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true)
                .addEvents(AnimationEvent.TimeStampedEvent.create(1.56F, ((livingEntityPatch, staticAnimation, objects) -> {
                            groundSplit(livingEntityPatch, 3, 0, 0, 0, 99999, 1.1F, 200);
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(1.65F, ((livingEntityPatch, staticAnimation, objects) -> {
                            groundSplit(livingEntityPatch, 3.8, 0, 0, 0, 99999, 1.1F, 200);
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(1.74F, ((livingEntityPatch, staticAnimation, objects) -> {
                            groundSplit(livingEntityPatch, 5, 0, 0, 0, 99999, 1.1F, 200);
                        }), AnimationEvent.Side.BOTH));
        PLAYER_AUTO3 = new LinkArtifactSpiritAnimation(0.15F, 2.25F, "biped/vatansever/vatansever_auto3_owner", biped, VATANSEVER_AUTO3)
                .newTimePair(0.0F, 3.0F)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .addEvents(AnimationEvent.TimeStampedEvent.create(1F, ((livingEntityPatch, staticAnimation, objects) -> {
                            groundSplit(livingEntityPatch, 3, 0, 0, 0, 99999, 1.1F, 200);
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(1.09F, ((livingEntityPatch, staticAnimation, objects) -> {
                            groundSplit(livingEntityPatch, 3.8, 0, 0, 0, 99999, 1.1F, 200);
                        }), AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(1.18F, ((livingEntityPatch, staticAnimation, objects) -> {
                            groundSplit(livingEntityPatch, 5, 0, 0, 0, 99999, 1.1F, 200);
                        }), AnimationEvent.Side.BOTH));
        PLAYER_AUTO3_B = new LinkArtifactSpiritAnimation(0.15F, 2.25F, "biped/vatansever/vatansever_auto3_b_owner", biped, VATANSEVER_AUTO3_B)
                .newTimePair(0.0F, 3.0F)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true);
        PLAYER_AUTO4 = new LinkArtifactSpiritAnimation(0.15F, 4F, "biped/vatansever/vatansever_auto4_owner", biped, VATANSEVER_AUTO4)
                .newTimePair(1.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .addEvents(AnimationEvent.TimeStampedEvent.create(1.38F, ((livingEntityPatch, staticAnimation, objects) -> {
                    groundSplit(livingEntityPatch, 4.2, 0, 0, 0, 99999, 5, 2000);
                }), AnimationEvent.Side.BOTH));
        PLAYER_AUTO4_B = new LinkArtifactSpiritAnimation(0.15F, 4F, "biped/vatansever/vatansever_auto4_b_owner", biped, VATANSEVER_AUTO4_B)
                .newTimePair(1.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true);
        PLAYER_INIT = new LinkArtifactSpiritAnimation(0.15F, "biped/vatansever/vatansever_init", biped, VATANSEVER_INIT)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false)
                .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, false);
        PLAYER_FLY_BEGIN = new ActionAnimation(0.15F, "biped/vatansever/vatansever_fly_begin", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if(livingEntityPatch.getOriginal() instanceof ServerPlayer serverPlayer){
                        serverPlayer.startFallFlying();
                    }
                }, AnimationEvent.Side.SERVER));
        PLAYER_STORM_START = new LinkArtifactSpiritAnimation(0.15F, "biped/vatansever/skill/vatansever_storm_start", biped, VATANSEVER_STORM_START)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.5F, ((livingEntityPatch, staticAnimation, objects) -> {
                    groundSplit(livingEntityPatch, 0, 0, 0, 0, 0, 3, 2000);
                    createStorm(livingEntityPatch, 0, 18, 0, VatanseverStormAnimations.VATANSEVER_STORM_UP);
                    createStorm(livingEntityPatch, 0, 20, 0, VatanseverStormAnimations.VATANSEVER_STORM_MIDDLE);
                    createStorm(livingEntityPatch, 0, 20, 0, VatanseverStormAnimations.VATANSEVER_STORM_MIDDLE_2);
                    createStorm(livingEntityPatch, 0, 22, 0, VatanseverStormAnimations.VATANSEVER_STORM_DOWN);
                }), AnimationEvent.Side.BOTH));
    }

    public static void createStorm(LivingEntityPatch<?> entityPatch, double xOffset, double yOffset, double zOffset, StaticAnimation staticAnimation) {
        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
            ServerPlayer serverPlayer = serverPlayerPatch.getOriginal();
            Vec3 pos = new Vec3(serverPlayer.getX() + xOffset, serverPlayer.getY() + yOffset, serverPlayer.getZ() + zOffset);
            VatanseverStormEntity stormEntity = new VatanseverStormEntity(serverPlayer.level, serverPlayer, pos);
            serverPlayer.level.addFreshEntity(stormEntity);
            stormEntity.setYRot(serverPlayer.getYRot());
            EpicFightCapabilities.getEntityPatch(stormEntity, VatanseverStormEntityPatch.class).playAnimationSynchronized(staticAnimation, 0.05F);
        }

    }

    public static void groundSplit(LivingEntityPatch<?> entityPatch, double viewOffset, double xOffset, double yOffset, double zOffset, float damage, float radius, int particleCount) {
        LivingEntity entity = entityPatch.getOriginal();
        Vec3 pos = entity.position();
        Vec3 dir = entity.getViewVector(1).normalize().scale(viewOffset);
        Vec3 target = pos.add(dir.x + xOffset, -1 + yOffset, dir.z + zOffset);
        Vec3 damagetarget = pos.add(dir.x + xOffset,yOffset, dir.z + zOffset);
        if(entity.level instanceof ServerLevel level){
            LevelUtil.circleSlamFracture(entity, level, target, radius);
            dealAreaDamage(level,damagetarget, entity, damage, radius);
        } else {
            createRandomSmokeLine(entity.level, target, particleCount);
        }
    }

    private static void jet(VatanseverEntityPatch vatanseverEntityPatch, Joint toolJoint, int particleCount) {
        VatanseverEntity vatanseverEntity = vatanseverEntityPatch.getOriginal();
        if (vatanseverEntity.getOwner() == null) {
            return;
        }
        Level world = vatanseverEntity.level;
        // 获取骨骼变换矩阵
        OpenMatrix4f transformMatrix = vatanseverEntityPatch.getArmature()
                .getBindedTransformFor(
                        vatanseverEntityPatch.getArmature().getCurrentPose(),
                        toolJoint
                );

        // 初始变换（位置偏移和基础旋转）
        transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(
                -(float) Math.toRadians(vatanseverEntityPatch.getOriginal().yBodyRot + 180.0F),
                new Vec3f(0.0F, 1.0F, 0.0F)
        );
        OpenMatrix4f.mul(rotation, transformMatrix, transformMatrix);

        // 提取旋转后的 Y 轴方向作为基础速度
        Vec3f baseVelocity = new Vec3f(transformMatrix.m10 * 0.1F, transformMatrix.m11 * 0.1F, transformMatrix.m12 * 0.1F);

        // 获取玩家当前速度
        Vec3 playerVelocity = vatanseverEntity.getOwner().getDeltaMovement();
        Vec3f relativeVelocity = new Vec3f(
                (float) playerVelocity.x + baseVelocity.x,
                (float) playerVelocity.y + baseVelocity.y,
                (float) playerVelocity.z + baseVelocity.z
        );

        // 生成粒子
        for (int i = 0; i < 5 * particleCount; i++) {
            world.addParticle(
                    ParticleTypes.CLOUD,
                    transformMatrix.m30 + (float) vatanseverEntity.getX(),
                    transformMatrix.m31 + (float) vatanseverEntity.getY(),
                    transformMatrix.m32 + (float) vatanseverEntity.getZ(),
                    relativeVelocity.x,
                    relativeVelocity.y,
                    relativeVelocity.z
            );
        }
        for (int i = 0; i < 3 * particleCount; i++) {
            world.addParticle(
                    ParticleTypes.END_ROD,
                    transformMatrix.m30 + (float) vatanseverEntity.getX(),
                    transformMatrix.m31 + (float) vatanseverEntity.getY(),
                    transformMatrix.m32 + (float) vatanseverEntity.getZ(),
                    relativeVelocity.x,
                    relativeVelocity.y,
                    relativeVelocity.z
            );
        }
    }

    public static void flyVFX(LivingEntityPatch<?> entityPatch) {
        int particleCount = 1;
        Level world = entityPatch.getOriginal().level;
        if (entityPatch instanceof VatanseverEntityPatch vatanseverEntityPatch && world.isClientSide) {
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.L1, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.L2, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.L3, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.R1, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.R2, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.R3, particleCount);
        }
    }

    private static final double MIN_SPEED1 = 0.1;
    private static final double MAX_SPEED1 = 0.5;

    private static void createRandomSmokeLine(Level level, Vec3 center, int particleCount) {
        Random random1 = level.random;

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
            level.addParticle(ParticleTypes.SMOKE,
                    x, y, z,
                    offsetX * speed, offsetY * speed, offsetZ * speed
            );
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
                entity.isAlive() && entity.distanceToSqr(center) <= radius * radius && !(entity instanceof Player player && player.isCreative()) && entity != source  // 排除执行者
        );
        //线程安全迭代
        for (LivingEntity entity : new ArrayList<>(entities)) {
            if (entity.invulnerableTime >= 0 && source != null) {
                entity.invulnerableTime = 0;
                entity.hurt(DamageSource.indirectMagic(source, source), damage);
                entity.invulnerableTime = 0;
            }
        }
    }

}
