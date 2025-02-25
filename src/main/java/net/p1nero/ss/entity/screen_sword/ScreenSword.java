package net.p1nero.ss.entity.screen_sword;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;

public class ScreenSword extends AbstractArtifactSpiritEntity {

    public ScreenSword(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Item getOriginalItem() {
        return null;
    }

    @Override
    protected boolean shouldRemoveWhenOwnerLost() {
        return false;
    }
}
