package net.p1nero.ss.entity.sword;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.p1nero.ss.SwordSoaringMod;
//import net.p1nero.ss.compat.ArmourersWorkshopCompat;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class AbstractSwordEntity extends AbstractArtifactSpiritEntity implements IPatchedItemSupplier {
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(AbstractSwordEntity.class, EntityDataSerializers.ITEM_STACK);
    protected static final EntityDataAccessor<Integer> ANIMATION_TO_PLAY = SynchedEntityData.defineId(AbstractSwordEntity.class, EntityDataSerializers.INT);

    public AbstractSwordEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }
    public AbstractSwordEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, ItemStack itemStack, LivingEntity owner) {
        super(entityType, owner.level());
        this.setItemStack(itemStack);
        this.tame(owner);
        setPos(owner.position());
        //时装工坊联动，拷贝时装栏
//        SwordSoaringMod.runInArmourersWorkshopLoaded(() -> () -> ArmourersWorkshopCompat.copyArmourers(owner, this));
    }

    /**
     * 以防需要刀光的情况等
     */
    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot pSlot) {
        return getEntityData().get(ITEM_STACK);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ITEM_STACK, ItemStack.EMPTY);
        getEntityData().define(ANIMATION_TO_PLAY, -1);
    }

    public void setAnimationToPlay(AnimationManager.AnimationAccessor<? extends StaticAnimation> staticAnimation) {
        getEntityData().set(ANIMATION_TO_PLAY, staticAnimation.id());
    }

    /**
     * @return 存的动画。若没存则返回null
     */
    @Nullable
    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getAnimationToPlay() {
        int id = this.getEntityData().get(ANIMATION_TO_PLAY);
        if(id == -1){
            return null;
        }
        return AnimationManager.byId(id);
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
