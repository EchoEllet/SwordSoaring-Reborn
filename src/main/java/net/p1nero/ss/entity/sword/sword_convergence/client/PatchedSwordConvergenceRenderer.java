package net.p1nero.ss.entity.sword.sword_convergence.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.sword.sword_convergence.SwordConvergenceEntity;
import net.p1nero.ss.entity.sword.sword_convergence.SwordConvergencePatch;
import net.p1nero.ss.entity.sword.sword_convergence.client.layer.PatchedSwordConvergenceRandomReplaceableLayer;
import net.p1nero.ss.entity.vatansever_storm.client.VatanseverStormMesh;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import net.p1nero.ss.item.VatanseverItem;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class PatchedSwordConvergenceRenderer extends PatchedLivingEntityRenderer<SwordConvergenceEntity, SwordConvergencePatch, EmptyEntityModel<SwordConvergenceEntity>, VatanseverStormMesh> {

    @Override
    public void render(SwordConvergenceEntity entityIn, SwordConvergencePatch entityPatch, LivingEntityRenderer<SwordConvergenceEntity, EmptyEntityModel<SwordConvergenceEntity>> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        if(entityIn.getOwner() != null && !(entityIn.getItemStack(entityPatch).getItem() instanceof VatanseverItem)){
            this.addPatchedLayer(ReplaceableRenderLayer.class, new PatchedSwordConvergenceRandomReplaceableLayer<>());
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
    public VatanseverStormMesh getMesh(SwordConvergencePatch vatanseverStormEntityPatch) {
        return SwordSoaringMeshes.vatanseverStormMesh;
    }

}