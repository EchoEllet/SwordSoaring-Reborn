package net.p1nero.ss.mixin;

import net.minecraft.world.entity.Entity;
import net.p1nero.ss.entity.ReplaceableArmature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

@Mixin(value = Collider.class, remap = false)
public abstract class ColliderMixin {

    @Shadow protected abstract void transform(OpenMatrix4f mat);

    @Shadow public abstract List<Entity> getCollideEntities(Entity entity);

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

}
