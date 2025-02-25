package net.p1nero.ss.entity.sword;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.util.MathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSwordEntity extends TamableAnimal implements ISwordEntity {
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(AbstractSwordEntity.class, EntityDataSerializers.ITEM_STACK);

    public AbstractSwordEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.getEntityData().define(ITEM_STACK, ItemStack.EMPTY);
    }
    public AbstractSwordEntity(ItemStack itemStack, Player owner) {
        super(SwordSoaringEntities.SCREEN_SWORD.get(), owner.level);
        this.getEntityData().define(ITEM_STACK, itemStack);
        this.tame(owner);
    }

    @Override
    public ItemStack getItemStack() {
        return this.getEntityData().get(ITEM_STACK);
    }

    public void setItemStack(ItemStack itemStack) {
        this.getEntityData().set(ITEM_STACK, itemStack);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_19947_) {
        return false;
    }

    /**
     * 调整姿势
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void setPose(PoseStack poseStack){
        poseStack.mulPose(MathUtil.rotateTo(new Vec3(0, 1, 0), getDeltaMovement()));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.getEntityData().set(ITEM_STACK, ItemStack.of(tag.getCompound("item_stack")));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.put("item_stack", this.getEntityData().get(ITEM_STACK).serializeNBT());
    }

}
