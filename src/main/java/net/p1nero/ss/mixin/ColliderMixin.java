package net.p1nero.ss.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.ReplaceableArmature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.QuaternionUtils;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

@Mixin(value = Collider.class, remap = false)
public abstract class ColliderMixin {

    @Shadow protected abstract void transform(OpenMatrix4f mat);

    @Shadow public abstract List<Entity> getCollideEntities(Entity entity);

    @Shadow public abstract void drawInternal(PoseStack poseStack, VertexConsumer vertexConsumer, Armature armature, Joint joint, Pose pose, Pose pose1, float v, int i);

    @Shadow public abstract RenderType getRenderType();

    @Inject(method = "updateAndSelectCollideEntity", at = @At("HEAD"), cancellable = true)
    private void sword_soaring$updateAndSelectCollideEntity(LivingEntityPatch<?> entityPatch, AttackAnimation attackAnimation, float prevElapsedTime, float elapsedTime, Joint joint, float attackSpeed, CallbackInfoReturnable<List<Entity>> cir){
        Armature armature = entityPatch.getArmature();
        OpenMatrix4f transformMatrix;
        if (armature.rootJoint.equals(joint)) {
            Pose rootPose = new Pose();
            rootPose.putJointData("Root", JointTransform.empty());
            attackAnimation.modifyPose(attackAnimation, rootPose, entityPatch, elapsedTime, 1.0F);
            transformMatrix = rootPose.getOrDefaultTransform("Root").getAnimationBoundMatrix(armature.rootJoint, new OpenMatrix4f()).removeTranslation();
        } else {
            transformMatrix = armature.getBindedTransformFor(attackAnimation.getPoseByTime(entityPatch, elapsedTime, 1.0F), joint);
        }

        OpenMatrix4f toWorldCoord = OpenMatrix4f.createTranslation(-((float) entityPatch.getOriginal().getX()), (float) entityPatch.getOriginal().getY(), -((float) entityPatch.getOriginal().getZ()));
        transformMatrix.mulFront(toWorldCoord.mulBack(entityPatch.getModelMatrix(1.0F)));

        //校正旋转
        if(armature instanceof ReplaceableArmature){
            transformMatrix.rotateDeg(90, Vec3f.X_AXIS);
            transformMatrix.rotateDeg(90, Vec3f.Z_AXIS);
        }
        this.transform(transformMatrix);

        cir.setReturnValue(this.getCollideEntities(entityPatch.getOriginal()));
    }

    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    @OnlyIn(Dist.CLIENT)
    private void sword_soaring$draw(PoseStack poseStack, MultiBufferSource buffer, LivingEntityPatch<?> entityPatch, AttackAnimation animation, Joint joint, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed, CallbackInfo ci){
        Armature armature = entityPatch.getArmature();
        String pathIndex = armature.searchPathIndex(joint.getName());
        EntityState state = animation.getState(entityPatch, elapsedTime);
        EntityState prevState = animation.getState(entityPatch, prevElapsedTime);
        boolean attacking = prevState.attacking() || state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2;
        Pose prevPose;
        Pose currentPose;
        if (StringUtil.isNullOrEmpty(pathIndex)) {
            prevPose = new Pose();
            currentPose = new Pose();
            prevPose.putJointData("Root", JointTransform.empty());
            currentPose.putJointData("Root", JointTransform.empty());
            animation.modifyPose(animation, prevPose, entityPatch, prevElapsedTime, 0.0F);
            animation.modifyPose(animation, currentPose, entityPatch, elapsedTime, 1.0F);
        } else {
            prevPose = animation.getPoseByTime(entityPatch, prevElapsedTime, 0.0F);
            currentPose = animation.getPoseByTime(entityPatch, elapsedTime, 1.0F);
        }
        //校正旋转 FIXME 是否重复旋转，存疑，服务端出伤异常
        if(armature instanceof ReplaceableArmature){
            poseStack.mulPose(QuaternionUtils.XP.rotationDegrees(90));
            poseStack.mulPose(QuaternionUtils.ZP.rotationDegrees(90));
        }
        this.drawInternal(poseStack, buffer.getBuffer(this.getRenderType()), armature, joint, prevPose, currentPose, partialTicks, attacking ? -65536 : -1);

        ci.cancel();
    }

}
