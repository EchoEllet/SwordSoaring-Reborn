package net.p1nero.ss.entity.sword.gate_of_babylon.client.layer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.IReplaceableArmature;
import net.p1nero.ss.entity.client.layer.PatchedReplaceableLayer;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.util.MathUtils;
import org.lwjgl.opengl.GL11C;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.List;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class PatchedBabylonRandomReplaceableLayer<E extends BabylonEntity, T extends AbstractArtifactSpiritPatch<E>, M extends EntityModel<E>, AM extends AnimatedMesh> extends PatchedLayer<E, T, M, RenderLayer<E, M>, AM> {
    public PatchedBabylonRandomReplaceableLayer() {
        super(null);
    }
    public static final int FADE_TIME = 30, LIFE_TIME = 130;
    public static final ResourceLocation PORTAL_TEXTURE = new ResourceLocation(SwordSoaring.MOD_ID, "textures/entity/portal.png");

    protected void renderLayer(T entityPatch, E entity, RenderLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
        if (entityPatch.getArmature() instanceof IReplaceableArmature armature) {
            renderItemInJoint(entity, entityPatch, armature, poses, buffer, postStack, LightTexture.FULL_BRIGHT);
        }
    }

    /**
     * 根据王之宝库随机替换Joint的位置渲染
     */
    public static void renderItemInJoint(BabylonEntity entity, AbstractArtifactSpiritPatch<?> artifactSpiritPatch, IReplaceableArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        if (entity.getOwner() != null) {
            List<ItemStack> babylons = entity.getValidBabylonItems();
            if (babylons.isEmpty()) {
                PatchedReplaceableLayer.renderItemInJoint(EpicFightItems.GOLDEN_LONGSWORD.get().getDefaultInstance(), artifactSpiritPatch, armature, poses, buffer, poseStack, packedLight);
            }
            List<Joint> jointList = armature.getJoints(artifactSpiritPatch);
            Random random = new Random(entity.getSeed());
            for (int i = 0; i < jointList.size(); i++) {
                Joint joint = jointList.get(i);
                ItemStack itemStack;
                if (i < babylons.size()) {
                    itemStack = babylons.get(i);//尽可能都用上
                } else {
                    itemStack = babylons.get(random.nextInt(babylons.size()));
                }
                if (itemStack != null) {
                    OpenMatrix4f jointTransform = poses[joint.getId()];
                    if (entity.getStartTransform(joint.getId()) == null) {
                        entity.bindStartTransform(joint.getId(), MathUtils.removeScale(jointTransform));
                    }
                    //画传送门
                    poseStack.pushPose();
                    OpenMatrix4f startMatrix = entity.getStartTransform(joint.getId());
                    MathUtils.mulPoseStack(poseStack, startMatrix == null ? jointTransform : startMatrix);
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
                    float alpha = 1.0F;
                    int currentTickCount = entity.tickCount - 15;
                    if(currentTickCount < FADE_TIME){
                        alpha = currentTickCount * 1.0F / FADE_TIME;
                    }
                    if(currentTickCount > LIFE_TIME - FADE_TIME){
                        alpha = (LIFE_TIME - currentTickCount) * 1.0F / FADE_TIME;
                    }
                    //画核心圈圈
                    poseStack.pushPose();
                    poseStack.scale(alpha, alpha, alpha);
                    renderPortal(poseStack, alpha, 1.0F, 1.0F, 1.0F, PORTAL_TEXTURE);
                    poseStack.popPose();
                    //画不断放大的圈圈
                    if(currentTickCount < LIFE_TIME - FADE_TIME){
                        alpha = (currentTickCount * 1.0F % FADE_TIME) / FADE_TIME;
                        poseStack.scale(alpha, alpha, alpha);
                        renderPortal(poseStack, alpha, 1.0F, 1.0F, 1.0F, PORTAL_TEXTURE);
                    }
                    poseStack.popPose();

                    poseStack.pushPose();
                    MathUtils.mulPoseStack(poseStack, jointTransform);
                    ItemTransforms.TransformType transformType = ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                    Minecraft.getInstance().getItemInHandRenderer().renderItem(artifactSpiritPatch.getOriginal(), itemStack, transformType, false, poseStack, buffer, packedLight);
                    poseStack.popPose();
                }
            }
        }
    }

    /**
     * 曦月哥的恩情还不完\ToT/  \ToT/  \ToT/  \ToT/
     */
    public static void renderPortal(PoseStack poseStack, float alpha, float r, float g, float b, ResourceLocation portalTexture) {
        poseStack.pushPose();
        poseStack.scale(0.75F, 0.75F, 0.75F);
        RenderSystem.setShaderTexture(0, portalTexture);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(519);
        float[] original = RenderSystem.getShaderColor();
        RenderSystem.setShaderColor(r, b, g, alpha);
        GuiComponent.blit(poseStack, -1, -1, 0, 0.0F, 0.0F, 2, 2, 2, 2);
        RenderSystem.setShaderColor(original[0], original[1], original[2], original[3]);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        poseStack.popPose();
    }

}