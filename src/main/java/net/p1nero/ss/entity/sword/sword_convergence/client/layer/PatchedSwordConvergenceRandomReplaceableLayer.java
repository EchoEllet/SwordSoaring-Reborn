package net.p1nero.ss.entity.sword.sword_convergence.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.IReplaceableArmature;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.entity.sword.sword_convergence.SwordConvergenceEntity;
import net.p1nero.ss.util.MathUtils;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;

import java.util.List;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class PatchedSwordConvergenceRandomReplaceableLayer<E extends SwordConvergenceEntity, T extends AbstractArtifactSpiritPatch<E>, M extends EntityModel<E>, AM extends AnimatedMesh> extends PatchedLayer<E, T, M, RenderLayer<E, M>, AM> {
    public PatchedSwordConvergenceRandomReplaceableLayer() {
        super(null);
    }

    protected void renderLayer(T entityPatch, E entity, RenderLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
        if (entityPatch.getArmature() instanceof IReplaceableArmature armature) {
            renderItemInJoint(entity, entityPatch, armature, poses, buffer, postStack, LightTexture.FULL_BRIGHT);
        }
    }

    /**
     * 根据王之宝库随机替换Joint的位置渲染
     */
    public static void renderItemInJoint(SwordConvergenceEntity entity, AbstractArtifactSpiritPatch<?> artifactSpiritPatch, IReplaceableArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        if (entity.getOwner() != null) {
            List<ItemStack> babylons = entity.getValidBabylonItems();
            if (babylons.isEmpty()) {
                return;
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
                    if (entity.getStartTransform(joint.getId()) == null && jointTransform.toScaleVector().length() > 0) {
                        entity.bindStartTransform(joint.getId(), MathUtils.removeScale(jointTransform));
                    }

                    //画在Joint上
                    poseStack.pushPose();
                    MathUtils.mulPoseStack(poseStack, jointTransform);
                    ItemTransforms.TransformType transformType = ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                    poseStack.scale(0.3F, 0.3F, 0.3F);
                    Minecraft.getInstance().getItemInHandRenderer().renderItem(artifactSpiritPatch.getOriginal(), itemStack, transformType, false, poseStack, buffer, packedLight);
                    poseStack.popPose();
                }
            }
        }
    }

}