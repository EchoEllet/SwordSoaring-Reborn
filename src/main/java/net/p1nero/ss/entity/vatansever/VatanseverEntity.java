package net.p1nero.ss.entity.vatansever;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
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
    }

    @Override
    protected Item getOriginalItem() {
        return SwordSoaringItems.VATANSEVER.get();
    }

}