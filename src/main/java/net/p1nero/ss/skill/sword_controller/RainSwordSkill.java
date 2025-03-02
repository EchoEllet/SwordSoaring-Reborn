package net.p1nero.ss.skill.sword_controller;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;
import java.util.UUID;

public class RainSwordSkill extends Skill {
    private static final UUID EVENT_UUID = UUID.fromString("051a9bb2-1145-14ee-b962-0242ac191981");
    public static SkillDataManager.SkillDataKey<Integer> DELAY_TIMER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    public static SkillDataManager.SkillDataKey<Integer> COOLDOWN_TIMER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    private int lifeTime, minCount, maxCount, interval, cooldown;

    public RainSwordSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        interval = parameters.getInt("interval");
        lifeTime = parameters.getInt("life_time");
        minCount = parameters.getInt("min_count");
        maxCount = parameters.getInt("max_count");
        cooldown = parameters.getInt("cooldown");
        if (cooldown < lifeTime) {
            throw new IllegalArgumentException("cooldown can not be less than lifetime!");
        }
        if (minCount > maxCount) {
            throw new IllegalArgumentException("max count can not be less than min count!");
        }
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(DELAY_TIMER);
        container.getDataManager().registerData(COOLDOWN_TIMER);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID, basicAttackEvent -> {
            LivingEntity target = basicAttackEvent.getPlayerPatch().getTarget();
            int currentLifeTime = cooldown - container.getDataManager().getDataValue(COOLDOWN_TIMER);
            if (currentLifeTime < this.lifeTime && target != null) {
                int count = basicAttackEvent.getPlayerPatch().getOriginal().getRandom().nextInt(minCount, maxCount);
                container.getDataManager().setDataSync(DELAY_TIMER, count * interval, basicAttackEvent.getPlayerPatch().getOriginal());
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        return SwordSoaring.isValidSword(executer.getValidItemInHand(InteractionHand.MAIN_HAND)) && (executer.getSkill(this).getDataManager().getDataValue(COOLDOWN_TIMER) <= 0 || executer.getOriginal().isCreative());
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        executer.getSkill(this).getDataManager().setDataSync(COOLDOWN_TIMER, cooldown, executer.getOriginal());
        executer.playAnimationSynchronized(ScreenSwordAnimations.PLAYER_SUMMON_SWORD, 0.15F);
        executer.playSound(SoundEvents.EVOKER_PREPARE_SUMMON, 0.0F, 0.0F);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        int cooldown = container.getDataManager().getDataValue(COOLDOWN_TIMER);
        if (cooldown > 0) {
            container.getDataManager().setData(COOLDOWN_TIMER, cooldown - 1);
        }
        int delayTimer = container.getDataManager().getDataValue(DELAY_TIMER);
        if (delayTimer > 0) {
            if (!container.getExecuter().isLogicalClient() && delayTimer % interval == 0) {
                LivingEntity target = container.getExecuter().getTarget();
                if(target != null){
                    FlySwordEntity flySwordEntity = new FlySwordEntity(container.getExecuter().getOriginal(), -114, target);
                    flySwordEntity.setRotationLock(false);
                    float yRot = (delayTimer * 1.0F / interval) / maxCount * 360.0F;
                    flySwordEntity.setYRot(yRot);
                    flySwordEntity.setYBodyRot(yRot);
                    flySwordEntity.setYHeadRot(yRot);
                    target.level.addFreshEntity(flySwordEntity);
                }
            }
            container.getDataManager().setData(DELAY_TIMER, delayTimer - 1);
        }
    }

    @Override
    public List<Object> getTooltipArgsOfScreen(List<Object> list) {
        list.add(minCount);
        list.add(maxCount);
        list.add(lifeTime / 20.0);
        list.add(cooldown / 20.0);
        return list;
    }

    @Override
    public boolean shouldDraw(SkillContainer container) {
        return container.getDataManager().getDataValue(COOLDOWN_TIMER) > 0;
    }

    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, PoseStack poseStack, float x, float y) {
        poseStack.pushPose();
        poseStack.translate(0.0, (float) gui.getSlidingProgression(), 0.0);
        RenderSystem.setShaderTexture(0, getSkillTexture());
        GuiComponent.blit(poseStack, (int) x, (int) y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
        int currentCooldown = container.getDataManager().getDataValue(COOLDOWN_TIMER);
        int currentLifetime = this.cooldown - currentCooldown;
        if (currentLifetime > this.lifeTime) {
            gui.font.drawShadow(poseStack, String.format("%.1f", currentCooldown / 20.0), x + 6.0F, y + 8.0F, 16777215);
        } else {
            gui.font.drawShadow(poseStack, String.format("%.1f", (this.lifeTime - currentLifetime) / 20.0), x + 6.0F, y + 8.0F, 16777215);
        }
    }
}
