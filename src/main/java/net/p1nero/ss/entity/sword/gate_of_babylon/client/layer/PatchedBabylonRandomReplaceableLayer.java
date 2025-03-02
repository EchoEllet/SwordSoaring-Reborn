package net.p1nero.ss.entity.sword.gate_of_babylon.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.IReplaceableArmature;
import net.p1nero.ss.entity.client.layer.PatchedReplaceableLayer;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.util.MathUtils;
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

    protected void renderLayer(T entityPatch, E entity, RenderLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
        if (entityPatch.getArmature() instanceof IReplaceableArmature armature) {
            renderItemInJoint(entity, entityPatch, armature, poses, buffer, postStack, 0xf000ff);
        }
    }

    /**
     * 根据王之宝库随机替换Joint的位置渲染
     */
    public static void renderItemInJoint(BabylonEntity entity, AbstractArtifactSpiritPatch<?> artifactSpiritPatch, IReplaceableArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        if (entity.getOwner() != null) {
            List<Item> babylons = entity.getValidBabylonItems();
            if (babylons.isEmpty()) {
                PatchedReplaceableLayer.renderItemInJoint(EpicFightItems.GOLDEN_LONGSWORD.get().getDefaultInstance(), artifactSpiritPatch, armature, poses, buffer, poseStack, packedLight);
            }
            List<Joint> jointList = armature.getJoints(artifactSpiritPatch);
            Random random = new Random(entity.getSeed());
            for (int i = 0; i < jointList.size(); i++) {
                Joint joint = jointList.get(i);
                Item item;
                if (i < babylons.size()) {
                    item = babylons.get(i);//尽可能都用上
                } else {
                    item = babylons.get(random.nextInt(babylons.size()));
                }
                if (item != null) {
                    OpenMatrix4f jointTransform = poses[joint.getId()];
                    poseStack.pushPose();
                    MathUtils.mulPoseStack(poseStack, jointTransform);
                    ItemTransforms.TransformType transformType = ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                    Minecraft.getInstance().getItemInHandRenderer().renderItem(artifactSpiritPatch.getOriginal(), item.getDefaultInstance(), transformType, false, poseStack, buffer, packedLight);
                    poseStack.popPose();
                }
            }
        }
    }

}