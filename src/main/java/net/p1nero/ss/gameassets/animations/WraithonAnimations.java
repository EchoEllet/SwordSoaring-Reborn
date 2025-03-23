package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.entity.wraithon.WraithonArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Armatures;

public class WraithonAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_WALK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_ROTATE_R;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_ROTATE_L;

    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> WRAITHON_1;

    public static void buildWraithonAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<WraithonArmature> armature = SwordSoaringArmatures.WRAITHON_ARMATURE;

        WRAITHON_IDLE = builder.nextAccessor("wraithon/wraithon_idle", (accessor -> new StaticAnimation(true, accessor, armature)));
        WRAITHON_WALK = builder.nextAccessor("wraithon/wraithon_walk", (accessor -> new StaticAnimation(true, accessor, armature)));
        WRAITHON_ROTATE_R = builder.nextAccessor("wraithon/wraithon_rotate_r", (accessor -> new ActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L = builder.nextAccessor("wraithon/wraithon_rotate_l", (accessor -> new ActionAnimation(0.15F, accessor, armature)));

        WRAITHON_1 = builder.nextAccessor("wraithon/wraithon_attack_1", (accessor -> new BasicAttackAnimation(0.15F, accessor, armature,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.1F, 0.6F, 0.6F, armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK),
                new AttackAnimation.Phase(0.6F, 0.6F, 1.2F, 1.2F, 1.2F, armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK))));


    }
}
