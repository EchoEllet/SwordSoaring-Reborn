package net.p1nero.ss.entity.sword.gate_of_babylon.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.entity.sword.gate_of_babylon.client.layer.PatchedBabylonRandomReplaceableLayer;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import net.p1nero.ss.item.VatanseverItem;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@SuppressWarnings({"unchecked", "rawtypes"})
@OnlyIn(Dist.CLIENT)
public class PatchedBabylonRenderer<E extends BabylonEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLivingEntityRenderer<E, T, M, BabylonMesh> {
    @Override
    public void render(E entityIn, T entityPatch, LivingEntityRenderer<E, M> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        if(entityIn.getOwner() != null && !(entityIn.getItemStack(entityPatch).getItem() instanceof VatanseverItem)){
            this.addPatchedLayer(ReplaceableRenderLayer.class, new PatchedBabylonRandomReplaceableLayer());
            OpenMatrix4f[] poseMatrices = this.getPoseMatrices(entityPatch, entityPatch.getArmature(), partialTicks);
            Armature armature = entityPatch.getArmature();
            this.mulPoseStack(poseStack, armature, entityIn, entityPatch, partialTicks);
            this.renderLayer(renderer, entityPatch, entityIn, poseMatrices, buffer, poseStack, packedLight, partialTicks);

            //画攻击碰撞箱
            Minecraft mc = Minecraft.getInstance();
            boolean isVisible = this.isVisible(renderer, entityIn);
            boolean isVisibleToPlayer = !isVisible && !entityIn.isInvisibleTo(Objects.requireNonNull(mc.player));
            boolean isGlowing = mc.shouldEntityAppearGlowing(entityIn);
            RenderType renderType = this.getRenderType(entityIn, entityPatch, renderer, isVisible, isVisibleToPlayer, isGlowing);
            if (renderType != null && Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
                for (Layer layer : entityPatch.getClientAnimator().getAllLayers()) {
                    AnimationPlayer animPlayer = layer.animationPlayer;
                    float playTime = animPlayer.getPrevElapsedTime() + (animPlayer.getElapsedTime() - animPlayer.getPrevElapsedTime()) * partialTicks;
                    animPlayer.getAnimation().renderDebugging(poseStack, buffer, entityPatch, playTime, partialTicks);
                }
            }
        } else {
            super.render(entityIn, entityPatch, renderer, buffer, poseStack, packedLight, partialTicks);
        }
    }

    @Override
    public BabylonMesh getMesh(T babylonPatch) {
        return SwordSoaringMeshes.babylonMesh;
    }

}