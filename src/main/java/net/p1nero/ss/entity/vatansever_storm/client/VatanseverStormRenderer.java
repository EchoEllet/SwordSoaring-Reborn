package net.p1nero.ss.entity.vatansever_storm.client;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;
import org.jetbrains.annotations.NotNull;

public class VatanseverStormRenderer extends MobRenderer<VatanseverStormEntity, EmptyEntityModel<VatanseverStormEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SwordSoaring.MOD_ID, "textures/entity/vatansever_swordgroup.png");
    public VatanseverStormRenderer(EntityRendererProvider.Context context) {
        super(context, new EmptyEntityModel<>(), 1);
    }

    @Override
    public boolean shouldRender(@NotNull VatanseverStormEntity pLivingEntity, @NotNull Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull VatanseverStormEntity lianRenEntity) {
        return TEXTURE;
    }
}
