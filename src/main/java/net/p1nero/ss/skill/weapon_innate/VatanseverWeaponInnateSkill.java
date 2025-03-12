package net.p1nero.ss.skill.weapon_innate;

import com.google.common.collect.Lists;
import com.p1nero.invincible.client.keymappings.InvincibleKeyMappings;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.p1nero.ss.client.keymapping.SwordSoaringKeyMappings;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

public class VatanseverWeaponInnateSkill extends ComboBasicAttack {

    public VatanseverWeaponInnateSkill(Builder builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        PlayerPatch<?> executor = container.getExecutor();
        SkillDataManager manager = executor.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
        if (manager.hasData(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get())) {
            int id = manager.getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get());
            VatanseverEntityPatch vatanseverEntityPatch = EpicFightCapabilities.getEntityPatch(executor.getOriginal().level().getEntity(id), VatanseverEntityPatch.class);
            return super.canExecute(container) && (vatanseverEntityPatch == null || !vatanseverEntityPatch.getEntityState().movementLocked());
        }
        return super.canExecute(container);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerpatch) {
        List<Component> list = Lists.newArrayList();
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip1").append(InvincibleKeyMappings.getTranslatableKey1()).append(" ").append(InvincibleKeyMappings.getTranslatableKey1()).append(" ").append(InvincibleKeyMappings.getTranslatableKey1()).append(" ").append(InvincibleKeyMappings.getTranslatableKey2()));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip2"));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip3").append(InvincibleKeyMappings.getTranslatableKey1()).append(" ").append(InvincibleKeyMappings.getTranslatableKey1()).append(" ").append(InvincibleKeyMappings.getTranslatableKey2()));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip4"));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip5").append(InvincibleKeyMappings.getTranslatableKey1()).append(" ").append(InvincibleKeyMappings.getTranslatableKey1()).append(" ").append(InvincibleKeyMappings.getTranslatableKey1()).append(" ").append(InvincibleKeyMappings.getTranslatableKey1()));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip6"));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip7").append(InvincibleKeyMappings.getTranslatableKey3()).append(" ").append(InvincibleKeyMappings.getTranslatableKey4()));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip8"));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip9").append(SwordSoaringKeyMappings.SWORD_SKILL.getTranslatedKeyMessage()));
        list.add(Component.translatable("skill.sword_soaring.vatansever.tooltip10"));
        return list;
    }
}
