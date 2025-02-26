package net.p1nero.ss.entity.sword.screen_sword;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;

public class ScreenSword extends AbstractSwordEntity {

    private int maxTickCount = - 1;
    public ScreenSword(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ScreenSword(Player owner, int maxTickCount){
        super(SwordSoaringEntities.SCREEN_SWORD.get(), owner.getMainHandItem().copy(), owner);
        this.maxTickCount = maxTickCount;
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide && tickCount == maxTickCount){
            this.discard();
        }
    }

    @Override
    protected void moveToOwner(LivingEntity owner) {
        setYRot(0);
        setYBodyRot(0);
        setYHeadRot(0);
        setPos(owner.position());
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
