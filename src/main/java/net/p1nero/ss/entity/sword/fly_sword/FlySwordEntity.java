package net.p1nero.ss.entity.sword.fly_sword;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.skill.sword_controller.ScreenSwordSkill;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class FlySwordEntity extends AbstractSwordEntity {
    private int maxTickCount = -1;
    public FlySwordEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FlySwordEntity(Player owner, int maxTickCount){
        super(SwordSoaringEntities.FLY_SWORD.get(), owner.getMainHandItem().copy(), owner);
        this.maxTickCount = maxTickCount;
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide){
            ServerPlayerPatch serverPlayerPatch = EpicFightCapabilities.getEntityPatch(getOwner(), ServerPlayerPatch.class);
            if(serverPlayerPatch != null){

            }
        }
    }

    @Override
    protected void moveToOwner(LivingEntity owner) {
        setYRot(0);
        setYBodyRot(0);
        setYHeadRot(0);
        //在Patch里同步位置
    }

    @Override
    protected Item getOriginalItem() {
        return null;
    }

}
