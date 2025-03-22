package net.p1nero.ss.entity.wraithon.client;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.wraithon.WraithonEntity;
import org.jetbrains.annotations.NotNull;

public class WraithonRenderer extends MobRenderer<WraithonEntity, EmptyEntityModel<WraithonEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SwordSoaringMod.MOD_ID, "textures/entity/wraithon.png");
    public WraithonRenderer(EntityRendererProvider.Context context) {
        super(context, new EmptyEntityModel<>(), 1);
    }

    @Override
    public boolean shouldRender(@NotNull WraithonEntity pLivingEntity, @NotNull Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

//    @Override
//    public void render(@NotNull WraithonEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
//        this.shadowRadius = 10;//TODO
//        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
//    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WraithonEntity wraithonEntity) {
        return TEXTURE;
    }
}
