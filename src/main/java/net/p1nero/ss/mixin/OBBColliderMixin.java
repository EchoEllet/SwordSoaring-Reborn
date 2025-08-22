package net.p1nero.ss.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.ReplaceableArmature;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;

@Mixin(value = OBBCollider.class, remap = false)
public abstract class OBBColliderMixin extends Collider {

    @Shadow @Final protected Vec3[] modelVertices;

    @Shadow public abstract boolean isCollide(Entity entity);

    public OBBColliderMixin(Vec3 center, @Nullable AABB outerAABB) {
        super(center, outerAABB);
    }

    /**
     * 矫正旋转
     * 其实按道理drawInternal都得改
     */
    @OnlyIn(Dist.CLIENT)
    @Inject(method = "drawInternal", at = @At(value = "HEAD"), cancellable = true)
    private void sword_soaring$drawInternal(PoseStack poseStack, VertexConsumer vertexConsumer, Armature armature, Joint joint, Pose pose1, Pose pose2, float partialTicks, int color, CallbackInfo ci){
        if(!(armature instanceof ReplaceableArmature)){
            return;
        }
        Pose interpolatedPose = Pose.interpolatePose(pose1, pose2, partialTicks);
        OpenMatrix4f poseMatrix;
        if (armature.rootJoint.equals(joint)) {
            JointTransform jt = interpolatedPose.get("Root");
            jt.rotation().x = 0.0F;
            jt.rotation().y = 0.0F;
            jt.rotation().z = 0.0F;
            jt.rotation().w = 1.0F;
            poseMatrix = jt.getAnimationBoundMatrix(armature.rootJoint, new OpenMatrix4f()).removeTranslation();
        } else {
            poseMatrix = armature.getBoundTransformFor(interpolatedPose, joint);
        }

        poseMatrix.rotateDeg(90, Vec3f.X_AXIS);
        poseMatrix.rotateDeg(90, Vec3f.Z_AXIS);
        poseStack.pushPose();
        MathUtils.mulStack(poseStack, poseMatrix);
        Matrix4f matrix = poseStack.last().pose();
        Vec3 vec = this.modelVertices[1];
        float maxX = (float)(this.modelCenter.x + vec.x);
        float maxY = (float)(this.modelCenter.y + vec.y);
        float maxZ = (float)(this.modelCenter.z + vec.z);
        float minX = (float)(this.modelCenter.x - vec.x);
        float minY = (float)(this.modelCenter.y - vec.y);
        float minZ = (float)(this.modelCenter.z - vec.z);
        vertexConsumer.addVertex(matrix, minX, maxY, minZ).setColor(color).setNormal(0.0F, 0.0F, 1.0F);
        vertexConsumer.addVertex(matrix, minX, maxY, maxZ).setColor(color).setNormal(0.0F, 0.0F, 1.0F);
        vertexConsumer.addVertex(matrix, minX, maxY, maxZ).setColor(color).setNormal(1.0F, 0.0F, 0.0F);
        vertexConsumer.addVertex(matrix, maxX, maxY, maxZ).setColor(color).setNormal(1.0F, 0.0F, 0.0F);
        vertexConsumer.addVertex(matrix, maxX, maxY, maxZ).setColor(color).setNormal(0.0F, 0.0F, -1.0F);
        vertexConsumer.addVertex(matrix, maxX, maxY, minZ).setColor(color).setNormal(0.0F, 0.0F, -1.0F);
        vertexConsumer.addVertex(matrix, maxX, maxY, minZ).setColor(color).setNormal(-1.0F, 0.0F, 0.0F);
        vertexConsumer.addVertex(matrix, minX, maxY, minZ).setColor(color).setNormal(-1.0F, 0.0F, 0.0F);
        vertexConsumer.addVertex(matrix, maxX, maxY, maxZ).setColor(color).setNormal(0.0F, -1.0F, 0.0F);
        vertexConsumer.addVertex(matrix, maxX, minY, maxZ).setColor(color).setNormal(0.0F, -1.0F, 0.0F);
        vertexConsumer.addVertex(matrix, minX, maxY, maxZ).setColor(color).setNormal(0.0F, -1.0F, 0.0F);
        vertexConsumer.addVertex(matrix, minX, minY, maxZ).setColor(color).setNormal(0.0F, -1.0F, 0.0F);
        vertexConsumer.addVertex(matrix, maxX, maxY, minZ).setColor(color).setNormal(0.0F, -1.0F, 0.0F);
        vertexConsumer.addVertex(matrix, maxX, minY, minZ).setColor(color).setNormal(0.0F, -1.0F, 0.0F);
        vertexConsumer.addVertex(matrix, minX, maxY, minZ).setColor(color).setNormal(0.0F, -1.0F, 0.0F);
        vertexConsumer.addVertex(matrix, minX, minY, minZ).setColor(color).setNormal(0.0F, -1.0F, 0.0F);
        vertexConsumer.addVertex(matrix, minX, minY, minZ).setColor(color).setNormal(0.0F, 0.0F, 1.0F);
        vertexConsumer.addVertex(matrix, minX, minY, maxZ).setColor(color).setNormal(0.0F, 0.0F, 1.0F);
        vertexConsumer.addVertex(matrix, minX, minY, maxZ).setColor(color).setNormal(1.0F, 0.0F, 0.0F);
        vertexConsumer.addVertex(matrix, maxX, minY, maxZ).setColor(color).setNormal(1.0F, 0.0F, 0.0F);
        vertexConsumer.addVertex(matrix, maxX, minY, maxZ).setColor(color).setNormal(0.0F, 0.0F, -1.0F);
        vertexConsumer.addVertex(matrix, maxX, minY, minZ).setColor(color).setNormal(0.0F, 0.0F, -1.0F);
        vertexConsumer.addVertex(matrix, maxX, minY, minZ).setColor(color).setNormal(-1.0F, 0.0F, 0.0F);
        vertexConsumer.addVertex(matrix, minX, minY, minZ).setColor(color).setNormal(-1.0F, 0.0F, 0.0F);
        poseStack.popPose();
        ci.cancel();
    }
}
