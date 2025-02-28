package net.p1nero.ss.entity.sword;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class AbstractSwordEntity extends AbstractArtifactSpiritEntity implements IPatchedItemSupplier {
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(AbstractSwordEntity.class, EntityDataSerializers.ITEM_STACK);

    public AbstractSwordEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }
    public AbstractSwordEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, ItemStack itemStack, Player owner) {
        super(entityType, owner.level);
        this.setItemStack(itemStack);
        this.tame(owner);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        this.getEntityData().set(ITEM_STACK, ItemStack.of(tag.getCompound("item_stack")));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        tag.put("item_stack", this.getEntityData().get(ITEM_STACK).serializeNBT());
    }

    @Override
    public ItemStack getItemStack(@Nullable LivingEntityPatch<?> livingEntityPatch) {
        return this.getEntityData().get(ITEM_STACK);
    }

    public void setItemStack(ItemStack itemStack) {
        this.getEntityData().set(ITEM_STACK, itemStack);
    }


}
