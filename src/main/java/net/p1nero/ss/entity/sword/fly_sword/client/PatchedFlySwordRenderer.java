package net.p1nero.ss.entity.sword.fly_sword.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.sword.client.PatchedReplaceableRenderer;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class PatchedFlySwordRenderer<E extends FlySwordEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>, R extends LivingEntityRenderer<E, M>> extends PatchedReplaceableRenderer<E, T, M, R, FlySwordMesh> {

    public PatchedFlySwordRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
    }

    @Override
    public AssetAccessor<FlySwordMesh> getDefaultMesh() {
        return SwordSoaringMeshes.FLY_SWORD_MESH;
    }
}