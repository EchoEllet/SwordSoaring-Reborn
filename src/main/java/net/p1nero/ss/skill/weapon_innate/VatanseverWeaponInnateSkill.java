package net.p1nero.ss.skill.weapon_innate;

import com.google.common.collect.Lists;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntityPatch;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import net.p1nero.ss.gameassets.animations.VatanseverStormAnimations;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

public class VatanseverWeaponInnateSkill extends ComboBasicAttack {

    public VatanseverWeaponInnateSkill(Builder builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        SkillDataManager manager = executer.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
        if (manager.hasData(VatanseverPassive.ARTIFACT_SPIRIT_ENTITY_ID)) {
            int id = manager.getDataValue(VatanseverPassive.ARTIFACT_SPIRIT_ENTITY_ID);
            VatanseverEntityPatch vatanseverEntityPatch = EpicFightCapabilities.getEntityPatch(executer.getOriginal().level.getEntity(id), VatanseverEntityPatch.class);
            return super.canExecute(executer) && (vatanseverEntityPatch == null || !vatanseverEntityPatch.getEntityState().movementLocked());
        }
        return super.canExecute(executer);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerpatch) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent("skill.sword_soaring.vatansever.tooltip"));
        return list;
    }
}
