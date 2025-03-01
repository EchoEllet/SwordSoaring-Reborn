package net.p1nero.ss.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.capability.SSCapabilityProvider;
import net.p1nero.ss.capability.SSPlayer;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.IReplaceableArmature;
import net.p1nero.ss.util.MathUtils;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PatchedRandomReplaceableLayer<E extends AbstractArtifactSpiritEntity, T extends AbstractArtifactSpiritPatch<E>, M extends EntityModel<E>, AM extends AnimatedMesh> extends PatchedLayer<E, T, M, RenderLayer<E, M>, AM> {
    public PatchedRandomReplaceableLayer() {
        super(null);
    }

    protected void renderLayer(T entityPatch, E entity, RenderLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
        if (entityPatch.getArmature() instanceof IReplaceableArmature armature) {
            renderItemInJoint(entity, entityPatch, armature, poses, buffer, postStack, packedLightIn);
        }
    }

    /**
     * 根据王之宝库随机替换Joint的位置渲染
     */
    public static void renderItemInJoint(AbstractArtifactSpiritEntity entity, AbstractArtifactSpiritPatch<?> artifactSpiritPatch, IReplaceableArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        if (entity.getOwner() != null) {
            SSPlayer ssPlayer = entity.getOwner().getCapability(SSCapabilityProvider.SS_PLAYER).orElse(new SSPlayer());
            List<Item> babylons = ssPlayer.getValidBabylonItems();
            Collections.shuffle(babylons);//打乱
            List<Joint> jointList = armature.getJoints(artifactSpiritPatch);

            for (int i = 0; i < jointList.size(); i++) {
                Joint joint = jointList.get(i);
                Item item;
                if(i < babylons.size()){
                    item = babylons.get(i);//尽可能都用上
                } else {
                    item = babylons.get(entity.getRandom().nextInt(babylons.size()));
                }
                if(item != null){
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
