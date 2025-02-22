package net.p1nero.ss.skill.weapon_innate;

import com.google.common.collect.Lists;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntityPatch;
import net.p1nero.ss.gameassets.animations.VatanseverStormAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

public class VatanseverWeaponInnateSkill extends ComboBasicAttack {

    public static SkillDataManager.SkillDataKey<Integer> STORM_TIMER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    private Vec3 pos = Vec3.ZERO;
    public VatanseverWeaponInnateSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(STORM_TIMER);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        SkillDataManager manager = container.getDataManager();
        if(!container.getExecuter().isLogicalClient()){
            ServerPlayerPatch serverPlayerPatch = ((ServerPlayerPatch) container.getExecuter());
            ServerPlayer serverPlayer =serverPlayerPatch.getOriginal();
            int current = manager.getDataValue(STORM_TIMER);
            if(current > 0){
                if(current == 66){
                    pos = new Vec3(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
                }
                if(current % 10 == 0){
                    createStorm(serverPlayer, VatanseverStormAnimations.VATANSEVER_STORM_RAIN);
                }
                manager.setDataSync(STORM_TIMER, current - 1, serverPlayer);
                createStorm(serverPlayer, VatanseverStormAnimations.VATANSEVER_STORM_1);
                createStorm(serverPlayer, VatanseverStormAnimations.VATANSEVER_STORM_2);
                createStorm(serverPlayer, VatanseverStormAnimations.VATANSEVER_STORM_3);
                createStorm(serverPlayer, VatanseverStormAnimations.VATANSEVER_STORM_4);
            }
        }
    }

    public void createStorm(ServerPlayer serverPlayer, StaticAnimation staticAnimation){
        VatanseverStormEntity stormEntity = new VatanseverStormEntity(serverPlayer.level, serverPlayer, pos);
        serverPlayer.level.addFreshEntity(stormEntity);
        EpicFightCapabilities.getEntityPatch(stormEntity, VatanseverStormEntityPatch.class).playAnimationSynchronized(staticAnimation, 0.15F);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerpatch) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent("skill.sword_soaring.vatansever.tooltip"));
        return list;
    }
}
