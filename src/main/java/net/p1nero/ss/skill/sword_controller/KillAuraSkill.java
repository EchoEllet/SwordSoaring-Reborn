package net.p1nero.ss.skill.sword_controller;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordEntity;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordPatch;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.gameassets.SwordSoaringSkillCategories;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;
import java.util.function.Function;

public class KillAuraSkill extends Skill {
    protected int lifeTime, cooldown;
    protected AnimationManager.AnimationAccessor<? extends StaticAnimation> playerSummonAnim;
    protected AnimationManager.AnimationAccessor<? extends StaticAnimation> swordSummonAnim;
    
    public KillAuraSkill(Builder builder) {
        super(builder);
        
        this.playerSummonAnim = builder.playerSummonAnim;
        this.swordSummonAnim = builder.swordSummonAnim;
    }

    @Override
    public void loadDatapackParameters(CompoundTag parameters) {
        super.loadDatapackParameters(parameters);
        lifeTime = parameters.getInt("life_time");
        cooldown = parameters.getInt("cooldown");
        cooldown += lifeTime;
    }

    public int getMaxCooldown() {
        return cooldown;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getSwordSummonAnim() {
        return swordSummonAnim;
    }

    public static Builder createKillAuraBuilder(Function<Builder, ? extends KillAuraSkill> constructor) {
        return new Builder(constructor).setCategory(SwordSoaringSkillCategories.SWORD_CONTROLLER).setResource(Resource.NONE);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        int id = getSwordEntityId(container);
        if(id != 0 && container.getExecutor().getOriginal().level().getEntity(id) instanceof ScreenSwordEntity abstractArtifactSpiritEntity){
            if(abstractArtifactSpiritEntity.isAlive()){
                abstractArtifactSpiritEntity.discard();
            }
        }
    }

    public int getSwordEntityId(SkillContainer container){
        if(container.getDataManager().hasData(SwordSoaringDatakeys.SWORD_ENTITY_ID)){
            return container.getDataManager().getDataValue(SwordSoaringDatakeys.SWORD_ENTITY_ID);
        }
        return 0;
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        PlayerPatch<?> executor = container.getExecutor();
        return executor.getOriginal().onGround() && SwordSoaringMod.isValidSword(executor.getValidItemInHand(InteractionHand.MAIN_HAND)) && container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER) <= 0 || executor.getOriginal().isCreative();
    }

    @Override
    public void executeOnServer(SkillContainer container, CompoundTag args) {
        super.executeOnServer(container, args);
        ServerPlayerPatch executor = container.getServerExecutor();
        executor.playAnimationSynchronized(playerSummonAnim, 0.15F);
        container.getDataManager().setDataSync(SwordSoaringDatakeys.COOLDOWN_TIMER, cooldown);
    }

    /**
     * 延迟生剑， 动画播放在{@link ScreenSwordPatch#preTickClient(EntityTickEvent.Pre)}
     */
    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        int cooldown = container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER);
        if(cooldown > 0){
            container.getDataManager().setData(SwordSoaringDatakeys.COOLDOWN_TIMER, cooldown - 1);
        }
        if(cooldown == this.cooldown - (int) (playerSummonAnim.get().getTotalTime() * 10) && !container.getExecutor().isLogicalClient()){
            ScreenSwordEntity screenSwordEntity = new ScreenSwordEntity(container.getExecutor().getOriginal(), lifeTime);
            container.getExecutor().getOriginal().level().addFreshEntity(screenSwordEntity);
            container.getDataManager().setDataSync(SwordSoaringDatakeys.SWORD_ENTITY_ID, screenSwordEntity.getId());
        }
    }

    @Override
    public List<Object> getTooltipArgsOfScreen(List<Object> list) {
        list.add(this.lifeTime / 20.0);
        list.add(this.cooldown / 20.0);
        return list;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        return container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER) > 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y, float partialTick) {
        guiGraphics.blit(getSkillTexture(), (int) x, (int) y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
        int currentCooldown = container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER);
        int currentLifetime = this.cooldown - currentCooldown;
        if (currentLifetime > this.lifeTime) {
            guiGraphics.drawString(gui.getFont(), String.format("%.1f", currentCooldown / 20.0), x + 6.0F, y + 8.0F, 16777215, true);
        } else {
            guiGraphics.drawString(gui.getFont(), String.format("%.1f", (this.lifeTime - currentLifetime) / 20.0), x + 6.0F, y + 8.0F, 16777215, true);
        }
    }

    public static class Builder extends SkillBuilder<Builder> {
        protected AnimationManager.AnimationAccessor<? extends StaticAnimation> playerSummonAnim, swordSummonAnim;
        public Builder(Function<Builder, ? extends KillAuraSkill> constructor) {
            super(constructor);
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

        public Builder setPlayerSummonAnim(AnimationManager.AnimationAccessor<? extends StaticAnimation> summonAnim) {
            this.playerSummonAnim = summonAnim;
            return this;
        }

        public Builder setSwordSummonAnim(AnimationManager.AnimationAccessor<? extends StaticAnimation> summonAnim) {
            this.swordSummonAnim = summonAnim;
            return this;
        }

    }

}
