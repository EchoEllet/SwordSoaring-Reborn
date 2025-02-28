package net.p1nero.ss.gameassets.animations;

import com.p1nero.invincible.api.animation.StaticAnimationProvider;
import net.p1nero.ss.animation.ArtifactSpiritMultiPhaseAttackAnimation;
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
    public static StaticAnimation KILL_AURA_1_SUMMON;
    public static StaticAnimation KILL_AURA_2;
    public static StaticAnimation KILL_AURA_2_SUMMON;
    public static StaticAnimation SCREEN_SWORD;
    public static StaticAnimation SCREEN_SWORD_SUMMON;
    public static StaticAnimation PLAYER_SUMMON_SWORD;

    public static AnimationEvent.TimeStampedEvent RESET_ANIM = AnimationEvent.TimeStampedEvent.create(0.49F, ((livingEntityPatch, staticAnimation, objects) -> {
        livingEntityPatch.reserveAnimation(staticAnimation);
    }), AnimationEvent.Side.SERVER);

    public static AnimationEvent onEndPlay(StaticAnimationProvider provider) {
        return AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> {
            livingEntityPatch.reserveAnimation(provider.get());
        }), AnimationEvent.Side.SERVER);
    }

    public static void buildScreenSwordAnim() {
        ScreenSwordArmature screenSwordArmature = SwordSoaringArmatures.screenSwordArmature;
        AttackAnimation.Phase[] phases = new AttackAnimation.Phase[]{
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W1, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W2, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W3, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W4, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W5, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W6, null)};

        SCREEN_SWORD_IDLE = new StaticAnimation(true, "screen_sword/screen_sword_idle", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 2.0F));
        KILL_AURA_1 = new ArtifactSpiritMultiPhaseAttackAnimation(0.001F, "screen_sword/kill_aura_1", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        KILL_AURA_1_SUMMON = new ActionAnimation(0.15F, "screen_sword/kill_aura_1_summon", screenSwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> KILL_AURA_1));
        KILL_AURA_2 = new ArtifactSpiritMultiPhaseAttackAnimation(0.001F, "screen_sword/kill_aura_2", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        KILL_AURA_2_SUMMON = new ActionAnimation(0.15F, "screen_sword/kill_aura_2_summon", screenSwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> KILL_AURA_2));
        SCREEN_SWORD = new ActionAnimation(0.001F, "screen_sword/screen_sword", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        SCREEN_SWORD_SUMMON = new ActionAnimation(0.15F, "screen_sword/screen_sword_summon", screenSwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> SCREEN_SWORD));

        HumanoidArmature biped = Armatures.BIPED;
        PLAYER_SUMMON_SWORD = new ActionAnimation(0.15F, "screen_sword/sword_summon_owner", biped)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.7F, ((livingEntityPatch, staticAnimation, objects) -> {
                    VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, 0, 2, 500);
                }), AnimationEvent.Side.BOTH));;
    }
}
