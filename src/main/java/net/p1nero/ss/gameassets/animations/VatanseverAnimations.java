package net.p1nero.ss.gameassets.animations;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.animation.LinkArtifactSpiritAnimation;
import net.p1nero.ss.entity.vatansever.VatanseverArmature;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import net.p1nero.ss.skill.weapon_innate.VatanseverWeaponInnateSkill;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.SelectiveAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;
import java.util.List;

public class VatanseverAnimations {
    public static StaticAnimation PLAYER_AUTO1;
    public static StaticAnimation PLAYER_AUTO2;
    public static StaticAnimation PLAYER_AUTO3;
    public static StaticAnimation PLAYER_AUTO4;
    public static StaticAnimation PLAYER_AUTO5;
    public static StaticAnimation PLAYER_STORM_START;

    public static StaticAnimation VATANSEVER_IDLE;
    public static StaticAnimation VATANSEVER_WALK;
    public static StaticAnimation VATANSEVER_WALK_F;
    public static StaticAnimation VATANSEVER_WALK_F_STOP;
    public static StaticAnimation VATANSEVER_WALK_B;
    public static StaticAnimation VATANSEVER_WALK_B_STOP;
    public static StaticAnimation VATANSEVER_RUN;
    public static StaticAnimation VATANSEVER_RUN_STOP;
    public static StaticAnimation VATANSEVER_DEATH;
    public static StaticAnimation VATANSEVER_FALL;
    public static StaticAnimation VATANSEVER_FLOAT;
    public static StaticAnimation VATANSEVER_FLY;
    public static StaticAnimation VATANSEVER_FLY_STOP;
    public static StaticAnimation VATANSEVER_SNEAK;
    public static StaticAnimation VATANSEVER_SNEAK_STOP;
    public static StaticAnimation VATANSEVER_SWIM;
    public static StaticAnimation VATANSEVER_AUTO1;
    public static StaticAnimation VATANSEVER_AUTO2;
    public static StaticAnimation VATANSEVER_AUTO3;
    public static StaticAnimation VATANSEVER_AUTO4;
    public static StaticAnimation VATANSEVER_AUTO5;
    public static StaticAnimation VATANSEVER_STORM_START;

