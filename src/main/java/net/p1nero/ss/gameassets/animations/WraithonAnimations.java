package net.p1nero.ss.gameassets.animations;

import net.minecraft.world.InteractionHand;
import net.p1nero.ss.entity.wraithon.WraithonArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Armatures;

import java.util.List;
import java.util.function.Supplier;

public class WraithonAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_WALK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_ROTATE_R;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_ROTATE_L;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> WRAITHON_1;

    public static void buildWraithonAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<WraithonArmature> armature = SwordSoaringArmatures.WRAITHON_ARMATURE;

        Supplier<AttackAnimation.JointColliderPair[]> supplier = ()-> {
            List<AttackAnimation.JointColliderPair> atkJoints = List.of(AttackAnimation.JointColliderPair.of(armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK_1),
                    AttackAnimation.JointColliderPair.of(armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK_2));
            return atkJoints.toArray(new AttackAnimation.JointColliderPair[0]);
        };

        WRAITHON_IDLE = builder.nextAccessor("wraithon/wraithon_idle", (accessor -> new StaticAnimation(true, accessor, armature)));
        WRAITHON_WALK = builder.nextAccessor("wraithon/wraithon_walk", (accessor -> new StaticAnimation(true, accessor, armature)));
        WRAITHON_ROTATE_R = builder.nextAccessor("wraithon/wraithon_rotate_r", (accessor -> new ActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L = builder.nextAccessor("wraithon/wraithon_rotate_l", (accessor -> new ActionAnimation(0.15F, accessor, armature)));

        WRAITHON_1 = builder.nextAccessor("wraithon/wraithon_attack_1", (accessor -> new BasicAttackAnimation(0.15F, accessor, armature,
                new AttackAnimation.Phase(0.0F, 1, 1, 1.25F, 1.25F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, supplier.get()),
                new AttackAnimation.Phase(1.25F, 2.16F, 2.16F, 2.42F, 2.42F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, supplier.get()))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1F))));


    }
}
