package net.p1nero.ss.entity.wraithon;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.Joint;

public class WraithonPartEntity extends PartEntity<WraithonEntity> {
    public final WraithonEntity parentMob;
    public final Joint joint;
    private final EntityDimensions size;
    private final Vec3 offset;

    public WraithonPartEntity(WraithonEntity pParentMob, Joint joint, float pWidth, float pHeight, Vec3 offset) {
        super(pParentMob);
        this.size = EntityDimensions.scalable(pWidth, pHeight);
        this.refreshDimensions();
        this.parentMob = pParentMob;
        this.joint = joint;
        this.offset = offset;
    }

    public Vec3 getYOffset() {
        return offset;
    }

    public EntityDimensions getSize() {
        return size;
    }

    protected void defineSynchedData() {
    }

    protected void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
    }

    protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
    }

    public boolean isPickable() {
        return true;
    }

    @Nullable
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }

    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return !this.isInvulnerableTo(pSource) && this.parentMob.hurt(this, pSource, pAmount);
    }

    public boolean is(@NotNull Entity pEntity) {
        return this == pEntity || this.parentMob == pEntity;
    }

    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return this.size;
    }

    public boolean shouldBeSaved() {
        return false;
    }
}
