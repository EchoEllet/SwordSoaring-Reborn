package net.p1nero.ss.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
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

    public static ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase[] getPhases(List<Joint> joints, float maxTime) {
        ArrayList<ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase> multiAttackPhases = new ArrayList<>();
        for (Joint joint : joints) {
            multiAttackPhases.add(new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, maxTime, maxTime, Float.MAX_VALUE, joint, null));
        }
        return multiAttackPhases.toArray(new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase[0]);
    }

    public static ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase[] getPhases(List<Joint> joints, float startTime, float endTime) {
        ArrayList<ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase> multiAttackPhases = new ArrayList<>();
        for (Joint joint : joints) {
            multiAttackPhases.add(new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(startTime, startTime, endTime, endTime, Float.MAX_VALUE, joint, null));
        }
        return multiAttackPhases.toArray(new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase[0]);
    }

    public static Vec3 jointRayDetection(LivingEntityPatch<?> livingEntityPatch, Joint joint, float distance,String xyz){
        if (xyz == "x"){
            return jointRayDetectionX(livingEntityPatch,joint,distance);
        }
        if (xyz == "y"){
            return jointRayDetectionY(livingEntityPatch,joint,distance);
        }
        if (xyz == "z"){
            return jointRayDetectionZ(livingEntityPatch,joint,distance);
        }
        return null;
    }


    public static Vec3 jointRayDetectionX(LivingEntityPatch<?> livingEntityPatch, Joint joint, float distance) {
        LivingEntity entity = livingEntityPatch.getOriginal();
        OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBindedTransformFor(livingEntityPatch.getAnimator().getPose(1.0F), joint);
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(entity.yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f rotatedMatrix = new OpenMatrix4f();
        OpenMatrix4f.mul(rotation, transformMatrix, rotatedMatrix);

        // 根据 distance 的正负决定方向
        float sign = Math.signum(distance);
        float absoluteDistance = Math.abs(distance);
        for (int i = 0; i * 0.03 < absoluteDistance; i++) {
            OpenMatrix4f currentTransform = new OpenMatrix4f(rotatedMatrix);
            currentTransform.translate(new Vec3f(i * 0.03 * sign, 0.0F, 0.0F));
            Vec3 pos = new Vec3(
                    currentTransform.m30 + (float) entity.getX(),
                    currentTransform.m31 + (float) entity.getY(),
                    currentTransform.m32 + (float) entity.getZ()
            );
            BlockPos center = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
            if (checkRadiusBlocks(entity, center, 1)) {
                return pos;
            }
        }
        OpenMatrix4f endTransform = new OpenMatrix4f(rotatedMatrix);
        endTransform.translate(new Vec3f(distance, 0.0F, 0.0F));
        Vec3 endPos = new Vec3(
                endTransform.m30 + (float) entity.getX(),
                endTransform.m31 + (float) entity.getY(),
                endTransform.m32 + (float) entity.getZ()
        );
        return endPos;
    }

    public static Vec3 jointRayDetectionY(LivingEntityPatch<?> livingEntityPatch, Joint joint, float distance) {
        LivingEntity entity = livingEntityPatch.getOriginal();
        OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBindedTransformFor(livingEntityPatch.getAnimator().getPose(1.0F), joint);
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(entity.yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f rotatedMatrix = new OpenMatrix4f();
        OpenMatrix4f.mul(rotation, transformMatrix, rotatedMatrix);

        // 根据 distance 的正负决定方向
        float sign = Math.signum(distance);
        float absoluteDistance = Math.abs(distance);
        for (int i = 0; i * 0.03 < absoluteDistance; i++) {
            OpenMatrix4f currentTransform = new OpenMatrix4f(rotatedMatrix);
            currentTransform.translate(new Vec3f(0.0F, i * 0.03 * sign, 0.0F));
            Vec3 pos = new Vec3(
                    currentTransform.m30 + (float) entity.getX(),
                    currentTransform.m31 + (float) entity.getY(),
                    currentTransform.m32 + (float) entity.getZ()
            );
            BlockPos center = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
            if (checkRadiusBlocks(entity, center, 1)) {
                return pos;
            }
        }
        OpenMatrix4f endTransform = new OpenMatrix4f(rotatedMatrix);
        endTransform.translate(new Vec3f(0.0F, distance, 0.0F));
        Vec3 endPos = new Vec3(
                endTransform.m30 + (float) entity.getX(),
                endTransform.m31 + (float) entity.getY(),
                endTransform.m32 + (float) entity.getZ()
        );
        return endPos;
    }

    public static Vec3 jointRayDetectionZ(LivingEntityPatch<?> livingEntityPatch, Joint joint, float distance) {
        LivingEntity entity = livingEntityPatch.getOriginal();
        OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBindedTransformFor(livingEntityPatch.getAnimator().getPose(1.0F), joint);
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(entity.yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f rotatedMatrix = new OpenMatrix4f();
        OpenMatrix4f.mul(rotation, transformMatrix, rotatedMatrix);

        // 根据 distance 的正负决定方向
        float sign = Math.signum(distance);
        float absoluteDistance = Math.abs(distance);
        for (int i = 0; i * 0.03 < absoluteDistance; i++) {
            OpenMatrix4f currentTransform = new OpenMatrix4f(rotatedMatrix);
            currentTransform.translate(new Vec3f(0.0F, 0.0F, i * 0.03 * sign));
            Vec3 pos = new Vec3(
                    currentTransform.m30 + (float) entity.getX(),
                    currentTransform.m31 + (float) entity.getY(),
                    currentTransform.m32 + (float) entity.getZ()
            );
            BlockPos center = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
            if (checkRadiusBlocks(entity, center, 1)) {
                return pos;
            }
        }
        OpenMatrix4f endTransform = new OpenMatrix4f(rotatedMatrix);
        endTransform.translate(new Vec3f(0.0F, 0.0F, distance));
        Vec3 endPos = new Vec3(
                endTransform.m30 + (float) entity.getX(),
                endTransform.m31 + (float) entity.getY(),
                endTransform.m32 + (float) entity.getZ()
        );
        return endPos;
    }
    private static boolean checkRadiusBlocks(LivingEntity entity, BlockPos center, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = center.offset(x, y, z);
                    if (entity.level().getBlockState(checkPos).isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
