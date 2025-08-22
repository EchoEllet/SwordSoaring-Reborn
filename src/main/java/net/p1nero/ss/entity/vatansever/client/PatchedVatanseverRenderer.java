package net.p1nero.ss.entity.vatansever.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class PatchedVatanseverRenderer extends PatchedLivingEntityRenderer<VatanseverEntity, VatanseverEntityPatch, EmptyEntityModel<VatanseverEntity>, VatanseverRenderer, VatanseverMesh> {

    public PatchedVatanseverRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
    }

    @Override
    protected void prepareModel(VatanseverMesh mesh, VatanseverEntity entity, VatanseverEntityPatch entitypatch, VatanseverRenderer renderer) {
        super.prepareModel(mesh, entity, entitypatch, renderer);
        mesh.swordLists.forEach((part -> part.setHidden(false)));
        for (int i = entitypatch.getLeftSwordCount(); i < 6; i++) {
            mesh.swordLists.get(5 - i).setHidden(true);
        }
    }

    @Override
    public AssetAccessor<VatanseverMesh> getDefaultMesh() {
        return SwordSoaringMeshes.vatanseverMesh;
    }
}