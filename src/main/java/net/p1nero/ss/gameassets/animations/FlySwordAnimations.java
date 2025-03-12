package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.animation.AutoDiscardActionAnimation;
import net.p1nero.ss.animation.AutoDiscardAttackAnimation;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordArmature;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;

import java.util.List;

@SuppressWarnings("rawtypes")
public class FlySwordAnimations {
    public static AnimationManager.AnimationAccessor<AttackAnimation> FLY_SWORD_ATK_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FLY_SWORD_ATK_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FLY_SWORD_ATK_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FLY_SWORD_ATK_4_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FLY_SWORD_ATK_4_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FLY_SWORD_ATK_4_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FLY_SWORD_ATK_4_4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FLY_SWORD_ATK_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FLY_SWORD_ATK_FLY;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FLY_SWORD_ATK_FLY_BACK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FLY_SWORD_WAN_1;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FLY_SWORD_WAN_2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FLY_SWORD_WAN_3;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FLY_SWORD_WAN_4;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FLY_SWORD_WAN_5;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FLY_SWORD_WAN_6;
    public static List<AnimationManager.AnimationAccessor<ActionAnimation>> WAN_ANIMATIONS;
    public static AnimationEvent SET_ANIMATION_END = AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> {
        if (livingEntityPatch.getOriginal() instanceof FlySwordEntity flySwordEntity) {
            flySwordEntity.setAnimationEnd(true);
        }
    }, AnimationEvent.Side.SERVER);

    public static void buildFlySwordAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<FlySwordArmature> flySwordArmature = SwordSoaringArmatures.FLY_SWORD_ARMATURE;
        FLY_SWORD_ATK_1 = builder.nextAccessor("fly_sword/fly_sword_atk_1", accessor -> new AttackAnimation(0.15F, accessor, flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.2F, 0.2F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.2F, 0.35F, 0.42F, 0.42F, 0.42F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.42F, 0.42F, 0.62F, 0.62F, 0.62F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.62F, 0.72F, 0.9F, 0.9F, 0.9F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.9F, 1.1F, 1.3F, 1.3F, 1.3F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.9F, 1.1F, 1.3F, 1.3F, 1.3F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(1.3F, 1.3F, 1.5F, 1.5F, 1.5F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, SET_ANIMATION_END)
                .addEvents(AnimationEvent.InTimeEvent.create(1.3F, ((livingEntityPatch, staticAnimation, objects) ->
                        VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, VatanseverAnimations.getTotalAttackDamage(livingEntityPatch) * 5, 3, 500)), AnimationEvent.Side.BOTH)));
        
        FLY_SWORD_ATK_2 = builder.nextAccessor("fly_sword/fly_sword_atk_2", accessor -> new AttackAnimation(0.15F, accessor, flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.2F, 0.2F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.2F, 0.2F, 0.4F, 0.4F, 0.4F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.4F, 0.4F, 0.6F, 0.6F, 0.6F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.6F, 0.6F, 0.8F, 0.8F, 0.8F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.8F, 0.8F, 1.0F, 1.0F, 1.0F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(1.0F, 1.0F, 1.3F, 1.3F, 1.3F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(1.3F, 1.3F, 1.5167F, 1.5167F, 1.5167F, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.5F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, SET_ANIMATION_END)
                .addEvents(AnimationEvent.InTimeEvent.create(1.3F, ((livingEntityPatch, staticAnimation, objects) ->
                        VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, VatanseverAnimations.getTotalAttackDamage(livingEntityPatch) * 5, 3, 500)), AnimationEvent.Side.BOTH)));
        FLY_SWORD_ATK_3 = builder.nextAccessor("fly_sword/fly_sword_atk_3", accessor -> new AutoDiscardAttackAnimation(0.15F, accessor, flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.17F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch.getOriginal() instanceof FlySwordEntity flySwordEntity) {
                        flySwordEntity.setRotationLock(false);
                        flySwordEntity.setGlowingTag(true);
                    }
                }, AnimationEvent.Side.SERVER)));
        FLY_SWORD_ATK_4_1 = builder.nextAccessor("fly_sword/fly_sword_atk_4_1", accessor -> new AutoDiscardAttackAnimation(0.15F, accessor, flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true), AnimationEvent.Side.SERVER)));
        FLY_SWORD_ATK_4_2 = builder.nextAccessor("fly_sword/fly_sword_atk_4_2", accessor -> new AutoDiscardAttackAnimation(0.15F, accessor, flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true), AnimationEvent.Side.SERVER)));
        FLY_SWORD_ATK_4_3 = builder.nextAccessor("fly_sword/fly_sword_atk_4_3", accessor -> new AutoDiscardAttackAnimation(0.15F, accessor, flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true), AnimationEvent.Side.SERVER)));
        FLY_SWORD_ATK_4_4 = builder.nextAccessor("fly_sword/fly_sword_atk_4_4", accessor -> new AutoDiscardAttackAnimation(0.15F, accessor, flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 1, 1, 1, flySwordArmature.get().body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true), AnimationEvent.Side.SERVER)));
        FLY_SWORD_ATK_IDLE = builder.nextAccessor("fly_sword/fly_sword_idle", accessor ->  new StaticAnimation(true, accessor, flySwordArmature));
        FLY_SWORD_ATK_FLY = builder.nextAccessor("fly_sword/fly_sword_fly", accessor -> new StaticAnimation(true, accessor, flySwordArmature));
        FLY_SWORD_ATK_FLY_BACK = builder.nextAccessor("fly_sword/fly_sword_back", accessor -> new ActionAnimation(0.15F, accessor, flySwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch.getOriginal() instanceof FlySwordEntity flySwordEntity) {
                        flySwordEntity.setFlyingBack(true);
                    }
                }, AnimationEvent.Side.SERVER)));

        FLY_SWORD_WAN_1 = builder.nextAccessor("fly_sword/fly_sword_wan_1", accessor -> new AutoDiscardActionAnimation(0.15F, accessor, flySwordArmature));
        FLY_SWORD_WAN_2 = builder.nextAccessor("fly_sword/fly_sword_wan_2", accessor -> new AutoDiscardActionAnimation(0.15F, accessor, flySwordArmature));
        FLY_SWORD_WAN_3 = builder.nextAccessor("fly_sword/fly_sword_wan_3", accessor -> new AutoDiscardActionAnimation(0.15F, accessor, flySwordArmature));
        FLY_SWORD_WAN_4 = builder.nextAccessor("fly_sword/fly_sword_wan_4", accessor -> new AutoDiscardActionAnimation(0.15F, accessor, flySwordArmature));
        FLY_SWORD_WAN_5 = builder.nextAccessor("fly_sword/fly_sword_wan_5", accessor -> new AutoDiscardActionAnimation(0.15F, accessor, flySwordArmature));
        FLY_SWORD_WAN_6 = builder.nextAccessor("fly_sword/fly_sword_wan_6", accessor -> new AutoDiscardActionAnimation(0.15F, accessor, flySwordArmature));
        WAN_ANIMATIONS = List.of(FLY_SWORD_WAN_1, FLY_SWORD_WAN_2, FLY_SWORD_WAN_3, FLY_SWORD_WAN_4, FLY_SWORD_WAN_5, FLY_SWORD_WAN_6);

    }

}
