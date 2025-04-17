package net.p1nero.ss.entity.wraithon.client;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.client.model.EmptyEntityModel;
import net.p1nero.ss.entity.wraithon.WraithonEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WraithonRenderer extends MobRenderer<WraithonEntity, EmptyEntityModel<WraithonEntity>> {
    public static final List<ResourceLocation> TEXTURES = List.of(ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "textures/entity/wraithon.png"), ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "textures/entity/wraithon1.png"), ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "textures/entity/wraithon2.png"), ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "textures/entity/wraithon3.png"), ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "textures/entity/wraithon4.png"), ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "textures/entity/wraithon5.png"));
    public WraithonRenderer(EntityRendererProvider.Context context) {
        super(context, new EmptyEntityModel<>(), 1);
    }

    @Override
    public boolean shouldRender(@NotNull WraithonEntity pLivingEntity, @NotNull Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WraithonEntity wraithonEntity) {
        return TEXTURES.get(wraithonEntity.getState());
    }
}
