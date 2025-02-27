package net.p1nero.ss.skill.sword_controller;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordEntity;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class ScreenSwordSkill extends KillAuraSkill {

    private static final UUID EVENT_UUID = UUID.fromString("051a9bb2-7541-11ee-b962-0242ac191981");
    public static final SkillDataManager.SkillDataKey<Integer> PROTECT_COUNT = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    private int maxProtectCount;
    private float healCount;

    public ScreenSwordSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        maxProtectCount = parameters.getInt("protect_count");
        healCount = parameters.getFloat("heal_count");
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(PROTECT_COUNT);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, hurtEvent -> {
            if(hurtEvent.getPlayerPatch().getOriginal().level.getEntity(container.getDataManager().getDataValue(SWORD_ENTITY_ID)) instanceof ScreenSwordEntity){
                int protectCountLeft = container.getDataManager().getDataValue(PROTECT_COUNT);
                if(protectCountLeft <= 0) {
                    return;
                }
                container.getDataManager().setData(PROTECT_COUNT, protectCountLeft - 1);
                if((protectCountLeft - 1) % 6 == 0){
                    hurtEvent.getPlayerPatch().playSound(EpicFightSounds.NEUTRALIZE_MOBS, 0.0F, 0.0F);
                    hurtEvent.getPlayerPatch().getOriginal().heal(healCount);
                } else {
                    hurtEvent.getPlayerPatch().playSound(EpicFightSounds.CLASH, 0.0F, 0.0F);
                }
                //免疫硬直
                if(hurtEvent.getDamageSource() instanceof EpicFightDamageSource epicFightDamageSource){
                    epicFightDamageSource.setImpact(0);
                    epicFightDamageSource.setStunType(StunType.NONE);
                }
                //免疫投掷物
                if(hurtEvent.getDamageSource().isProjectile()){
                    hurtEvent.setAmount(0);
                    hurtEvent.setResult(AttackResult.ResultType.MISSED);
                    hurtEvent.setParried(true);
                    hurtEvent.setCanceled(true);
                } else {
                    //反伤
                    Entity entity = hurtEvent.getDamageSource().getEntity();
                    if(entity != null){
                        hurtEvent.getDamageSource().getEntity().hurt(hurtEvent.getDamageSource(), hurtEvent.getAmount() * 0.25F);
                    }
                }
            } else {
                container.getDataManager().setData(PROTECT_COUNT, 0);
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        executer.getSkill(this).getDataManager().setDataSync(PROTECT_COUNT, maxProtectCount, executer.getOriginal());
    }
}
