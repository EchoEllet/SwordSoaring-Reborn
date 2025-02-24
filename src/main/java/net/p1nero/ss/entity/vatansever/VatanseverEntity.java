package net.p1nero.ss.entity.vatansever;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.item.SwordSoaringItems;

public class VatanseverEntity extends AbstractArtifactSpiritEntity {

    public VatanseverEntity(EntityType<? extends VatanseverEntity> entityType, Level level) {
        super(entityType, level);
    }

    public VatanseverEntity(Level level, Player owner) {
        super(SwordSoaringEntities.VATANSEVER.get(), level);
        tame(owner);
        setItemSlot(EquipmentSlot.MAINHAND, owner.getItemBySlot(EquipmentSlot.MAINHAND).copy());
    }

    @Override
    protected void moveToOwner(LivingEntity owner) {
        if(level.isClientSide && owner.isFallFlying()){
            //仅客户端同步位置，防止双端不同步导致的乱转
            setPos(owner.position());
        } else {
            setYRot(owner.yBodyRot);
            setYBodyRot(owner.yBodyRot);
            setYHeadRot(owner.yBodyRot);
            setPos(owner.position());
        }
    }

    @Override
    protected Item getOriginalItem() {
        return SwordSoaringItems.VATANSEVER.get();
    }
}

