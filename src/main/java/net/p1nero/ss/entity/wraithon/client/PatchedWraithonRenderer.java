package net.p1nero.ss.entity.wraithon.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.wraithon.WraithonEntity;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

public class PatchedWraithonRenderer extends PatchedLivingEntityRenderer<WraithonEntity, WraithonEntityPatch, EmptyEntityModel<WraithonEntity>, LivingEntityRenderer<WraithonEntity, EmptyEntityModel<WraithonEntity>>, WraithonMesh> {

    public PatchedWraithonRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
    }

    @Override
    public AssetAccessor<WraithonMesh> getDefaultMesh() {
        return SwordSoaringMeshes.WRAITHON_MESH;
    }
}
