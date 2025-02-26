package net.p1nero.ss.skill.sword_controller;

import com.p1nero.invincible.api.animation.StaticAnimationProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSword;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordPatch;
import net.p1nero.ss.gameassets.SwordSoaringSkillCategories;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class ScreenSwordSkill extends Skill {
    protected int lifeTime;
    protected StaticAnimationProvider anim;
    protected StaticAnimationProvider summonAnim;
    public static final SkillDataManager.SkillDataKey<Integer> DELAY_TIMER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);

    public ScreenSwordSkill(Builder builder) {
        super(builder);
        this.anim = builder.anim;
        this.summonAnim = builder.summonAnim;
        this.lifeTime = builder.maxLifeTime;
    }

    public StaticAnimationProvider getAnim() {
        return anim;
    }

    public static Builder createScreenSwordBuilder() {
        return new Builder().setCategory(SwordSoaringSkillCategories.SWORD_CONTROLLER).setResource(Resource.NONE);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(DELAY_TIMER);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        return SwordSoaring.isValidSword(executer.getValidItemInHand(InteractionHand.MAIN_HAND));
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        executer.playAnimationSynchronized(summonAnim.get(), 0.15F);
        executer.getSkill(this).getDataManager().setDataSync(DELAY_TIMER, 20, executer.getOriginal());
    }

    /**
     * 延迟生剑， 动画播放在{@link ScreenSwordPatch#clientTick(LivingEvent.LivingUpdateEvent)}
     */
    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        int delayTime = container.getDataManager().getDataValue(DELAY_TIMER);
        if(delayTime > 0){
            container.getDataManager().setData(DELAY_TIMER, delayTime - 1);
        }
        if(!container.getExecuter().isLogicalClient() && delayTime == 1){
            ScreenSword screenSword = new ScreenSword(container.getExecuter().getOriginal(), lifeTime);
            container.getExecuter().getOriginal().level.addFreshEntity(screenSword);
        }
    }

    public static class Builder extends Skill.Builder<ScreenSwordSkill> {
        protected StaticAnimationProvider anim = () -> ScreenSwordAnimations.SCREEN_SWORD_IDLE;
        protected StaticAnimationProvider summonAnim = () -> ScreenSwordAnimations.SCREEN_SWORD_PLAYER_SUMMON;
        protected int maxLifeTime = 200;

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

        public Builder setScreenSwordAnim(StaticAnimationProvider anim) {
            this.anim = anim;
            return this;
        }

        public Builder setSummonAnim(StaticAnimationProvider summonAnim) {
            this.summonAnim = summonAnim;
            return this;
        }

        public Builder setLifeTime(int maxLifeTime) {
            this.maxLifeTime = maxLifeTime;
            return this;
        }

    }

}
