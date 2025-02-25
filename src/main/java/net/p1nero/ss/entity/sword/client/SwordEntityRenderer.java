package net.p1nero.ss.entity.sword.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;
import org.jetbrains.annotations.NotNull;

public class SwordEntityRenderer extends EntityRenderer<AbstractSwordEntity> {

    public SwordEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * 调用物品渲染方法，渲染实体绑定的物品。
     * 原理是拦截渲染实体的一些参数，然后用于渲染物品。
     */
    @Override
    public void render(AbstractSwordEntity sword, float p_114486_, float p_114487_, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light) {
        poseStack.pushPose();
        sword.setPose(poseStack);
        BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(sword.getItemStack());
        Minecraft.getInstance().getItemRenderer().render(sword.getItemStack(), ItemTransforms.TransformType.FIXED, false, poseStack, multiBufferSource, light, 1, model);
        poseStack.popPose();
    }

    /**
     * 好像没什么用但是Renderer不能没有
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractSwordEntity swordEntity) {
        return TextureMapping.getItemTexture(swordEntity.getItemStack().getItem());
    }
}
