package net.p1nero.ss.entity.screen_sword.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.screen_sword.ScreenSword;
import net.p1nero.ss.entity.vatansever.client.VatanseverRenderer;
import org.jetbrains.annotations.NotNull;

public class ScreenSwordRenderer extends MobRenderer<ScreenSword, EmptyEntityModel<ScreenSword>> {
    public ScreenSwordRenderer(EntityRendererProvider.Context context) {
        super(context, new EmptyEntityModel<>(), 1);
        this.addLayer(new ScreenSwordRenderLayer(this));
    }

    @Override
    public boolean shouldRender(@NotNull ScreenSword p_115468_, @NotNull Frustum p_115469_, double p_115470_, double p_115471_, double p_115472_) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ScreenSword lianRenEntity) {
        return VatanseverRenderer.TEXTURE;
    }

    public static class ScreenSwordRenderLayer extends RenderLayer<ScreenSword, EmptyEntityModel<ScreenSword>>{

        public ScreenSwordRenderLayer(RenderLayerParent<ScreenSword, EmptyEntityModel<ScreenSword>> pRenderer) {
            super(pRenderer);
        }

        @Override
        public void render(@NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, @NotNull ScreenSword pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            //do nothing
        }
    }

}
