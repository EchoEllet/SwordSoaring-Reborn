package net.p1nero.ss.gameassets.animations;

import com.p1nero.invincible.api.animation.StaticAnimationProvider;
import net.p1nero.ss.animation.AutoDiscardActionAnimation;
import net.p1nero.ss.client.CameraAnim;
import net.p1nero.ss.entity.sword.sword_convergence.SwordConvergenceEntity;
import net.p1nero.ss.entity.sword.sword_convergence.WanArmature;
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
    public static StaticAnimation WAN_SHOOT_L;

    public static StaticAnimation WAN1_R;
    public static StaticAnimation WAN2_R;
    public static StaticAnimation WAN3_R;
    public static StaticAnimation WAN4_R;
    public static StaticAnimation WAN_SHOOT_R;

    public static StaticAnimation WAN1_PLAYER;
    public static StaticAnimation WAN2_PLAYER;
    public static StaticAnimation WAN3_PLAYER;


    public static AnimationEvent.TimeStampedEvent summonAndPlay(float time, StaticAnimationProvider animationToPlay) {
        return AnimationEvent.TimeStampedEvent.create(time, (livingEntityPatch, staticAnimation, objects) -> {
            if (livingEntityPatch.getOriginal() instanceof SwordConvergenceEntity swordConvergenceEntity) {
                SwordConvergenceEntity newSwords = new SwordConvergenceEntity(swordConvergenceEntity.getOwner());
                newSwords.setAnimationToPlay(animationToPlay.get());
                newSwords.initBabylonItems(swordConvergenceEntity.getValidBabylonItems(), false);
                swordConvergenceEntity.level.addFreshEntity(newSwords);
            }
        }, AnimationEvent.Side.SERVER);
    }

    public static AnimationEvent nextPlay(StaticAnimationProvider animation) {
        return AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.reserveAnimation(animation.get()), AnimationEvent.Side.SERVER);
    }

    public static void buildSwordConvergenceAnim() {
        HumanoidArmature biped = Armatures.BIPED;
        WAN1_PLAYER = new ActionAnimation(0.15F, "wan/wan_owner_1", biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> CameraAnim.zoomIn(new Vec3f(0, -3, -6), 200), AnimationEvent.Side.CLIENT))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, nextPlay(() -> WAN2_PLAYER));
        WAN2_PLAYER = new ActionAnimation(0.0001F, "wan/wan_owner_2", biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> CameraAnim.zoomIn(new Vec3f(0, -3, -6), 200), AnimationEvent.Side.CLIENT))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillDataManager manager = serverPlayerPatch.getSkill(SwordSoaringSkillSlots.SWORD_CONTROLLER).getDataManager();
                        if (manager.hasData(WanJianGuiZongSkill.IS_PRESSING) && manager.getDataValue(WanJianGuiZongSkill.IS_PRESSING)) {
                            serverPlayerPatch.reserveAnimation(WAN2_PLAYER);
                        } else {
                            serverPlayerPatch.reserveAnimation(WAN3_PLAYER);
                        }
                    }
                }, AnimationEvent.Side.SERVER))
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, false);
        WAN3_PLAYER = new ActionAnimation(0.0001F, "wan/wan_owner_3", biped)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> CameraAnim.zoomIn(new Vec3f(0, -3, -6), 200), AnimationEvent.Side.CLIENT))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 1.5F));

        WanArmature wanArmature = SwordSoaringArmatures.wanArmature;
        WAN1_L = new AutoDiscardActionAnimation(0.15F, "wan/wan_l_1", wanArmature)
                .addEvents(summonAndPlay(2.30F, () -> WAN2_L));
        WAN2_L = new ActionAnimation(0.0001F, "wan/wan_l_2", wanArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch.getOriginal() instanceof SwordConvergenceEntity swordConvergenceEntity) {
                        if (swordConvergenceEntity.isOwnerKeyPressing()) {
                            livingEntityPatch.reserveAnimation(WAN2_L);
                        } else {
                            livingEntityPatch.reserveAnimation(WAN3_L);
                        }
                    }
                }, AnimationEvent.Side.SERVER));
        WAN3_L = new AutoDiscardActionAnimation(0.0001F, "wan/wan_l_3", wanArmature)
                .addEvents(summonAndPlay(1.33F, () -> WAN4_L));
        WAN4_L = new AutoDiscardActionAnimation(0.15F, "wan/wan_l_4", wanArmature)
                .addEvents(summonAndPlay(1.13F, () -> WAN_SHOOT_L))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 1F));
        WAN_SHOOT_L = new AutoDiscardActionAnimation(0.0001F, "wan/wan_shoot_l", wanArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER));

        WAN1_R = new AutoDiscardActionAnimation(0.15F, "wan/wan_r_1", wanArmature)
                .addEvents( summonAndPlay(2.30F, () -> WAN2_R));
        WAN2_R = new ActionAnimation(0.0001F, "wan/wan_r_2", wanArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch.getOriginal() instanceof SwordConvergenceEntity swordConvergenceEntity) {
                        if (swordConvergenceEntity.isOwnerKeyPressing()) {
                            livingEntityPatch.reserveAnimation(WAN2_R);
                        } else {
                            livingEntityPatch.reserveAnimation(WAN3_R);
                        }
                    }
                }, AnimationEvent.Side.SERVER));
        WAN3_R = new AutoDiscardActionAnimation(0.0001F, "wan/wan_r_3", wanArmature)
                .addEvents(summonAndPlay(1.33F, () -> WAN4_R));
        WAN4_R = new AutoDiscardActionAnimation(0.15F, "wan/wan_r_4", wanArmature)
                .addEvents(summonAndPlay(1.13F, () -> WAN_SHOOT_R))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 1F));
        WAN_SHOOT_R = new AutoDiscardActionAnimation(0.0001F, "wan/wan_shoot_r", wanArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().discard(), AnimationEvent.Side.SERVER));

    }
}
