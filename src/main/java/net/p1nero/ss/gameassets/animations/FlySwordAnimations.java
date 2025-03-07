package net.p1nero.ss.gameassets.animations;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.animation.AutoDiscardAttackAnimation;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordArmature;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;

public class FlySwordAnimations {
    public static StaticAnimation FLY_SWORD_ATK_1;
    public static StaticAnimation FLY_SWORD_ATK_2;
    public static StaticAnimation FLY_SWORD_ATK_3;
    public static StaticAnimation FLY_SWORD_ATK_4_1;
    public static StaticAnimation FLY_SWORD_ATK_4_2;
    public static StaticAnimation FLY_SWORD_ATK_4_3;
    public static StaticAnimation FLY_SWORD_ATK_4_4;
    public static StaticAnimation FLY_SWORD_ATK_IDLE;
    public static StaticAnimation FLY_SWORD_ATK_FLY;
    public static StaticAnimation FLY_SWORD_ATK_FLY_BACK;
    public static AnimationEvent SET_ANIMATION_END = AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
        if(livingEntityPatch.getOriginal() instanceof FlySwordEntity flySwordEntity){
            flySwordEntity.setAnimationEnd(true);
        }
    }, AnimationEvent.Side.SERVER);
    public static void buildFlySwordAnim() {
        FlySwordArmature flySwordArmature = SwordSoaringArmatures.flySwordArmature;
        FLY_SWORD_ATK_1 = new AttackAnimation(0.15F, "fly_sword/fly_sword_atk_1", flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.2F, 0.2F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.2F, 0.35F, 0.42F, 0.42F, 0.42F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.42F, 0.8F, 0.9F, 0.9F, 0.9F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.9F, 1.1F, 1.3F, 1.3F, 1.3F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.9F, 1.1F, 1.3F, 1.3F, 1.3F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(1.3F, 1.3F, 1.5F, 1.5F, 1.5F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(1.5F, 1.9F, 2.0F, 2.0F, 2.0F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, SET_ANIMATION_END)
                .addEvents(AnimationEvent.TimeStampedEvent.create(1.3F, ((livingEntityPatch, staticAnimation, objects) -> {
                    VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, VatanseverAnimations.getTotalAttackDamage(livingEntityPatch)*5, 3, 500);
                }), AnimationEvent.Side.BOTH));
        FLY_SWORD_ATK_2 = new AttackAnimation(0.15F, "fly_sword/fly_sword_atk_2", flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.2F, 0.2F, 0.2F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.2F, 0.35F, 0.45F, 0.45F, 0.45F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.45F, 0.7F, 0.7F, 0.7F, 0.7F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.7F, 0.9F, 1.1F, 1.1F, 1.1F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(1.1F, 1.3F, 1.5167F, 1.5167F, 1.5167F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, SET_ANIMATION_END)
                .addEvents(AnimationEvent.TimeStampedEvent.create(1.3F, ((livingEntityPatch, staticAnimation, objects) -> {
                    VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, VatanseverAnimations.getTotalAttackDamage(livingEntityPatch)*5, 3, 500);
                }), AnimationEvent.Side.BOTH));
        FLY_SWORD_ATK_3 = new AutoDiscardAttackAnimation(0.15F, "fly_sword/fly_sword_atk_3", flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.17F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if(livingEntityPatch.getOriginal() instanceof FlySwordEntity flySwordEntity){
                        flySwordEntity.setRotationLock(false);
                        flySwordEntity.setGlowingTag(true);
                    }
                }, AnimationEvent.Side.SERVER));
        FLY_SWORD_ATK_4_1 = new AutoDiscardAttackAnimation(0.15F, "fly_sword/fly_sword_atk_4_1", flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true), AnimationEvent.Side.SERVER));
        FLY_SWORD_ATK_4_2 = new AutoDiscardAttackAnimation(0.15F, "fly_sword/fly_sword_atk_4_2", flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true), AnimationEvent.Side.SERVER));
        FLY_SWORD_ATK_4_3 = new AutoDiscardAttackAnimation(0.15F, "fly_sword/fly_sword_atk_4_3", flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true), AnimationEvent.Side.SERVER));
        FLY_SWORD_ATK_4_4 = new AutoDiscardAttackAnimation(0.15F, "fly_sword/fly_sword_atk_4_4", flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true), AnimationEvent.Side.SERVER));
        FLY_SWORD_ATK_IDLE = new StaticAnimation(true, "fly_sword/fly_sword_idle", flySwordArmature);
        FLY_SWORD_ATK_FLY = new StaticAnimation(true, "fly_sword/fly_sword_fly", flySwordArmature);
        FLY_SWORD_ATK_FLY_BACK = new ActionAnimation(0.15F, "fly_sword/fly_sword_back", flySwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if(livingEntityPatch.getOriginal() instanceof FlySwordEntity flySwordEntity){
                        flySwordEntity.setFlyingBack(true);
                    }
                }, AnimationEvent.Side.SERVER));
    }

    public static void flySwordDamage(FlySwordPatch flySwordPatch, float attractRadius, float damageRadius){
        LivingEntityPatch<?> ownerPatch = flySwordPatch.getOwnerPatch();
        if(ownerPatch == null){
            return;
        }
        LivingEntity sword = flySwordPatch.getOriginal();
        LivingEntity source = ownerPatch.getOriginal();
        double baseDamage = source.getAttributeValue(Attributes.ATTACK_DAMAGE)*3;

        Vec3 Pos = sword.position();
        if(sword.level instanceof ServerLevel level){

            AABB area = new AABB(
                    Pos.x - attractRadius, Pos.y - attractRadius, Pos.z - attractRadius,
                    Pos.x + attractRadius, Pos.y + attractRadius, Pos.z + attractRadius);

            sword.level.getEntitiesOfClass(Entity.class, area).forEach(entity -> {
                if (entity == sword) return;
                if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) return;

                Vec3 entityPos = entity.position();
                Vec3 delta = Pos.subtract(entityPos);
                double distance = delta.length();

                if (distance > 1.0) {
                    Vec3 direction = delta.normalize();
                    double speed = 0.5;
                    entity.setDeltaMovement(entity.getDeltaMovement().add(direction.scale(speed)));
                } else {
                    Vec3 safePos = Pos.subtract(delta.normalize().scale(1.0));
                    entity.setPos(safePos.x, safePos.y, safePos.z);
                    entity.setDeltaMovement(Vec3.ZERO);
                }
            });
            AABB damageArea = new AABB(
                    Pos.x() - damageRadius, Pos.y() - damageRadius, Pos.z() - damageRadius, Pos.x() + damageRadius, Pos.y() + damageRadius, Pos.z() + damageRadius
            );
            //来源实体过滤
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, damageArea, entity ->
                    entity.isAlive() && entity.distanceToSqr(Pos) <= damageRadius * damageRadius && !(entity instanceof Player player && player.isCreative()) && entity != source && !(entity instanceof AbstractArtifactSpiritEntity)
            );
            for (LivingEntity entity : new ArrayList<>(entities)) {
                if (entity.invulnerableTime == 0) {
                    entity.hurt(DamageSource.indirectMagic(source, source), (float) baseDamage);
                    entity.invulnerableTime = 10;
                    if (!entity.level.isClientSide) {
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
                    }
                }
            }
        }
    }
}
