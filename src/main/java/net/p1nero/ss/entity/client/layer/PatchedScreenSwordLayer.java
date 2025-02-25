package net.p1nero.ss.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.screen_sword.ScreenSword;
import net.p1nero.ss.entity.screen_sword.ScreenSwordArmature;
import net.p1nero.ss.entity.screen_sword.client.ScreenSwordMesh;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.item.EpicFightItems;

@OnlyIn(Dist.CLIENT)
public class PatchedScreenSwordLayer<E extends ScreenSword, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, RenderLayer<E, M>, ScreenSwordMesh> {
    public PatchedScreenSwordLayer() {
        super(null);
    }

    protected void renderLayer(T entityPatch, E entity, RenderLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
        if (entityPatch.getArmature() instanceof ScreenSwordArmature screenSwordArmature) {
            ItemStack mainHandStack = entity.getOwner() != null ? entity.getOwner().getMainHandItem() : EpicFightItems.IRON_LONGSWORD.get().getDefaultInstance();
            if (mainHandStack.getItem() != Items.AIR) {
                new RenderScreenSword().renderScreenSword(mainHandStack, entityPatch, screenSwordArmature, poses, buffer, postStack, packedLightIn);
            }

        }
    }

    public static class RenderScreenSword extends RenderItemBase {

        /**
         * 根据六把剑的位置渲染
         */
        public void renderScreenSword(ItemStack stack, LivingEntityPatch<?> entityPatch, ScreenSwordArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
            OpenMatrix4f modelMatrix = this.getCorrectionMatrix(stack, entityPatch, InteractionHand.MAIN_HAND);
            for(Joint joint : armature.joints){
                OpenMatrix4f jointTransform = poses[joint.getId()];
                modelMatrix.mulFront(jointTransform);
                poseStack.pushPose();
                this.mulPoseStack(poseStack, modelMatrix);
                ItemTransforms.TransformType transformType = ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                Minecraft.getInstance().getItemInHandRenderer().renderItem(entityPatch.getOriginal(), stack, transformType, false, poseStack, buffer, packedLight);
                poseStack.popPose();
            }
        }
    }

}
