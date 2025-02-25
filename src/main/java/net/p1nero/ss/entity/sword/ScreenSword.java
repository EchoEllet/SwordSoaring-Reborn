package net.p1nero.ss.entity.sword;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.util.MathUtil;

public class ScreenSword extends AbstractSwordEntity {
    public ScreenSword(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setPose(PoseStack poseStack) {
        if(getOwner() == null){
            return;
        }
        poseStack.mulPose(MathUtil.rotateTo(new Vec3(0, 1, 0), position().subtract(getOwner().position())));
    }

    @Override
    public void tick() {
        super.tick();
    }

}
