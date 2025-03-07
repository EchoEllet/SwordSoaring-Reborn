package net.p1nero.ss.gameassets.animations;

import com.p1nero.invincible.api.animation.StaticAnimationProvider;
import net.p1nero.ss.client.CameraAnim;
import net.p1nero.ss.entity.sword.sword_convergence.SwordConvergenceEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.skill.sword_controller.WanJianGuiZongSkill;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class SwordConvergenceAnimations {
    public static StaticAnimation WAN1_L;
    public static StaticAnimation WAN2_L;
    public static StaticAnimation WAN3_L;
    public static StaticAnimation WAN4_L;
    public static StaticAnimation WAN5_L;

    public static StaticAnimation WAN1_R;
    public static StaticAnimation WAN2_R;
    public static StaticAnimation WAN3_R;
    public static StaticAnimation WAN4_R;
    public static StaticAnimation WAN5_R;

    public static StaticAnimation WAN1_PLAYER;
    public static StaticAnimation WAN2_PLAYER;
    public static StaticAnimation WAN3_PLAYER;
    public static StaticAnimation WAN4_PLAYER;
    public static StaticAnimation WAN5_PLAYER;

    public static AnimationEvent nextPlay(StaticAnimationProvider animation) {
        return AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.reserveAnimation(animation.get()), AnimationEvent.Side.SERVER);
    }

    public static void buildSwordConvergenceAnim() {
        HumanoidArmature biped = Armatures.BIPED;
        WAN1_PLAYER = new ActionAnimation(0.15F, "wan/wan_owner_1", biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> CameraAnim.zoomIn(new Vec3f(0, -3 ,-6), 200), AnimationEvent.Side.CLIENT))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN2_PLAYER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) -> 2F);
        WAN2_PLAYER = new ActionAnimation(0.0001F, "wan/wan_owner_2", biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> CameraAnim.zoomIn(new Vec3f(0, -3 ,-6), 200), AnimationEvent.Side.CLIENT))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN3_PLAYER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) ->2F);
        WAN3_PLAYER = new ActionAnimation(0.0001F, "wan/wan_owner_3", biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> CameraAnim.zoomIn(new Vec3f(0, -3 ,-6), 200), AnimationEvent.Side.CLIENT))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillDataManager manager = serverPlayerPatch.getSkill(SwordSoaringSkillSlots.SWORD_CONTROLLER).getDataManager();
                        if (manager.hasData(WanJianGuiZongSkill.IS_PRESSING) && manager.getDataValue(WanJianGuiZongSkill.IS_PRESSING)) {
                            serverPlayerPatch.reserveAnimation(WAN3_PLAYER);
                        } else {
                            serverPlayerPatch.reserveAnimation(WAN4_PLAYER);
                        }
                    }
                }, AnimationEvent.Side.SERVER))
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) -> 2F);
        WAN4_PLAYER = new ActionAnimation(0.15F, "wan/wan_owner_4", biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> CameraAnim.zoomIn(new Vec3f(0, -3 ,-6), 200), AnimationEvent.Side.CLIENT))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN5_PLAYER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) -> 2F);
        WAN5_PLAYER = new ActionAnimation(0.0001F, "wan/wan_owner_5", biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> CameraAnim.zoomIn(new Vec3f(0, -3 ,-6), 200), AnimationEvent.Side.CLIENT))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) -> 2F);

        VatanseverStormArmature stormArmature = SwordSoaringArmatures.vatanseverStormArmature;
        WAN1_L = new ActionAnimation(0.15F, "wan/wan_l_1", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN2_L));
        WAN2_L = new ActionAnimation(0.0001F, "wan/wan_l_2", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN3_L));
        WAN3_L = new ActionAnimation(0.0001F, "wan/wan_l_3", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch.getOriginal() instanceof SwordConvergenceEntity swordConvergenceEntity) {
                        if (swordConvergenceEntity.isOwnerKeyPressing()) {
                            livingEntityPatch.reserveAnimation(WAN3_L);
                        } else {
                            livingEntityPatch.reserveAnimation(WAN4_L);
                        }
                    }
                }, AnimationEvent.Side.SERVER));
        WAN4_L = new ActionAnimation(0.15F, "wan/wan_l_4", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN5_L));
        WAN5_L = new ActionAnimation(0.0001F, "wan/wan_l_5", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER));

        WAN1_R = new ActionAnimation(0.15F, "wan/wan_r_1", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN2_R));
        WAN2_R = new ActionAnimation(0.0001F, "wan/wan_r_2", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN3_R));
        WAN3_R = new ActionAnimation(0.0001F, "wan/wan_r_3", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch.getOriginal() instanceof SwordConvergenceEntity swordConvergenceEntity) {
                        if (swordConvergenceEntity.isOwnerKeyPressing()) {
                            livingEntityPatch.reserveAnimation(WAN3_R);
                        } else {
                            livingEntityPatch.reserveAnimation(WAN4_R);
                        }
                    }
                }, AnimationEvent.Side.SERVER));
        WAN4_R = new ActionAnimation(0.15F, "wan/wan_r_4", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(()->WAN5_R));
        WAN5_R = new ActionAnimation(0.0001F, "wan/wan_r_5", stormArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER));

    }
}
