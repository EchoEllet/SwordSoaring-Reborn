package net.p1nero.ss.entity.sword.wan.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.sword.client.PatchedReplaceableRenderer;
import net.p1nero.ss.entity.sword.wan.WanEntity;
import net.p1nero.ss.entity.sword.wan.WanPatch;
import net.p1nero.ss.entity.sword.wan.client.layer.PatchedWanRandomReplaceableLayer;
import net.p1nero.ss.entity.vatansever_storm.client.VatanseverStormMesh;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.api.asset.AssetAccessor;

@OnlyIn(Dist.CLIENT)
public class PatchedWanRenderer extends PatchedReplaceableRenderer<WanEntity, WanPatch, EmptyEntityModel<WanEntity>, LivingEntityRenderer<WanEntity, EmptyEntityModel<WanEntity>>, VatanseverStormMesh> {

    public PatchedWanRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
    }

    @Override
    protected void addReplaceablePatchedLayer() {
        this.addPatchedLayer(ReplaceableRenderLayer.class, new PatchedWanRandomReplaceableLayer<>());
    }

    @Override
    public AssetAccessor<VatanseverStormMesh> getDefaultMesh() {
        return SwordSoaringMeshes.vatanseverStormMesh;
    }
}