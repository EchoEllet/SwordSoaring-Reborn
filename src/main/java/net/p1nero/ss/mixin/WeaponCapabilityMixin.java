package net.p1nero.ss.mixin;

import net.minecraft.world.InteractionHand;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.gameassets.animations.FlyAnimations;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkillElytra;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.Map;

/**
 * 修复起飞时播放默认飞行动画
 */
@Mixin(value = WeaponCapability.class, remap = false)
public class WeaponCapabilityMixin {
    @Inject(method = "getLivingMotionModifier", at = @At("RETURN"))
    private void sword_soaring$getLivingMotionModifier(LivingEntityPatch<?> entityPatch, InteractionHand hand, CallbackInfoReturnable<Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> cir) {
        if (entityPatch instanceof PlayerPatch<?> playerPatch && playerPatch.getSkill(SwordSoaringSkillSlots.SWORD_SOARING).getSkill() instanceof SwordSoaringSkillElytra skillElytra && SwordSoaringMod.isValidSword(playerPatch.getOriginal().getMainHandItem())) {
            cir.getReturnValue().put(LivingMotions.FLY, skillElytra.getFlyingAnim() == null ? FlyAnimations.APPRENTICE_FLYING : skillElytra.getFlyingAnim());
        }
    }
}
