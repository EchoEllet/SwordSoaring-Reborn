package net.p1nero.ss.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.IReplaceableArmature;
import net.p1nero.ss.entity.sword.IPatchedItemSupplier;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.item.EpicFightItems;

@OnlyIn(Dist.CLIENT)
public class PatchedReplaceableLayer<E extends LivingEntity & OwnableEntity & IPatchedItemSupplier, T extends LivingEntityPatch<E>, M extends EntityModel<E>, AM extends AnimatedMesh> extends PatchedLayer<E, T, M, RenderLayer<E, M>, AM> {
    public PatchedReplaceableLayer() {
        super(null);
    }

    protected void renderLayer(T entityPatch, E entity, RenderLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
        if(entityPatch.getArmature() instanceof IReplaceableArmature armature){
            ItemStack mainHandStack = entity.getItemStack(entityPatch);
            if (mainHandStack.getItem() != Items.AIR) {
                renderScreenSword(mainHandStack, entityPatch, armature, poses, buffer, postStack, packedLightIn);
            }
        }
    }

    /**
     * 根据对应要替换Joint的位置渲染
     */
    public static void renderScreenSword(ItemStack stack, LivingEntityPatch<?> entityPatch, IReplaceableArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        for(Joint joint : armature.getJoints(entityPatch)){
            OpenMatrix4f jointTransform = poses[joint.getId()];
//            jointTransform.removeTranslation(); TODO 验证是否有用
            poseStack.pushPose();
            mulPoseStack(poseStack, jointTransform);
            ItemTransforms.TransformType transformType = ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entityPatch.getOriginal(), stack, transformType, false, poseStack, buffer, packedLight);
            poseStack.popPose();
        }
    }

    public static void mulPoseStack(PoseStack poseStack, OpenMatrix4f pose) {
        OpenMatrix4f transposed = pose.transpose(null);
        MathUtils.translateStack(poseStack, pose);
        MathUtils.rotateStack(poseStack, transposed);
        MathUtils.scaleStack(poseStack, transposed);
    }

}
