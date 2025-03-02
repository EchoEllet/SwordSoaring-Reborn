package net.p1nero.ss.skill.sword_controller;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

public class GateOfBabylonSkill extends Skill {
    public static SkillDataManager.SkillDataKey<Integer> COOLDOWN_TIMER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    private int cooldown;
    public GateOfBabylonSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        cooldown = parameters.getInt("cooldown");
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(COOLDOWN_TIMER);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        return (executer.getSkill(this).getDataManager().getDataValue(COOLDOWN_TIMER) <= 0 || executer.getOriginal().isCreative());
    }
    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        executer.getSkill(this).getDataManager().setDataSync(COOLDOWN_TIMER, cooldown, executer.getOriginal());
        executer.playAnimationSynchronized(ScreenSwordAnimations.PLAYER_SUMMON_SCREEN_SWORD, 0.15F);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        int currentCooldown = container.getDataManager().getDataValue(COOLDOWN_TIMER);
        if (currentCooldown > 0) {
            container.getDataManager().setData(COOLDOWN_TIMER, currentCooldown - 1);
        }
        if(!container.getExecuter().isLogicalClient()){
            if(currentCooldown == this.cooldown - 1){
                BabylonEntity babylonEntity = new BabylonEntity(container.getExecuter().getOriginal());
                container.getExecuter().getOriginal().level.addFreshEntity(babylonEntity);
            }
        }
    }

    @Override
    public List<Object> getTooltipArgsOfScreen(List<Object> list) {
        list.add(cooldown / 20.0);
        return list;
    }

}
