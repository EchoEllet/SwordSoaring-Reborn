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
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class FlySwordEntity extends AbstractSwordEntity {
    private int maxTickCount = -1;

    private LivingEntity target;
    public FlySwordEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FlySwordEntity(LivingEntity owner, int maxTickCount, LivingEntity target){
        super(SwordSoaringEntities.FLY_SWORD.get(), owner.getMainHandItem().copy(), owner);
        this.maxTickCount = maxTickCount;
        this.target = target;
        if(target != null && target.isAlive()){
            setPos(target.position());
        }
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
    public LivingEntity getTarget() {
        return target;
    }

    /**
     * 最好只用一次
     */
    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    protected void moveToOwner(LivingEntity owner) {
        setYRot(0);
        setYBodyRot(0);
        setYHeadRot(0);
        if(!level.isClientSide){
            if(target != null && target.isAlive()){
                this.setPos(target.position());
            }
        }
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
