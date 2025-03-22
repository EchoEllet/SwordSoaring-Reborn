package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.entity.wraithon.WraithonArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;

public class WraithonAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_WALK;

    public static void buildWraithonAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<WraithonArmature> armature = SwordSoaringArmatures.WRAITHON_ARMATURE;

        WRAITHON_IDLE = builder.nextAccessor("wraithon/wraithon_idle", (accessor -> new StaticAnimation(true, accessor, armature)));
        WRAITHON_WALK = builder.nextAccessor("wraithon/wraithon_walk", (accessor -> new StaticAnimation(true, accessor, armature)));
    }

}