    protected static void buildVatanseverAnim() {
        VatanseverArmature vatanseverArmature = SwordSoaringArmatures.vatanseverArmature;
        List<Pair<Joint, Collider>> left = List.of(Pair.of(vatanseverArmature.L1, SwordSoaringColliders.VATANSEVER),
                Pair.of(vatanseverArmature.L2, SwordSoaringColliders.VATANSEVER),
                Pair.of(vatanseverArmature.L3, SwordSoaringColliders.VATANSEVER));
        List<Pair<Joint, Collider>> right = List.of(Pair.of(vatanseverArmature.R1, SwordSoaringColliders.VATANSEVER),
                Pair.of(vatanseverArmature.R2, SwordSoaringColliders.VATANSEVER),
                Pair.of(vatanseverArmature.R3, SwordSoaringColliders.VATANSEVER));
        ArrayList<Pair<Joint, Collider>> all = new ArrayList<>();
        all.addAll(left);
        all.addAll(right);

        VATANSEVER_IDLE = new StaticAnimation(true, "biped/vatansever/living/vatansever_idle", vatanseverArmature);
        VATANSEVER_WALK_F = new StaticAnimation(true, "biped/vatansever/living/vatansever_walk", vatanseverArmature);
        VATANSEVER_WALK_F_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_walk_stop", vatanseverArmature);
        VATANSEVER_WALK_B = new StaticAnimation(true, "biped/vatansever/living/vatansever_walk_b", vatanseverArmature);
        VATANSEVER_WALK_B_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_walk_b_stop", vatanseverArmature);
        VATANSEVER_WALK = new SelectiveAnimation((entityPatch) -> {
            if(entityPatch instanceof VatanseverEntityPatch vatanseverEntityPatch && vatanseverEntityPatch.getOwnerPatch() != null){
                Vec3 view = vatanseverEntityPatch.getOwnerPatch().getOriginal().getViewVector(1.0F);
                Vec3 move = vatanseverEntityPatch.getOwnerPatch().getOriginal().getDeltaMovement();
                double dot = view.dot(move);
                return dot < 0.0 ? 1 : 0;
            }
            return 0;
        }, VATANSEVER_WALK_F, VATANSEVER_WALK_B);
        VATANSEVER_RUN = new StaticAnimation(true, "biped/vatansever/living/vatansever_run", vatanseverArmature);
        VATANSEVER_RUN_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_run_stop", vatanseverArmature);
        VATANSEVER_SWIM = new StaticAnimation(true, "biped/vatansever/living/vatansever_swim", vatanseverArmature);
        VATANSEVER_FALL = new StaticAnimation(true, "biped/vatansever/living/vatansever_fall", vatanseverArmature);
        VATANSEVER_DEATH = new StaticAnimation(true, "biped/vatansever/living/vatansever_death", vatanseverArmature);
        VATANSEVER_FLOAT = new StaticAnimation(true, "biped/vatansever/living/vatansever_float", vatanseverArmature);
        VATANSEVER_FLY = new StaticAnimation(true, "biped/vatansever/living/vatansever_fly", vatanseverArmature);
        VATANSEVER_FLY_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_fly_stop", vatanseverArmature);
        VATANSEVER_SNEAK = new StaticAnimation(true, "biped/vatansever/living/vatansever_sneak", vatanseverArmature);
        VATANSEVER_SNEAK_STOP = new StaticAnimation(true, "biped/vatansever/living/vatansever_sneak_stop", vatanseverArmature);
        VATANSEVER_AUTO1 = new AttackAnimation(0.15F, "biped/vatansever/vatansever_auto1", vatanseverArmature,
                new AttackAnimation.Phase(0.05F, 0.1F, 0.4F, 0.4F, 0.85F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, right)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO2 = new AttackAnimation(0.15F, "biped/vatansever/vatansever_auto2", vatanseverArmature,
                new AttackAnimation.Phase(0.05F, 0.2F, 0.3F, 0.5F, 0.68F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, right)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO3 = new AttackAnimation(0.15F, "biped/vatansever/vatansever_auto3", vatanseverArmature,
                new AttackAnimation.Phase(0.05F, 0.2F, 0.3F, 0.5F, 0.68F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, left)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO4 = new AttackAnimation(0.15F, "biped/vatansever/vatansever_auto4", vatanseverArmature,
                new AttackAnimation.Phase(0.05F, 0.2F, 0.3F, 0.5F, 0.79F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, all)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_AUTO5 = new AttackAnimation(0.15F, "biped/vatansever/vatansever_auto5", vatanseverArmature,
                new AttackAnimation.Phase(0.05F, 0.2F, 0.2F, 0.5F, 0.6F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, right)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)));
        VATANSEVER_STORM_START = new ActionAnimation(0.15F, "biped/vatansever/skill/vatansever_storm_start", vatanseverArmature);

        HumanoidArmature biped = Armatures.BIPED;
        PLAYER_AUTO1 = new LinkArtifactSpiritAnimation(0.15F, "biped/vatansever/vatansever_auto1", biped, VATANSEVER_AUTO1);
        PLAYER_AUTO2 = new LinkArtifactSpiritAnimation(0.15F, "biped/vatansever/vatansever_auto2", biped, VATANSEVER_AUTO2);
        PLAYER_AUTO3 = new LinkArtifactSpiritAnimation(0.15F, "biped/vatansever/vatansever_auto3", biped, VATANSEVER_AUTO3);
        PLAYER_AUTO4 = new LinkArtifactSpiritAnimation(0.15F, "biped/vatansever/vatansever_auto4", biped, VATANSEVER_AUTO4);
        PLAYER_AUTO5 = new LinkArtifactSpiritAnimation(0.15F, "biped/vatansever/vatansever_auto5", biped, VATANSEVER_AUTO5);
        PLAYER_STORM_START = new LinkArtifactSpiritAnimation(0.15F, "biped/vatansever/skill/vatansever_storm_start", biped, VATANSEVER_STORM_START)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> {
                    if (livingEntityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE).getDataManager();
                        if (manager.hasData(VatanseverWeaponInnateSkill.STORM_TIMER) && manager.getDataValue(VatanseverWeaponInnateSkill.STORM_TIMER) <= 0) {
                            manager.setDataSync(VatanseverWeaponInnateSkill.STORM_TIMER, 66, serverPlayerPatch.getOriginal());
                        }
                    }
                }), AnimationEvent.Side.SERVER));
    }
}
