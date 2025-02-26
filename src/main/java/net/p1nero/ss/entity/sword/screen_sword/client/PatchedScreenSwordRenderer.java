package net.p1nero.ss.entity.sword.screen_sword.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.layer.PatchedReplaceableLayer;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSword;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class PatchedScreenSwordRenderer<E extends ScreenSword, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLivingEntityRenderer<E, T, M, ScreenSwordMesh> {

    /**
     * 有主人就渲染主人主手物品，无主人就渲染真身
     */
    @Override
    public void render(E entityIn, T entityPatch, LivingEntityRenderer<E, M> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        if(entityIn.getOwner() != null){
            this.addPatchedLayer(ReplaceableRenderLayer.class, new PatchedReplaceableLayer<>());
            OpenMatrix4f[] poseMatrices = this.getPoseMatrices(entityPatch, entityPatch.getArmature(), partialTicks);
            this.renderLayer(renderer, entityPatch, entityIn, poseMatrices, buffer, poseStack, packedLight, partialTicks);
        } else {
            super.render(entityIn, entityPatch, renderer, buffer, poseStack, packedLight, partialTicks);
        }
    }

    @Override
    public ScreenSwordMesh getMesh(T t) {
        return SwordSoaringMeshes.screenSwordMesh;
    }

}