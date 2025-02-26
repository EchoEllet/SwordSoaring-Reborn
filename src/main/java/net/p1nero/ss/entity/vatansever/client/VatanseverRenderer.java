package net.p1nero.ss.entity.vatansever.client;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import org.jetbrains.annotations.NotNull;

public class VatanseverRenderer extends MobRenderer<VatanseverEntity, EmptyEntityModel<VatanseverEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SwordSoaring.MOD_ID, "textures/entity/vatansever.png");
    public VatanseverRenderer(EntityRendererProvider.Context context) {
        super(context, new EmptyEntityModel<>(), 1);
    }

    /**
     * 延缓5tick，遮羞一下
     */
    @Override
    public boolean shouldRender(@NotNull VatanseverEntity pLivingEntity, @NotNull Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return pLivingEntity.tickCount > 5;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull VatanseverEntity lianRenEntity) {
        return TEXTURE;
    }
}
