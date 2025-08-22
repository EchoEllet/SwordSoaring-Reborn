package net.p1nero.ss.entity.sword.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.layer.PatchedReplaceableLayer;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;
import net.p1nero.ss.item.VatanseverItem;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.mixin.client.MixinLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public abstract class PatchedReplaceableRenderer<E extends AbstractSwordEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>, R extends LivingEntityRenderer<E, M>, AM extends SkinnedMesh> extends PatchedLivingEntityRenderer<E, T, M, R, AM> {

    public PatchedReplaceableRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
        this.addReplaceablePatchedLayer();
    }

    /**
     * 有主人就渲染主人主手物品，无主人或是Vatansever就渲染真身
     */
    @Override
    public void render(E entityIn, T entityPatch, R renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        if(entityIn.getOwner() != null && !(entityIn.getItemStack(entityPatch).getItem() instanceof VatanseverItem)){
            Armature armature = entityPatch.getArmature();
            this.mulPoseStack(poseStack, armature, entityIn, entityPatch, partialTicks);
            this.setArmaturePose(entityPatch, armature, partialTicks);
            OpenMatrix4f[] poseMatrices = armature.getPoseMatrices();
            this.renderLayer(renderer, entityPatch, entityIn, poseMatrices, buffer, poseStack, LightTexture.FULL_BRIGHT, partialTicks);
            this.renderDebug(entityIn, entityPatch, renderer, buffer, poseStack, partialTicks);
        } else {
            super.render(entityIn, entityPatch, renderer, buffer, poseStack, packedLight, partialTicks);
        }
    }


    protected void addReplaceablePatchedLayer(){
        this.addPatchedLayer(ReplaceableRenderLayer.class, new PatchedReplaceableLayer<>());
    }

    /**
     * 画攻击碰撞箱
     */
    public void renderDebug(E entityIn, T entityPatch, LivingEntityRenderer<E, M> renderer, MultiBufferSource buffer, PoseStack poseStack, float partialTicks){
        Minecraft mc = Minecraft.getInstance();
        MixinLivingEntityRenderer livingEntityRendererAccessor = (MixinLivingEntityRenderer)renderer;
        boolean isVisible = livingEntityRendererAccessor.invokeIsBodyVisible(entityIn);
        boolean isVisibleToPlayer = !isVisible && !entityIn.isInvisibleTo(Objects.requireNonNull(mc.player));
        boolean isGlowing = mc.shouldEntityAppearGlowing(entityIn);
        RenderType renderType = livingEntityRendererAccessor.invokeGetRenderType(entityIn, isVisible, isVisibleToPlayer, isGlowing);
        if (renderType != null && Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            for (Layer layer : entityPatch.getClientAnimator().getAllLayers()) {
                AnimationPlayer animPlayer = layer.animationPlayer;
                float playTime = animPlayer.getPrevElapsedTime() + (animPlayer.getElapsedTime() - animPlayer.getPrevElapsedTime()) * partialTicks;
                animPlayer.getAnimation().get().renderDebugging(poseStack, buffer, entityPatch, playTime, partialTicks);
            }
        }
    }

}