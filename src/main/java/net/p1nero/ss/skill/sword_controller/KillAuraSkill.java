package net.p1nero.ss.skill.sword_controller;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.p1nero.invincible.api.animation.StaticAnimationProvider;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordEntity;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordPatch;
import net.p1nero.ss.gameassets.SwordSoaringSkillCategories;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class KillAuraSkill extends Skill {
    protected int lifeTime, cooldown;
    protected StaticAnimationProvider playerSummonAnim;
    protected StaticAnimationProvider swordSummonAnim;
    public static final SkillDataManager.SkillDataKey<Integer> COOL_DOWN_TIMER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    public static final SkillDataManager.SkillDataKey<Integer> SWORD_ENTITY_ID = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);

    public KillAuraSkill(Builder builder) {
        super(builder);
        this.playerSummonAnim = builder.playerSummonAnim;
        this.swordSummonAnim = builder.swordSummonAnim;
    }

    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        lifeTime = parameters.getInt("life_time");
        cooldown = parameters.getInt("cooldown");
    }

    public int getMaxCooldown() {
        return cooldown;
    }

    public StaticAnimationProvider getSwordSummonAnim() {
        return swordSummonAnim;
    }

    public static Builder createKillAuraBuilder() {
        return new Builder().setCategory(SwordSoaringSkillCategories.SWORD_CONTROLLER).setResource(Resource.NONE);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(COOL_DOWN_TIMER);
        container.getDataManager().registerData(SWORD_ENTITY_ID);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        return SwordSoaring.isValidSword(executer.getValidItemInHand(InteractionHand.MAIN_HAND)) && (executer.getSkill(this).getDataManager().getDataValue(COOL_DOWN_TIMER) <= 0 || executer.getOriginal().isCreative());
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        executer.playAnimationSynchronized(playerSummonAnim.get(), 0.15F);
        ScreenSwordEntity screenSwordEntity = new ScreenSwordEntity(executer.getOriginal(), lifeTime);
        executer.getOriginal().level.addFreshEntity(screenSwordEntity);
        executer.getSkill(this).getDataManager().setDataSync(SWORD_ENTITY_ID, screenSwordEntity.getId(), executer.getOriginal());
        executer.getSkill(this).getDataManager().setDataSync(COOL_DOWN_TIMER, cooldown, executer.getOriginal());
    }

    /**
     * 延迟生剑， 动画播放在{@link ScreenSwordPatch#clientTick(LivingEvent.LivingUpdateEvent)}
     */
    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        int cooldown = container.getDataManager().getDataValue(COOL_DOWN_TIMER);
        if(cooldown > 0){
            container.getDataManager().setData(COOL_DOWN_TIMER, cooldown - 1);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        return container.getDataManager().getDataValue(COOL_DOWN_TIMER) > 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawOnGui(BattleModeGui gui, SkillContainer container, PoseStack poseStack, float x, float y) {
        poseStack.pushPose();
        poseStack.translate(0.0, (float) gui.getSlidingProgression(), 0.0);
        RenderSystem.setShaderTexture(0, getSkillTexture());
        GuiComponent.blit(poseStack, (int) x, (int) y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
        gui.font.drawShadow(poseStack, String.format("%.1f", (container.getDataManager().getDataValue(COOL_DOWN_TIMER) / 20.0)), x + 6.0F, y + 8.0F, 16777215);
    }

    public static class Builder extends Skill.Builder<KillAuraSkill> {
        protected StaticAnimationProvider playerSummonAnim, swordSummonAnim;
        public Builder() {
        }

        public Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public Builder setActivateType(ActivateType activateType) {
            this.activateType = activateType;
            return this;
        }

        public Builder setResource(Resource resource) {
            this.resource = resource;
            return this;
        }

        public Builder setCreativeTab(CreativeModeTab tab) {
            this.tab = tab;
            return this;
        }

        public Builder setPlayerSummonAnim(StaticAnimationProvider summonAnim) {
            this.playerSummonAnim = summonAnim;
            return this;
        }

        public Builder setSwordSummonAnim(StaticAnimationProvider summonAnim) {
            this.swordSummonAnim = summonAnim;
            return this;
        }

    }

}
