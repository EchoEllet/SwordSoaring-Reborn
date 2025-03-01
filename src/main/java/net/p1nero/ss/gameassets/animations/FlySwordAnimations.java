package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.animation.ArtifactSpiritMultiPhaseAttackAnimation;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordArmature;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;

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
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, SET_ANIMATION_END);
        FLY_SWORD_ATK_2 = new AttackAnimation(0.15F, "fly_sword/fly_sword_atk_2", flySwordArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.2F, 0.2F, 0.2F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.2F, 0.35F, 0.45F, 0.45F, 0.45F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.45F, 0.7F, 0.7F, 0.7F, 0.7F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(0.7F, 0.9F, 1.1F, 1.1F, 1.1F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON),
                new AttackAnimation.Phase(1.1F, 1.3F, 1.5167F, 1.5167F, 1.5167F, flySwordArmature.body, SwordSoaringColliders.FLY_SWORD_COMMON))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.3F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, SET_ANIMATION_END);
        FLY_SWORD_ATK_3 = new AttackAnimation(0.15F, "fly_sword/fly_sword_atk_3", flySwordArmature)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F));
        FLY_SWORD_ATK_IDLE = new StaticAnimation(true, "fly_sword/fly_sword_idle", flySwordArmature);
        FLY_SWORD_ATK_FLY = new StaticAnimation(true, "fly_sword/fly_sword_fly", flySwordArmature);
        FLY_SWORD_ATK_FLY_BACK = new ActionAnimation(0.15F, "fly_sword/fly_sword_back", flySwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if(livingEntityPatch.getOriginal() instanceof FlySwordEntity flySwordEntity){
                        flySwordEntity.setFlyingBack(true);
                    }
                }, AnimationEvent.Side.SERVER));
    }
}
