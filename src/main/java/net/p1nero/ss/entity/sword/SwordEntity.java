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
import net.p1nero.ss.entity.SwordSoaringEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwordEntity extends TamableAnimal implements AbstractSwordEntity{
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(SwordEntity.class, EntityDataSerializers.ITEM_STACK);

    public SwordEntity(EntityType<? extends TamableAnimal> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        this.getEntityData().define(ITEM_STACK, ItemStack.EMPTY);
    }
    public SwordEntity(ItemStack itemStack, Player owner) {
        super(SwordSoaringEntities.SWORD.get(), owner.level);
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
     * FIXME
     * 调整姿势
     */
    @Override
    public void setPose(PoseStack poseStack){

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
