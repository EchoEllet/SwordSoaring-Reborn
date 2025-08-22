package net.p1nero.ss.skill.weapon_passive;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.capability.SwordSoaringAttachments;
import net.p1nero.ss.capability.SSPlayer;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.gameassets.SwordSoaringSkills;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import net.p1nero.ss.item.VatanseverItem;
import yesman.epicfight.api.neoevent.playerpatch.SetTargetEvent;
import yesman.epicfight.api.neoevent.playerpatch.SkillCastEvent;
import yesman.epicfight.api.neoevent.playerpatch.TakeDamageEvent;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class VatanseverPassive extends ArtifactSpiritPassiveSkill{

    public VatanseverPassive(SkillBuilder<?> builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        return super.canExecute(container) && container.getExecutor().getOriginal().getMainHandItem().getItem() instanceof VatanseverItem;
    }

    @SkillEvent(side = SkillEvent.Side.SERVER)
    public void onSkillCast(SkillCastEvent skillCastEvent, SkillContainer container) {
        if(!(skillCastEvent.getPlayerPatch().getOriginal().level().getEntity(getArtifactSpiritId(container)) instanceof VatanseverEntity)){
            if(!summonVatansever(container)){
                container.getDataManager().setDataSync(SwordSoaringDatakeys.SWORD_COUNT, 0);
            }
        }
    }

    @SkillEvent(side = SkillEvent.Side.SERVER)
    public void onTargetSet(SetTargetEvent setTargetEvent, SkillContainer container) {
        if(setTargetEvent.getTarget() instanceof VatanseverEntity){
            setTargetEvent.getPlayerPatch().setAttackTarget(null);
        }
    }


    @SkillEvent(side = SkillEvent.Side.SERVER)
    public void onHurtEventPre(TakeDamageEvent.Pre event, SkillContainer container) {
        Player player = event.getPlayerPatch().getOriginal();
        if(player.isFallFlying()){
            double power = player.getDeltaMovement().length();
            if(power > 1){
                LevelUtil.circleSlamFracture(player, player.level(), player.position().add(0, -1, 0), power * 2);
            }
            event.attachValueModifier(ValueModifier.setter(0));
        }
    }

    @SkillEvent(side = SkillEvent.Side.SERVER)
    public void onFallEvent(LivingFallEvent fallEvent, SkillContainer container) {
        Player player = container.getServerExecutor().getOriginal();
        double power = player.getDeltaMovement().length();
        if(power > 1){
            LevelUtil.circleSlamFracture(player, player.level(), player.position().add(0, -1, 0), power * 2);
        }
        fallEvent.setCanceled(true);
        player.stopFallFlying();
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        Skill lastDodge = container.getExecutor().getSkill(SkillSlots.DODGE).getSkill();
        container.getExecutor().getOriginal().getData(SwordSoaringAttachments.SS_PLAYER).setLastDodgeSkill(lastDodge == SwordSoaringSkills.VATANSEVER_DODGE.get() ? null : lastDodge);
        container.getExecutor().getSkill(SkillSlots.DODGE).setSkill(SwordSoaringSkills.VATANSEVER_DODGE.get());
        container.getDataManager().setData(SwordSoaringDatakeys.SWORD_COUNT, 6);
        summonVatansever(container);
    }

    public boolean summonVatansever(SkillContainer container){
        if(!container.getExecutor().isLogicalClient() && container.getDataManager().getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID) == 0){
            VatanseverEntity vatanseverEntity = new VatanseverEntity(container.getExecutor().getOriginal().level(), container.getExecutor().getOriginal());
            boolean success = container.getExecutor().getOriginal().level().addFreshEntity(vatanseverEntity);
            container.getExecutor().playAnimationSynchronized(VatanseverAnimations.PLAYER_INIT, 0.15F);
            container.getDataManager().setDataSync(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID, vatanseverEntity.getId());
            container.getDataManager().setDataSync(SwordSoaringDatakeys.SWORD_COUNT, 6);
            return success;
        }
        return false;
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);

        SSPlayer ssPlayer = container.getExecutor().getOriginal().getData(SwordSoaringAttachments.SS_PLAYER);
        container.getExecutor().getSkill(SkillSlots.DODGE).setSkill(ssPlayer.getLastDodgeSkill());

        int id = getArtifactSpiritId(container);
        if(id != 0 && container.getExecutor().getOriginal().level().getEntity(id) instanceof VatanseverEntity abstractArtifactSpiritEntity){
            if(abstractArtifactSpiritEntity.isAlive()){
                abstractArtifactSpiritEntity.discard();
            }
        }
        ssPlayer.clearVatanseverShootEntities();
        container.getDataManager().setData(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID, 0);

    }

}
