package net.p1nero.ss.entity.sword.gate_of_babylon.client.layer;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.ReplaceableArmature;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.QuaternionUtils;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PatchedBabylonRandomReplaceableLayer<E extends BabylonEntity, T extends AbstractArtifactSpiritPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, RenderLayer<E, M>> {
    public static final int FADE_TIME = 20, LIFE_TIME = 130;
    public static final ResourceLocation LIGHT_TEXTURE = new ResourceLocation(SwordSoaringMod.MOD_ID, "textures/entity/light.png");
    public static final ResourceLocation PORTAL_TEXTURE = new ResourceLocation(SwordSoaringMod.MOD_ID, "textures/entity/portal.png");

    @Override
    protected void renderLayer(T entityPatch, E entity, RenderLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
        if (entityPatch.getArmature() instanceof ReplaceableArmature armature) {
            renderItemInJoint(entity, entityPatch, armature, poses, buffer, postStack, LightTexture.FULL_BRIGHT);
        }
    }

    /**
     * 根据王之宝库随机替换Joint的位置渲染
     */
    public static void renderItemInJoint(BabylonEntity entity, AbstractArtifactSpiritPatch<?> artifactSpiritPatch, ReplaceableArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        if (entity.getOwner() != null) {
            List<ItemStack> babylons = entity.getValidBabylonItems();
            if (babylons.isEmpty()) {
                return;
            }
            List<Joint> jointList = armature.getJoints(artifactSpiritPatch);
            for (int i = 0; i < jointList.size() && i < babylons.size(); i++) {
                Joint joint = jointList.get(i);
                ItemStack itemStack = babylons.get(i);//有多少射多少，穷鬼莫玩
                if (itemStack != null) {
                    OpenMatrix4f jointTransform = poses[joint.getId()];
                    jointTransform.removeScale();
                    if (entity.getStartTransform(joint.getId()) == null && jointTransform.toScaleVector().length() > 0) {
                        entity.bindStartTransform(joint.getId(), jointTransform);
                    }

                    //画传送门
                    poseStack.pushPose();

                    OpenMatrix4f startMatrix = entity.getStartTransform(joint.getId());
                    MathUtils.mulStack(poseStack, startMatrix == null ? jointTransform : startMatrix);
                    poseStack.mulPose(QuaternionUtils.XP.rotationDegrees(90));
                    float alpha = 1.0F;
                    int currentTickCount = entity.tickCount - 15;
                    if(currentTickCount < FADE_TIME){
                        alpha = currentTickCount * 1.0F / FADE_TIME;
                    }
                    if(currentTickCount > LIFE_TIME - FADE_TIME){
                        alpha = (LIFE_TIME - currentTickCount) * 1.0F / FADE_TIME;
                    }
                    final float outerAlpha = alpha;
                    //画不断放大的圈圈
                    if(currentTickCount < LIFE_TIME - FADE_TIME){
                        alpha = (currentTickCount * 1.0F % FADE_TIME) / FADE_TIME;
                        poseStack.pushPose();
                        poseStack.scale(alpha, alpha, alpha);
                        poseStack.pushPose();
                        poseStack.scale(0.5F, 0.5F, 0.5F);
                        renderPortal(poseStack, alpha, 1.0F, 1.0F, 1.0F, PORTAL_TEXTURE, buffer);
                        poseStack.popPose();
                        renderPortal(poseStack, alpha, 1.0F, 1.0F, 1.0F, LIGHT_TEXTURE, buffer);
                        poseStack.popPose();
                    }

                    //画核心圈圈
                    poseStack.pushPose();
                    poseStack.scale(outerAlpha, outerAlpha, outerAlpha);
                    renderPortal(poseStack, alpha, 1.0F, 1.0F, 1.0F, LIGHT_TEXTURE, buffer);
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    renderPortal(poseStack, outerAlpha, 1.0F, 1.0F, 1.0F, PORTAL_TEXTURE, buffer);
                    poseStack.popPose();

                    poseStack.popPose();

                    //画在Joint上
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, jointTransform);
                    ItemDisplayContext transformType = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                    poseStack.mulPose(QuaternionUtils.YP.rotationDegrees(90));
                    Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(artifactSpiritPatch.getOriginal(), itemStack, transformType, false, poseStack, buffer, packedLight);
                    poseStack.popPose();
                }
            }
        }
    }

    /**
     * 曦月哥的恩情还不完\ToT/  \ToT/  \ToT/  \ToT/
     */
    public static void renderPortal(PoseStack poseStack, float alpha, float r, float g, float b, ResourceLocation portalTexture, MultiBufferSource buffer) {
        poseStack.pushPose();
        Matrix4f pMatrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(portalTexture, false));
        consumer.vertex(pMatrix, -1, -1, 0).color(r, g, b, alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normal,1,1,1).endVertex();
        consumer.vertex(pMatrix, 1, -1, 0).color(r, g, b, alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normal, 1,1,1).endVertex();
        consumer.vertex(pMatrix, 1, 1, 0).color(r, g, b, alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normal, 1,1,1).endVertex();
        consumer.vertex(pMatrix, -1, 1, 0).color(r, g, b, alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normal, 1,1,1).endVertex();

        poseStack.popPose();
    }

}