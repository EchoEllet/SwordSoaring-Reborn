package net.p1nero.ss.gameassets.animations;

import net.minecraft.world.InteractionHand;
import net.p1nero.ss.client.sound.SwordSoaringSounds;
import net.p1nero.ss.entity.wraithon.WraithonArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;

import java.util.ArrayList;
import java.util.List;

public class WraithonAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_WALK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_ROTATE_R;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_ROTATE_L;

    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_1;

    public static void buildWraithonAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<WraithonArmature> armature = SwordSoaringArmatures.WRAITHON_ARMATURE;

        WRAITHON_IDLE = builder.nextAccessor("wraithon/wraithon_idle", (accessor -> new StaticAnimation(true, accessor, armature)));
        WRAITHON_WALK = builder.nextAccessor("wraithon/wraithon_walk", (accessor -> new StaticAnimation(true, accessor, armature)));
        WRAITHON_ROTATE_R = builder.nextAccessor("wraithon/wraithon_rotate_r", (accessor -> new ActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L = builder.nextAccessor("wraithon/wraithon_rotate_l", (accessor -> new ActionAnimation(0.15F, accessor, armature)));

        WRAITHON_1 = builder.nextAccessor("wraithon/wraithon_1", (accessor -> new ActionAnimation(0.15F, accessor, armature)));


    }
}
