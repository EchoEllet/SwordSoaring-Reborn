package net.p1nero.ss.gameassets.animations;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.LevelUtil;

public class VatanseverStormAnimations {

    public static StaticAnimation VATANSEVER_STORM_IDLE;
    public static StaticAnimation VATANSEVER_STORM_UP;
    public static StaticAnimation VATANSEVER_STORM_MIDDLE;
    public static StaticAnimation VATANSEVER_STORM_MIDDLE_2;
    public static StaticAnimation VATANSEVER_STORM_DOWN;
    public static void buildVatanseverStormAnim() {
        VatanseverStormArmature stormArmature = SwordSoaringArmatures.vatanseverStormArmature;
        VATANSEVER_STORM_IDLE = new StaticAnimation(true, "vatansever_storm/vatansever_storm_idle", stormArmature);
        VATANSEVER_STORM_UP = new ActionAnimation(0.0001F, "vatansever_storm/vatansever_storm_up", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> {
                    livingEntityPatch.reserveAnimation(VATANSEVER_STORM_UP);
                }), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.8F));
        VATANSEVER_STORM_MIDDLE = new ActionAnimation(0.0001F, "vatansever_storm/vatansever_storm_middle", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> {
                    livingEntityPatch.reserveAnimation(VATANSEVER_STORM_MIDDLE);
                }), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.9F));
        VATANSEVER_STORM_MIDDLE_2 = new ActionAnimation(0.0001F, "vatansever_storm/vatansever_storm_middle_2", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> {
                    livingEntityPatch.reserveAnimation(VATANSEVER_STORM_MIDDLE_2);
                }), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.9F));
        VATANSEVER_STORM_DOWN = new ActionAnimation(0.0001F, "vatansever_storm/vatansever_storm_down", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> {
                    livingEntityPatch.reserveAnimation(VATANSEVER_STORM_DOWN);
                }), AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.8F));
        }



}
