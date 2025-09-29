package net.p1nero.ss.skill.weapon_passive;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.p1nero.ss.capability.SSCapabilityProvider;
import net.p1nero.ss.capability.SSPlayer;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import net.p1nero.ss.gameassets.skills.VatanseverSkills;
import net.p1nero.ss.item.VatanseverItem;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkill;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class VatanseverPassive extends ArtifactSpiritPassiveSkill{
    private static final UUID EVENT_UUID = UUID.fromString("d1d114cc-f30f-11ed-a05b-0242ac114514");

    public VatanseverPassive(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        return super.canExecute(container) && container.getExecutor().getOriginal().getMainHandItem().getItem() instanceof VatanseverItem;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        Skill lastDodge = container.getExecutor().getSkill(SkillSlots.DODGE).getSkill();
        container.getExecutor().getOriginal().getCapability(SSCapabilityProvider.SS_PLAYER).ifPresent(ssPlayer -> ssPlayer.setLastDodgeSkill(lastDodge == VatanseverSkills.VATANSEVER_DODGE ? null : lastDodge));
        container.getExecutor().getSkill(SkillSlots.DODGE).setSkill(VatanseverSkills.VATANSEVER_DODGE);

        container.getDataManager().setData(SwordSoaringDatakeys.SWORD_COUNT.get(), 6);
        
        summonVatansever(container);
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID, skillExecuteEvent -> {
            if(!skillExecuteEvent.getPlayerPatch().isLogicalClient() && !(skillExecuteEvent.getPlayerPatch().getOriginal().level().getEntity(getArtifactSpiritId(container)) instanceof VatanseverEntity)){
                if(!summonVatansever(container)){
                    container.getDataManager().setDataSync(SwordSoaringDatakeys.SWORD_COUNT.get(), 0);
                }
            }
        });
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.SET_TARGET_EVENT, EVENT_UUID, setTargetEvent -> {
            if(setTargetEvent.getTarget() instanceof VatanseverEntity){
                setTargetEvent.getPlayerPatch().setAttackTarget(null);
            }
        });
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.TARGET_INDICATOR_ALERT_CHECK_EVENT, EVENT_UUID, indicatorCheckEvent -> {
            if(indicatorCheckEvent.getTarget() instanceof VatanseverEntityPatch){
                indicatorCheckEvent.setCanceled(true);
            }
        });
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID, hurtEvent -> {
            Player player = hurtEvent.getPlayerPatch().getOriginal();
            if(player.isFallFlying()){
                double power = player.getDeltaMovement().length();
                if(power > 1){
                    LevelUtil.circleSlamFracture(player, player.level(), player.position().add(0, -1, 0), power * 2);
                }
                hurtEvent.setCanceled(true);
            }
        });
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.FALL_EVENT, EVENT_UUID, fallEvent -> {
            Player player = fallEvent.getPlayerPatch().getOriginal();
            double power = player.getDeltaMovement().length();
            if(power > 1){
                LevelUtil.circleSlamFracture(player, player.level(), player.position().add(0, -1, 0), power * 2);
            }
            fallEvent.getForgeEvent().setCanceled(true);
            player.stopFallFlying();
        });
    }

    public boolean summonVatansever(SkillContainer container){
        if(!container.getExecutor().isLogicalClient() && container.getDataManager().getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get()) == 0){
            VatanseverEntity vatanseverEntity = new VatanseverEntity(container.getExecutor().getOriginal().level(), container.getExecutor().getOriginal());
            boolean success = container.getExecutor().getOriginal().level().addFreshEntity(vatanseverEntity);
            container.getExecutor().playAnimationSynchronized(VatanseverAnimations.PLAYER_INIT, 0.15F);
            container.getDataManager().setDataSync(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get(), vatanseverEntity.getId());
            container.getDataManager().setDataSync(SwordSoaringDatakeys.SWORD_COUNT.get(), 6);
            return success;
        }
        return false;
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);

        container.getExecutor().getOriginal().getCapability(SSCapabilityProvider.SS_PLAYER).ifPresent(ssPlayer -> container.getExecutor().getSkill(SkillSlots.DODGE).setSkill(ssPlayer.getLastDodgeSkill()));

        int id = getArtifactSpiritId(container);
        if(id != 0 && container.getExecutor().getOriginal().level().getEntity(id) instanceof VatanseverEntity abstractArtifactSpiritEntity){
            if(abstractArtifactSpiritEntity.isAlive()){
                abstractArtifactSpiritEntity.discard();
            }
        }
        container.getExecutor().getOriginal().getCapability(SSCapabilityProvider.SS_PLAYER).ifPresent(SSPlayer::clearVatanseverShootEntities);
        container.getDataManager().setData(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get(), 0);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.TARGET_INDICATOR_ALERT_CHECK_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.SET_TARGET_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.FALL_EVENT, EVENT_UUID);
    }

    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event){
        if(event.getEntity() instanceof ServerPlayer serverPlayer && serverPlayer.isAlive()){
            ServerPlayerPatch serverPlayerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
            SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
            if(manager.hasData(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get())){
                VatanseverEntityPatch vatanseverEntityPatch = EpicFightCapabilities.getEntityPatch(serverPlayer.level().getEntity(manager.getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get())), VatanseverEntityPatch.class);
                if(vatanseverEntityPatch != null && vatanseverEntityPatch.getEntityState().inaction()){
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

}
