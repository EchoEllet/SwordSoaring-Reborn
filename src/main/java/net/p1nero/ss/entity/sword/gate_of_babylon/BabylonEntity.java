package net.p1nero.ss.entity.sword.gate_of_babylon;

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

public class BabylonEntity extends AbstractSwordEntity {
    public BabylonEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BabylonEntity(Player owner){
        super(SwordSoaringEntities.BABYLON.get(), owner.getMainHandItem().copy(), owner);
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

}
