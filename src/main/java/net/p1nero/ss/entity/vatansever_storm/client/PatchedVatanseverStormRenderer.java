package net.p1nero.ss.entity.vatansever_storm.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class PatchedVatanseverStormRenderer extends PatchedLivingEntityRenderer<VatanseverStormEntity, VatanseverStormEntityPatch, EmptyEntityModel<VatanseverStormEntity>, LivingEntityRenderer<VatanseverStormEntity, EmptyEntityModel<VatanseverStormEntity>>, VatanseverStormMesh> {
    public static final int FADE_TIME = 100;

    public PatchedVatanseverStormRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
    }

    @Override
    public AssetAccessor<VatanseverStormMesh> getDefaultMesh() {
        return SwordSoaringMeshes.vatanseverStormMesh;
    }

//    /**
//     * TODO 做渐隐
//     */
//    public float getAlpha(VatanseverStormEntity entityIn){
//        float alpha = 1.0F;
//        int dif = VatanseverStormEntity.MAX_LIFE_TIME - entityIn.tickCount;
//        if(dif < FADE_TIME){
//            alpha = dif * 1.0F / FADE_TIME;
//        }
//        return alpha;
//    }
//
//    @Override
//    public RenderType getRenderType(VatanseverStormEntity entityIn, VatanseverStormEntityPatch entitypatch, LivingEntityRenderer<VatanseverStormEntity, EmptyEntityModel<VatanseverStormEntity>> renderer, boolean isVisible, boolean isVisibleToPlayer, boolean isGlowing) {
//        return RenderType.entityTranslucent(renderer.getTextureLocation(entityIn));
//    }

}