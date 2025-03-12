package net.p1nero.ss.entity.sword.sword_convergence.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.sword.client.PatchedReplaceableRenderer;
import net.p1nero.ss.entity.sword.sword_convergence.SwordConvergenceEntity;
import net.p1nero.ss.entity.sword.sword_convergence.SwordConvergencePatch;
import net.p1nero.ss.entity.sword.sword_convergence.client.layer.PatchedSwordConvergenceRandomReplaceableLayer;
import net.p1nero.ss.entity.vatansever_storm.client.VatanseverStormMesh;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.api.asset.AssetAccessor;

@OnlyIn(Dist.CLIENT)
public class PatchedSwordConvergenceRenderer extends PatchedReplaceableRenderer<SwordConvergenceEntity, SwordConvergencePatch, EmptyEntityModel<SwordConvergenceEntity>, LivingEntityRenderer<SwordConvergenceEntity, EmptyEntityModel<SwordConvergenceEntity>>, VatanseverStormMesh> {

    public PatchedSwordConvergenceRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
    }

    @Override
    protected void addReplaceablePatchedLayer() {
        this.addPatchedLayer(ReplaceableRenderLayer.class, new PatchedSwordConvergenceRandomReplaceableLayer<>());
    }

    @Override
    public AssetAccessor<VatanseverStormMesh> getDefaultMesh() {
        return SwordSoaringMeshes.vatanseverStormMesh;
    }
}