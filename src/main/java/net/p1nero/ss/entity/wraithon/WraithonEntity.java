package net.p1nero.ss.entity.wraithon;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class WraithonEntity extends PathfinderMob {
    public WraithonEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }



    public static AttributeSupplier getDefaultAttribute() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000F)
                .add(Attributes.ATTACK_DAMAGE, 19.9f)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 10.0F)
                .build();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        hurtTime = 0;//取消受击变色
        return super.hurt(pSource, pAmount);
    }
}
