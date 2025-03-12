package net.p1nero.ss.animation;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.p1nero.ss.entity.vatansever.VatanseverArmature;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class VatanseverAttackAnimation extends ArtifactSpiritMultiPhaseAttackAnimation{


    public VatanseverAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
    }

    public VatanseverAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, accessor, armature);
    }

    public VatanseverAttackAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(transitionTime, accessor, armature, phases);
    }

    public VatanseverAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
    }

    public VatanseverAttackAnimation(float convertTime, String path, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    @Override
    public boolean isPhaseValid(LivingEntityPatch<?> entityPatch, Phase phase) {
        if(entityPatch instanceof VatanseverEntityPatch vatanseverEntityPatch && entityPatch.getArmature() instanceof VatanseverArmature vatanseverArmature){
            for(JointColliderPair pair : phase.colliders){
                for(Joint joint : vatanseverArmature.getInvalidJoints(vatanseverEntityPatch)){
                    //contain无法判断？只能换用id比较了
                    if(joint.getId() == pair.getFirst().getId()){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
