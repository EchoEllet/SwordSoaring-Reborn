package net.p1nero.ss.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.item.WeaponItem;

import java.util.List;

public class VatanseverItem extends WeaponItem {

    public VatanseverItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            if (pPlayer.onGround() && !pPlayer.isFallFlying() && !pPlayer.isInWater() && !pPlayer.hasEffect(MobEffects.LEVITATION)) {
                ServerPlayerPatch serverPlayerPatch = EpicFightCapabilities.getEntityPatch(pPlayer, ServerPlayerPatch.class);
                if(serverPlayerPatch.getPlayerMode() == PlayerPatch.PlayerMode.EPICFIGHT && !serverPlayerPatch.getEntityState().inaction()){
                    SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                    if(manager.hasData(SwordSoaringDatakeys.SWORD_COUNT) && manager.getDataValue(SwordSoaringDatakeys.SWORD_COUNT) >= 4){
                        serverPlayerPatch.playAnimationSynchronized(VatanseverAnimations.PLAYER_FLY_BEGIN, 0.15F);
                    }
                }
            }
        } else if(!pPlayer.onGround() && pPlayer.isFallFlying()){
            Vec3 view = pPlayer.getViewVector(1.0F);
            pPlayer.push(view.x, view.y, view.z);
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag tooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.sword_soaring.vatansever.description1"));
        pTooltipComponents.add(Component.translatable("item.sword_soaring.vatansever.description2"));
    }
}
