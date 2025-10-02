package net.p1nero.ss.skill.sword_controller;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.SwordSoaringConfig;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.capability.SSCapabilityProvider;
import net.p1nero.ss.client.keymapping.SwordSoaringKeyMappings;
import net.p1nero.ss.client.sound.WanSoundInstance;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.entity.sword.wan.WanEntity;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.gameassets.animations.FlySwordAnimations;
import net.p1nero.ss.gameassets.animations.WanAnimations;
import net.p1nero.ss.util.ItemUtils;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WanJianGuiZongSkill extends Skill {
    private static final UUID EVENT_UUID = UUID.fromString("d2d810cc-f30f-11ed-a05b-0242ac114581");
    private static int cooldown;

    public WanJianGuiZongSkill(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        cooldown = parameters.getInt("cooldown");
    }

    public static int getMaxCooldown() {
        return cooldown;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        //蓄力禁移动
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, (event -> {
            if (event.getPlayerPatch().getPlayerMode() == PlayerPatch.PlayerMode.EPICFIGHT && SwordSoaringKeyMappings.SWORD_SKILL.isDown()) {
                Input input = event.getMovementInput();
                input.forwardImpulse = 0.0F;
                input.leftImpulse = 0.0F;
                input.down = false;
                input.up = false;
                input.left = false;
                input.right = false;
                input.jumping = false;
                input.shiftKeyDown = false;
                LocalPlayer clientPlayer = event.getPlayerPatch().getOriginal();
                clientPlayer.setSprinting(false);
                clientPlayer.sprintTriggerTime = -1;
                Minecraft mc = Minecraft.getInstance();
                ControlEngine.setKeyBind(mc.options.keySprint, false);
            }
        }));
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        PlayerPatch<?> executor = container.getExecutor();
        return (container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER.get()) <= 0 || executor.getOriginal().isCreative()) && executor.getOriginal().onGround() && SwordSoaringMod.isValidSword(executor.getOriginal().getMainHandItem());
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        ServerPlayerPatch executer = container.getServerExecutor();
        super.executeOnServer(container, args);
        container.getDataManager().setDataSync(SwordSoaringDatakeys.COOLDOWN_TIMER.get(), cooldown);
        executer.playAnimationSynchronized(WanAnimations.WAN1_PLAYER, 0.15F);
        ArrayList<ItemStack> list = ItemUtils.calculateValidBabylonItems(executer.getOriginal(), false, (SwordSoaringMod::isValidSword));
        executer.getOriginal().getCapability(SSCapabilityProvider.SS_PLAYER).ifPresent(ssPlayer -> ssPlayer.setWanSwordList(list));
        ArrayList<ItemStack> firstHalf;
        ArrayList<ItemStack> secondHalf;
        if(list.size() <= 1){
            firstHalf = secondHalf = list;
        } else {
            firstHalf = new ArrayList<>(list.subList(0, list.size() / 2));
            secondHalf = new ArrayList<>(list.subList(list.size() / 2, list.size()));
        }
        WanEntity leftOne = new WanEntity(executer.getOriginal());
        leftOne.setAnimationToPlay(WanAnimations.WAN1_L);
        leftOne.initBabylonItems(firstHalf, true);
        executer.getOriginal().level().addFreshEntity(leftOne);
        WanEntity rightOne = new WanEntity(executer.getOriginal());
        rightOne.setAnimationToPlay(WanAnimations.WAN1_R);
        rightOne.initBabylonItems(secondHalf, true);
        executer.getOriginal().level().addFreshEntity(rightOne);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        Minecraft.getInstance().getSoundManager().play(new WanSoundInstance(container.getClientExecutor()));
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if(container.getExecutor().isLogicalClient()){
            boolean isKeyDown = SwordSoaringKeyMappings.SWORD_SKILL.isDown();
            if(isKeyDown != container.getDataManager().getDataValue(SwordSoaringDatakeys.IS_PRESSING.get())){
                container.getDataManager().setDataSync(SwordSoaringDatakeys.IS_PRESSING.get(), isKeyDown);
            }
        }
        int currentCooldown = container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER.get());
        if (currentCooldown > 0 && !container.getExecutor().isLogicalClient()) {
            container.getDataManager().setDataSync(SwordSoaringDatakeys.COOLDOWN_TIMER.get(), currentCooldown - 1);
        }
        if(!container.getExecutor().isLogicalClient() && cooldown - currentCooldown <= 128){
            for(int i = 0; i < SwordSoaringConfig.SWORD_EFFECT_PER_TICK.get(); i++){
                FlySwordEntity flySwordEntity = new FlySwordEntity(container.getExecutor().getOriginal(), 200, container.getExecutor().getOriginal());
                flySwordEntity.setAnimationToPlay(FlySwordAnimations.WAN_ANIMATIONS.get(currentCooldown % FlySwordAnimations.WAN_ANIMATIONS.size()));
                flySwordEntity.setRotationLock(false);
                float randomRot = (new Random().nextFloat() * 360);
                flySwordEntity.setYRot(randomRot);
                flySwordEntity.setYBodyRot(randomRot);
                flySwordEntity.setYHeadRot(randomRot);
                container.getExecutor().getOriginal().getCapability(SSCapabilityProvider.SS_PLAYER).ifPresent(ssPlayer -> flySwordEntity.setItemStack(ssPlayer.getWanSwordList().get(currentCooldown % ssPlayer.getWanSwordList().size())));
                container.getExecutor().getOriginal().level().addFreshEntity(flySwordEntity);
            }
        }
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        return container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER.get()) > 0;
    }

    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y, float partialTick) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(0.0F, (float)gui.getSlidingProgression(), 0.0F);
        guiGraphics.blit(getSkillTexture(), (int) x, (int) y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
        guiGraphics.drawString(gui.getFont(), String.format("%.1f", (container.getDataManager().getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER.get()) / 20.0)), x + 6.0F, y + 8.0F, 16777215, true);
        poseStack.popPose();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<Object> getTooltipArgsOfScreen(List<Object> list) {
        list.add(cooldown / 20.0);
        list.add(SwordSoaringKeyMappings.SWORD_SKILL.getTranslatedKeyMessage());
        return list;
    }

}
