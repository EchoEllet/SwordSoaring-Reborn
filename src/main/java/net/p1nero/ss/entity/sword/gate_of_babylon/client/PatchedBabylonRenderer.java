package net.p1nero.ss.entity.sword.gate_of_babylon.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.sword.client.PatchedReplaceableRenderer;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.entity.sword.gate_of_babylon.client.layer.PatchedBabylonRandomReplaceableLayer;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@SuppressWarnings({"unchecked", "rawtypes"})
@OnlyIn(Dist.CLIENT)
public class PatchedBabylonRenderer<E extends BabylonEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>, R extends LivingEntityRenderer<E, M>> extends PatchedReplaceableRenderer<E, T, M, R, BabylonMesh> {

    public PatchedBabylonRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
    }

    @Override
    protected void addReplaceablePatchedLayer() {
        this.addPatchedLayer(ReplaceableRenderLayer.class, new PatchedBabylonRandomReplaceableLayer());
    }

    @Override
    public AssetAccessor<BabylonMesh> getDefaultMesh() {
        return SwordSoaringMeshes.BABYLON_MESH;
    }
}