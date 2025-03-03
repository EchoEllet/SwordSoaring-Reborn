package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.animation.ArtifactSpiritMultiPhaseAttackAnimation;
import net.p1nero.ss.animation.AutoDiscardAnimation;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.util.AnimationUtils;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.ArrayList;
import java.util.List;

public class BabylonAnimations {

    public static StaticAnimation BABYLON_IDLE;
    public static StaticAnimation BABYLON_SHOOT;
    public static StaticAnimation BABYLON_SHOOT_L;
    public static StaticAnimation BABYLON_SHOOT_R;

    public static void buildBabylonAnim() {
        BabylonArmature babylonArmature = SwordSoaringArmatures.babylonArmature;
        BABYLON_IDLE = new StaticAnimation(true, "babylon/babylon_idle", babylonArmature);
        BABYLON_SHOOT = new ArtifactSpiritMultiPhaseAttackAnimation(0.15F, "babylon/babylon_shoot", babylonArmature, AnimationUtils.getPhases(babylonArmature.joints, 3.0F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) -> 0.5F);
        BABYLON_SHOOT_L = new ArtifactSpiritMultiPhaseAttackAnimation(0.15F, "babylon/babylon_shoot_l", babylonArmature, AnimationUtils.getPhases(babylonArmature.joints, 3.0F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) -> 0.5F);
        BABYLON_SHOOT_R = new ArtifactSpiritMultiPhaseAttackAnimation(0.15F, "babylon/babylon_shoot_r", babylonArmature, AnimationUtils.getPhases(babylonArmature.joints, 3.0F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) -> 0.5F);
    }

}
