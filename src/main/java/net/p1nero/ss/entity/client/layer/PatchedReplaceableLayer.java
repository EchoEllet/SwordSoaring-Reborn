package net.p1nero.ss.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.ReplaceableArmature;
import net.p1nero.ss.entity.sword.IPatchedItemSupplier;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.QuaternionUtils;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class PatchedReplaceableLayer<E extends LivingEntity & OwnableEntity & IPatchedItemSupplier, T extends LivingEntityPatch<E>, M extends EntityModel<E>, R extends RenderLayer<E, M>> extends PatchedLayer<E, T, M, R> {

    @Override
    protected void renderLayer(T entityPatch, E entity, @Nullable R r, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float v, float v1, float v2, float v3) {
        if(entityPatch.getArmature() instanceof ReplaceableArmature armature){
            ItemStack mainHandStack = entity.getItemStack(entityPatch);
            if (!mainHandStack.isEmpty()) {
                renderItemInJoint(mainHandStack, entityPatch, armature, poses, buffer, poseStack, packedLightIn);
            }
        }
    }

    /**
     * 根据对应要替换Joint的位置渲染
     */
    public static void renderItemInJoint(ItemStack stack, LivingEntityPatch<?> entityPatch, ReplaceableArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        for(Joint joint : armature.getJoints(entityPatch)){
            OpenMatrix4f jointTransform = poses[joint.getId()];
            poseStack.pushPose();
            MathUtils.mulStack(poseStack, jointTransform);
            ItemDisplayContext transformType = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            poseStack.mulPose(QuaternionUtils.YP.rotationDegrees(90));
            Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(entityPatch.getOriginal(), stack, transformType, false, poseStack, buffer, packedLight);
            poseStack.popPose();
        }
    }
}
