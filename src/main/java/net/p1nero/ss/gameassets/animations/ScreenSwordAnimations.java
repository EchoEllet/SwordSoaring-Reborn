package net.p1nero.ss.gameassets.animations;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

import java.util.ArrayList;

public class ScreenSwordAnimations {
    public static StaticAnimation SCREEN_SWORD_IDLE;
    public static StaticAnimation SCREEN_SWORD_1;
    public static StaticAnimation SCREEN_SWORD_2;
    public static StaticAnimation SCREEN_SWORD_3;
    public static StaticAnimation SCREEN_SWORD_PLAYER_SUMMON;

    public static AnimationEvent.TimeStampedEvent RESET_ANIM = AnimationEvent.TimeStampedEvent.create(0.49F, ((livingEntityPatch, staticAnimation, objects) -> {
        livingEntityPatch.reserveAnimation(staticAnimation);
    }), AnimationEvent.Side.SERVER);

    public static void buildScreenSwordAnim() {
        ScreenSwordArmature screenSwordArmature = SwordSoaringArmatures.screenSwordArmature;
        ArrayList<Pair<Joint, Collider>> list = new ArrayList<>();
        for (Joint joint : screenSwordArmature.joints) {
            list.add(new Pair<>(joint, SwordSoaringColliders.SCREEN_SWORD));
        }
        AttackAnimation.Phase[] phases = new AttackAnimation.Phase[]{
                new AttackAnimation.Phase(0.01F, 0.01F, 0.01F, 0.1F, 0.1F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list),
                new AttackAnimation.Phase(0.1F, 0.1F, 0.1F, 0.2F, 0.2F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list),
                new AttackAnimation.Phase(0.2F, 0.2F, 0.2F, 0.3F, 0.3F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list),
                new AttackAnimation.Phase(0.3F, 0.3F, 0.3F, 0.4F, 0.4F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list),
                new AttackAnimation.Phase(0.4F, 0.4F, 0.4F, 0.5F, 0.5F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list)};

        SCREEN_SWORD_IDLE = new StaticAnimation(true, "screen_sword/screen_sword_idle", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 2.0F));
        SCREEN_SWORD_1 = new AttackAnimation(0.001F, "screen_sword/screen_sword_1", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        SCREEN_SWORD_2 = new AttackAnimation(0.001F, "screen_sword/screen_sword_2", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        SCREEN_SWORD_3 = new AttackAnimation(0.001F, "screen_sword/screen_sword_3", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);

        HumanoidArmature biped = Armatures.BIPED;
        SCREEN_SWORD_PLAYER_SUMMON = new ActionAnimation(0.15F, "screen_sword/screen_sword_start_player", biped);
    }

}
