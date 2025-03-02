package net.p1nero.ss.util;

import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.animation.ArtifactSpiritMultiPhaseAttackAnimation;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;

public class AnimationUtils {

    public static Vec3 getJointWorldPos(LivingEntityPatch<?> entityPatch, Joint joint) {
        Animator animator = entityPatch.getAnimator();
        Pose pose = animator.getPlayerFor(null).getCurrentPose(entityPatch, 0.5F);
        Vec3 pos = entityPatch.getOriginal().position();
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float) pos.x, (float) pos.y, (float) pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(1)));
        OpenMatrix4f JointTf = new OpenMatrix4f(entityPatch.getArmature().getBindedTransformFor(pose, joint)).mulFront(modelTf);

        return OpenMatrix4f.transform(JointTf, Vec3.ZERO);
    }

    public static ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase[] getPhases(List<Joint> joints, float maxTime){
        ArrayList<ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase> multiAttackPhases = new ArrayList<>();
        for(Joint joint : joints){
            multiAttackPhases.add(new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, maxTime, maxTime, Float.MAX_VALUE, joint, null));
        }
        return multiAttackPhases.toArray(new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase[0]);
    }

}
