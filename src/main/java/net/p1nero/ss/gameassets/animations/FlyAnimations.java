package net.p1nero.ss.gameassets.animations;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public class FlyAnimations {
    public static AnimationManager.AnimationAccessor<ActionAnimation> APPRENTICE_INIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> APPRENTICE_FLYING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> APPRENTICE_SPEED_UP;
    public static AnimationManager.AnimationAccessor<ActionAnimation> EXPERT_INIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EXPERT_FLYING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EXPERT_SPEED_UP;
    public static AnimationManager.AnimationAccessor<ActionAnimation> MASTER_INIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> MASTER_FLYING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> MASTER_SPEED_UP;

    public static void buildFlyAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> biped = Armatures.BIPED;

        APPRENTICE_INIT = builder.nextAccessor("biped/fly_anim/fly_on_sword_apprentice_initiation", accessor -> new ActionAnimation(0.15F, accessor, biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create(((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.reserveAnimation(APPRENTICE_FLYING)), AnimationEvent.Side.SERVER)));
        APPRENTICE_FLYING = builder.nextAccessor("biped/fly_anim/fly_on_sword_apprentice_flying", accessor -> new StaticAnimation(true, accessor, biped));

        APPRENTICE_SPEED_UP = builder.nextAccessor("biped/fly_anim/fly_on_sword_apprentice_acceleration", accessor -> new StaticAnimation(true, accessor, biped));

        EXPERT_INIT = builder.nextAccessor("biped/fly_anim/fly_on_sword_expert_initiation", accessor -> new ActionAnimation(0.15F, accessor, biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create(((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.reserveAnimation(EXPERT_FLYING)), AnimationEvent.Side.SERVER)));

        EXPERT_FLYING = builder.nextAccessor("biped/fly_anim/fly_on_sword_expert_flying", accessor ->  new StaticAnimation(true, accessor, biped));

        EXPERT_SPEED_UP = builder.nextAccessor("biped/fly_anim/fly_on_sword_expert_acceleration", accessor ->  new StaticAnimation(true, accessor, biped));

        MASTER_INIT = builder.nextAccessor("biped/fly_anim/fly_on_sword_master_initiation", accessor -> new ActionAnimation(0.15F, accessor, biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create(((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.reserveAnimation(MASTER_FLYING)), AnimationEvent.Side.SERVER)));

        MASTER_FLYING = builder.nextAccessor("biped/fly_anim/fly_on_sword_master_flying", accessor -> new StaticAnimation(true, accessor, biped));

        MASTER_SPEED_UP = builder.nextAccessor("biped/fly_anim/fly_on_sword_master_acceleration", accessor ->  new StaticAnimation(true, accessor, biped));

    }
}
