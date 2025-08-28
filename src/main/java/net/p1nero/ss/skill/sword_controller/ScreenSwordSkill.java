package net.p1nero.ss.skill.sword_controller;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordEntity;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.util.ItemUtils;
import org.checkerframework.checker.units.qual.C;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;
import java.util.UUID;

public class ScreenSwordSkill extends KillAuraSkill {

    private static final UUID EVENT_UUID = UUID.fromString("051a9bb2-7541-11ee-b962-0242ac191981");
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

    public int getMaxProtectCount() {
        return maxProtectCount;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getOriginal().setGlowingTag(false);
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID, hurtEvent -> {
            if(hurtEvent.getPlayerPatch().getOriginal().level().getEntity(container.getDataManager().getDataValue(SwordSoaringDatakeys.SWORD_ENTITY_ID.get())) instanceof ScreenSwordEntity screenSwordEntity){
                int protectCountLeft = container.getDataManager().getDataValue(SwordSoaringDatakeys.PROTECT_COUNT.get());
                if(protectCountLeft <= 0) {
                    return;
                }
                container.getDataManager().setDataSync(SwordSoaringDatakeys.PROTECT_COUNT.get(), protectCountLeft - 1);
                if((protectCountLeft - 1) % (maxProtectCount / 6) == 0){
                    hurtEvent.getPlayerPatch().playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 0.0F, 0.0F);
                    hurtEvent.getPlayerPatch().getOriginal().heal(healCount);
                } else {
                    hurtEvent.getPlayerPatch().playSound(EpicFightSounds.CLASH.get(), 0.0F, 0.0F);
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(hurtEvent.getPlayerPatch().getOriginal().serverLevel(), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, hurtEvent.getPlayerPatch().getOriginal(), hurtEvent.getDamageSource().getDirectEntity());
                }
                //免疫硬直
                if(hurtEvent.getDamageSource() instanceof EpicFightDamageSource epicFightDamageSource){
                    epicFightDamageSource.setBaseImpact(0);
                    epicFightDamageSource.setStunType(StunType.NONE);
                }
                //免疫远程
                if(hurtEvent.getDamageSource().isIndirect()){
                    hurtEvent.setResult(AttackResult.ResultType.MISSED);
                    hurtEvent.setParried(true);
                    hurtEvent.setCanceled(true);
                } else {
                    //反伤（减伤有bug，setAmount无效，额外写太麻烦了）
                    Entity entity = hurtEvent.getDamageSource().getEntity();
                    if(entity != null){
                        //难道没有直接获取某个武器的伤害的办法吗。。
                        double total = ItemUtils.getItemAttackDamage(hurtEvent.getPlayerPatch().getOriginal(), screenSwordEntity.getItemStack(null));
                        //反击伤害不超过武器最大伤害
                        float counterattackDamage = hurtEvent.getDamage() * 0.5F > total ? (float) total : hurtEvent.getDamage() * 0.5F;
                        hurtEvent.getDamageSource().getEntity().hurt(hurtEvent.getDamageSource(), counterattackDamage);
                    }
                }
            } else {
                container.getDataManager().setDataSync(SwordSoaringDatakeys.PROTECT_COUNT.get(), 0);
                if(container.getExecutor().getOriginal().isCurrentlyGlowing()){
                    container.getExecutor().getOriginal().setGlowingTag(false);
                }
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecutor().getOriginal().setGlowingTag(false);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnServer(container, args);
        ServerPlayerPatch executer = container.getServerExecutor();
        container.getDataManager().setDataSync(SwordSoaringDatakeys.PROTECT_COUNT.get(), maxProtectCount);
    }

    @Override
    public List<Object> getTooltipArgsOfScreen(List<Object> list) {
        list.add(this.maxProtectCount);
        list.add(this.healCount);
        return super.getTooltipArgsOfScreen(list);
    }

    @Override
    public boolean shouldDraw(SkillContainer container) {
        return container.getDataManager().getDataValue(SwordSoaringDatakeys.PROTECT_COUNT.get()) > 0 || super.shouldDraw(container);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y, float partialTick) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(0.0F, (float)gui.getSlidingProgression(), 0.0F);
        guiGraphics.blit(getSkillTexture(), (int) x, (int) y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);

        int protectCount = container.getDataManager().getDataValue(SwordSoaringDatakeys.PROTECT_COUNT.get());
        int currentCooldown = container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER.get());
        int currentLifetime = this.cooldown - currentCooldown;
        if(protectCount > 0 && currentLifetime < this.lifeTime) {
            guiGraphics.drawString(gui.getFont(), container.getDataManager().getDataValue(SwordSoaringDatakeys.PROTECT_COUNT.get()).toString(), x + 6.0F, y + 8.0F, 16777215, true);
        } else {
            guiGraphics.drawString(gui.getFont(), String.format("%.1f", (container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER.get()) / 20.0)), x + 6.0F, y + 8.0F, 16777215, true);
        }
        poseStack.popPose();
    }

}
