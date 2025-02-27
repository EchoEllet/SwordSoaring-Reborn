package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.animation.MultiHitBoxAttackAnimation;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public class ScreenSwordAnimations {
    public static StaticAnimation SCREEN_SWORD_IDLE;
    public static StaticAnimation KILL_AURA_1;
    public static StaticAnimation KILL_AURA_2;
    public static StaticAnimation SCREEN_SWORD;
    public static StaticAnimation SCREEN_SWORD_PLAYER_SUMMON;

    public static AnimationEvent.TimeStampedEvent RESET_ANIM = AnimationEvent.TimeStampedEvent.create(0.49F, ((livingEntityPatch, staticAnimation, objects) -> {
        livingEntityPatch.reserveAnimation(staticAnimation);
    }), AnimationEvent.Side.SERVER);

    public static void buildScreenSwordAnim() {
        ScreenSwordArmature screenSwordArmature = SwordSoaringArmatures.screenSwordArmature;
        AttackAnimation.Phase[] phases = new AttackAnimation.Phase[]{
                new MultiHitBoxAttackAnimation.KillAuraAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W1, null),
                new MultiHitBoxAttackAnimation.KillAuraAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W2, null),
                new MultiHitBoxAttackAnimation.KillAuraAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W3, null),
                new MultiHitBoxAttackAnimation.KillAuraAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W4, null),
                new MultiHitBoxAttackAnimation.KillAuraAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W5, null),
                new MultiHitBoxAttackAnimation.KillAuraAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W6, null)};

        SCREEN_SWORD_IDLE = new StaticAnimation(true, "screen_sword/screen_sword_idle", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 2.0F));
        KILL_AURA_1 = new MultiHitBoxAttackAnimation(0.001F, "screen_sword/kill_aura_1", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        KILL_AURA_2 = new MultiHitBoxAttackAnimation(0.001F, "screen_sword/kill_aura_2", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        SCREEN_SWORD = new ActionAnimation(0.001F, "screen_sword/screen_sword", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);

        HumanoidArmature biped = Armatures.BIPED;
        SCREEN_SWORD_PLAYER_SUMMON = new ActionAnimation(0.15F, "screen_sword/screen_sword_start_player", biped);
    }
}
