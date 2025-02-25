package net.p1nero.ss.entity.sword;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ISwordEntity {
    @OnlyIn(Dist.CLIENT)
    void setPose(PoseStack poseStack);
    ItemStack getItemStack();

}
