package net.p1nero.ss.mixin;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.StringUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.ReplaceableArmature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.MultiCollider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.QuaternionUtils;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Iterator;
import java.util.List;

@Mixin(value = MultiOBBCollider.class, remap = false)
public abstract class MultiOBBColliderMixin extends MultiCollider<OBBCollider> {

    /**
     * 重写draw的都得改
     */
    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    @OnlyIn(Dist.CLIENT)
    private void sword_soaring$draw(PoseStack poseStack, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, AttackAnimation animation, Joint joint, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed, CallbackInfo ci){
        int colliderCount = Math.max(Math.round((float)(this.numberOfColliders + animation.getProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS).orElse(0)) * attackSpeed), this.numberOfColliders);
        float partialScale = 1.0F / (float)(colliderCount - 1);
        float interpolation = 0.0F;
        Armature armature = entitypatch.getArmature();
        String pathIndex = armature.searchPathIndex(joint.getName());
        EntityState state = animation.getState(entitypatch, elapsedTime);
        EntityState prevState = animation.getState(entitypatch, prevElapsedTime);
        boolean attacking = prevState.attacking() || state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2;
        List<OBBCollider> colliders = Lists.newArrayList();
        float index = 0.0F;
        float interIndex = Math.min((float)(this.numberOfColliders - 1) / (float)(colliderCount - 1), 1.0F);

        for(int i = 0; i < colliderCount; ++i) {
            colliders.add(this.colliders.get((int)index).deepCopy());
            index += interIndex;
        }

        for(Iterator var29 = colliders.iterator(); var29.hasNext(); interpolation += partialScale) {
            OBBCollider obbCollider = (OBBCollider)var29.next();
            float pt1 = prevElapsedTime + (elapsedTime - prevElapsedTime) * partialTicks;
            float pt2 = prevElapsedTime + (elapsedTime - prevElapsedTime) * interpolation;
            TransformSheet coordTransform = animation.getCoord();
            Vec3f p1 = coordTransform.getInterpolatedTranslation(pt1);
            Vec3f p2 = coordTransform.getInterpolatedTranslation(pt2);
            poseStack.pushPose();
            poseStack.translate(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);
            Pose pose;
            if (StringUtil.isNullOrEmpty(pathIndex)) {
                pose = new Pose();
                pose.putJointData("Root", JointTransform.empty());
                animation.modifyPose(animation, pose, entitypatch, elapsedTime, 1.0F);
            } else {
                pose = animation.getPoseByTime(entitypatch, pt2, 1.0F);
            }

            //校正旋转 FIXME 是否重复旋转，存疑，服务端出伤异常
//            if(armature instanceof ReplaceableArmature){
//                poseStack.mulPose(QuaternionUtils.XP.rotationDegrees(90));
//                poseStack.mulPose(QuaternionUtils.ZP.rotationDegrees(90));
//            }

            obbCollider.drawInternal(poseStack, buffer.getBuffer(this.getRenderType()), armature, joint, pose, pose, 1.0F, attacking ? -65536 : -1);
            poseStack.popPose();
        }

        ci.cancel();
    }
}
