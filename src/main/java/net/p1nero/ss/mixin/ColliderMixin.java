package net.p1nero.ss.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.LongArmature;
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
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

@Mixin(value = Collider.class, remap = false)
public abstract class ColliderMixin {

    @Shadow protected abstract void transform(OpenMatrix4f mat);

    @Shadow public abstract List<Entity> getCollideEntities(Entity entity);

    @Shadow public abstract void drawInternal(PoseStack poseStack, MultiBufferSource multiBufferSource, OpenMatrix4f openMatrix4f, boolean b);

    @Inject(method = "updateAndSelectCollideEntity", at = @At("HEAD"), cancellable = true)
    private void lianren$updateAndSelectCollideEntity(LivingEntityPatch<?> entityPatch, AttackAnimation attackAnimation, float prevElapsedTime, float elapsedTime, Joint joint, float attackSpeed, CallbackInfoReturnable<List<Entity>> cir){
        Armature armature = entityPatch.getArmature();
        if(armature instanceof LongArmature longArmature){
            long pathIndex = longArmature.searchPathIndexLong(joint.getName());

            OpenMatrix4f transformMatrix;

            if (pathIndex == -1) {
                Pose rootPose = new Pose();
                rootPose.putJointData("Root", JointTransform.empty());
                attackAnimation.modifyPose(attackAnimation, rootPose, entityPatch, elapsedTime, 1.0F);
                transformMatrix = rootPose.getOrDefaultTransform("Root").getAnimationBindedMatrix(entityPatch.getArmature().rootJoint, new OpenMatrix4f()).removeTranslation();
            } else {
                transformMatrix = longArmature.getBindedTransformByJointIndex(attackAnimation.getPoseByTime(entityPatch, elapsedTime, 1.0F), pathIndex);
            }

            OpenMatrix4f toWorldCoord = OpenMatrix4f.createTranslation(-(float)entityPatch.getOriginal().getX(), (float)entityPatch.getOriginal().getY(), -(float)entityPatch.getOriginal().getZ());
            transformMatrix.mulFront(toWorldCoord.mulBack(entityPatch.getModelMatrix(1.0F)));
            this.transform(transformMatrix);
            cir.setReturnValue(this.getCollideEntities(entityPatch.getOriginal()));
        }
    }

    @Inject(method = "draw", at = @At("HEAD"))
    @OnlyIn(Dist.CLIENT)
    private void lianren$draw(PoseStack matrixStackIn, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, AttackAnimation animation, Joint joint, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed, CallbackInfo ci){
        Armature armature = entitypatch.getArmature();
        if(armature instanceof LongArmature longArmature){
            long pathIndex = longArmature.searchPathIndexLong(joint.getName());
            EntityState state = animation.getState(entitypatch, elapsedTime);
            EntityState prevState = animation.getState(entitypatch, prevElapsedTime);
            boolean flag3 = prevState.attacking() || state.attacking() || (prevState.getLevel() < 2 && state.getLevel() > 2);
            OpenMatrix4f mat;
            if (pathIndex == -1) {
                Pose rootPose = new Pose();
                rootPose.putJointData("Root", JointTransform.empty());
                animation.modifyPose(animation, rootPose, entitypatch, elapsedTime, 1.0F);
                mat = rootPose.getOrDefaultTransform("Root").getAnimationBindedMatrix(entitypatch.getArmature().rootJoint, new OpenMatrix4f()).removeTranslation();
            } else {
                mat = longArmature.getBindedTransformByJointIndex(animation.getPoseByTime(entitypatch, elapsedTime, 0.0F), pathIndex);
            }
            this.drawInternal(matrixStackIn, buffer, mat, flag3);
        }
    }

}
