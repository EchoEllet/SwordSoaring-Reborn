package net.p1nero.ss.entity.screen_sword.client;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.screen_sword.ScreenSword;
import net.p1nero.ss.entity.vatansever.client.VatanseverRenderer;
import org.jetbrains.annotations.NotNull;

public class ScreenSwordRenderer extends MobRenderer<ScreenSword, EmptyEntityModel<ScreenSword>> {
    public ScreenSwordRenderer(EntityRendererProvider.Context context) {
        super(context, new EmptyEntityModel<>(), 1);
        this.addLayer(new ReplaceableRenderLayer<>(this));
    }

    @Override
    public boolean shouldRender(@NotNull ScreenSword p_115468_, @NotNull Frustum p_115469_, double p_115470_, double p_115471_, double p_115472_) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ScreenSword lianRenEntity) {
        return VatanseverRenderer.TEXTURE;
    }



}
