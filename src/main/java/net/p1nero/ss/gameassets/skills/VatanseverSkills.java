package net.p1nero.ss.gameassets.skills;

import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.api.skill.ComboNode;
import com.p1nero.invincible.conditions.CooldownCondition;
import com.p1nero.invincible.conditions.CustomCondition;
import com.p1nero.invincible.conditions.StackCondition;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.minecraft.world.entity.Entity;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.gameassets.SwordSoaringComboTypes;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import net.p1nero.ss.item.SwordSoaringItems;
import net.p1nero.ss.skill.weapon_innate.VatanseverWeaponInnateSkill;
import net.p1nero.ss.skill.weapon_passive.VatanseverDodgeSkill;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.data.conditions.entity.TargetInDistance;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.skill.dodge.StepSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class VatanseverSkills {
    public static Skill VATANSEVER_INNATE;
    public static Skill VATANSEVER_PASSIVE;
    public static Skill VATANSEVER_DODGE;

    public static void buildVatanseverSkills(SkillBuildEvent.ModRegistryWorker registryWorker) {
        ComboNode root = ComboNode.create();
        ComboNode a = ComboNode.createNode(VatanseverAnimations.PLAYER_AUTO1).addCondition(checkSwordCount(1, 6)).setCanBeInterrupt(false);
        ComboNode aa_1 = ComboNode.createNode(VatanseverAnimations.PLAYER_AUTO2_1).addCondition(checkSwordCount(2, 6)).setCanBeInterrupt(false);
        ComboNode aa_2 = ComboNode.createNode(VatanseverAnimations.PLAYER_AUTO2_2).addCondition(checkSwordCount(2, 6)).setCanBeInterrupt(false);
        ComboNode aaa = ComboNode.createNode(VatanseverAnimations.PLAYER_AUTO3).addCondition(checkSwordCount(1, 6)).setCanBeInterrupt(false);
        ComboNode aab = ComboNode.createNode(VatanseverAnimations.PLAYER_AUTO3_B).addCondition(checkSwordCount(5, 6)).setCanBeInterrupt(false);
        ComboNode aaaa = ComboNode.createNode(VatanseverAnimations.PLAYER_AUTO4).addCondition(checkSwordCount(6)).setCanBeInterrupt(false);
        ComboNode aaab = ComboNode.createNode(VatanseverAnimations.PLAYER_AUTO4_B).addCondition(checkSwordCount(6)).setCanBeInterrupt(false);
        ComboNode storm = ComboNode.createNode(VatanseverAnimations.PLAYER_STORM_START).addCondition(checkSwordCount(6))
                .setCooldown(1200)
                .addCondition(new CooldownCondition(false)).setCanBeInterrupt(false);
        ComboNode execute = ComboNode.createNode(VatanseverAnimations.PLAYER_EXECUTE)
                .setConvertTime(0.15F)
                .addCondition(new StackCondition(7, 7))
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        return entityPatch.getTarget() != null;
                    }
                })
                .addCondition(new TargetInDistance(0, 5))
                .addTimeEvent(new TimeStampedEvent(0.0F, entityPatch -> {
            if(entityPatch instanceof ServerPlayerPatch serverPlayerPatch){
                SkillContainer container = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                container.getSkill().setStackSynchronize(container, 0);
                container.getSkill().setConsumptionSynchronize(container, 0);
                serverPlayerPatch.getTarget().moveTo(serverPlayerPatch.getOriginal().position());
                serverPlayerPatch.getTarget().setYRot(serverPlayerPatch.getYRot());
                serverPlayerPatch.getTarget().setYBodyRot(serverPlayerPatch.getYRot());
                serverPlayerPatch.getTarget().setYHeadRot(serverPlayerPatch.getYRot());
                LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(serverPlayerPatch.getTarget(), LivingEntityPatch.class);
                if(livingEntityPatch.getArmature() instanceof HumanoidArmature){
                    livingEntityPatch.playAnimationSynchronized(VatanseverAnimations.PLAYER_BE_EXECUTED, 0.10F);
                }
            }
        }));
        ComboNode shootL3 = ComboNode.createNode(VatanseverAnimations.PLAYER_SHOOT_L3).addCondition(checkSwordCount(6)).addCondition(checkIsNotInaction()).setPriority(6).setCanBeInterrupt(false);
        ComboNode shootR3 = ComboNode.createNode(VatanseverAnimations.PLAYER_SHOOT_R3).addCondition(checkSwordCount(5)).addCondition(checkIsNotInaction()).setPriority(5).setCanBeInterrupt(false);
        ComboNode shootL2 = ComboNode.createNode(VatanseverAnimations.PLAYER_SHOOT_L2).addCondition(checkSwordCount(4)).addCondition(checkIsNotInaction()).setPriority(4).setCanBeInterrupt(false);
        ComboNode shootR2 = ComboNode.createNode(VatanseverAnimations.PLAYER_SHOOT_R2).addCondition(checkSwordCount(3)).addCondition(checkIsNotInaction()).setPriority(3).setCanBeInterrupt(false);
        ComboNode shootL1 = ComboNode.createNode(VatanseverAnimations.PLAYER_SHOOT_L1).addCondition(checkSwordCount(2)).addCondition(checkIsNotInaction()).setPriority(2).setCanBeInterrupt(false);
        ComboNode shootR1 = ComboNode.createNode(VatanseverAnimations.PLAYER_SHOOT_R1).addCondition(checkSwordCount(1)).addCondition(checkIsNotInaction()).setPriority(1).setCanBeInterrupt(false);
        ComboNode shoot = ComboNode.create().addConditionAnimation(shootL1)
                .addConditionAnimation(shootL2)
                .addConditionAnimation(shootL3)
                .addConditionAnimation(shootR1)
                .addConditionAnimation(shootR2)
                .addConditionAnimation(shootR3);
        root.key1(a);
        a.key1(aa_1);
        aa_1.key1(aa_2);
        aa_2.key1(aaa);
        aa_2.key2(aab);
        aaa.key1(aaaa);
        aaa.key2(aaab);
        aaaa.key1(a);
        a.key3(shoot);
        aa_1.key3(shoot);
        aa_2.key3(shoot);
        aaa.key3(shoot);
        aaaa.key3(shoot);
        shoot.key1(a);
        shoot.key3(shoot);
        root.key3(storm);
        root.key1_4(execute);
        VATANSEVER_INNATE = registryWorker.build("vatansever_innate", VatanseverWeaponInnateSkill::new, ComboBasicAttack.createComboBasicAttack().setCombo(root).setShouldDrawGui(true));
        VATANSEVER_PASSIVE = registryWorker.build("vatansever_passive", VatanseverPassive::new, Skill.createBuilder().setCategory(SkillCategories.WEAPON_PASSIVE).setResource(Skill.Resource.NONE));

        VATANSEVER_DODGE = registryWorker.build("vatansever_dodge", VatanseverDodgeSkill::new, VatanseverDodgeSkill.createDodgeBuilder()
                .setAnimations(
                        VatanseverAnimations.PLAYER_DODGE_F,
                        VatanseverAnimations.PLAYER_DODGE_B,
                        VatanseverAnimations.PLAYER_DODGE_L,
                        VatanseverAnimations.PLAYER_DODGE_R
                ).setCreativeTab(SwordSoaringItems.DEFAULT_TAB.get()));
    }

    public static CustomCondition checkSwordCount(int swordCount) {
        return checkSwordCount(swordCount, swordCount);
    }

    public static CustomCondition checkSwordCount(int min, int max) {
        return new CustomCondition() {
            @Override
            public boolean predicate(LivingEntityPatch<?> entityPatch) {
                if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                    int swordCount = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(SwordSoaringDatakeys.SWORD_COUNT.get());
                    return min <= swordCount && max >= swordCount;
                }
                return false;
            }

        };
    }

    public static CustomCondition checkIsNotInaction() {
        return new CustomCondition() {
            @Override
            public boolean predicate(LivingEntityPatch<?> entityPatch) {
                if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                    int id = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get());
                    Entity entity = serverPlayerPatch.getOriginal().level().getEntity(id);
                    if (entity instanceof VatanseverEntity vatansever) {
                        return !vatansever.getPatch().getEntityState().movementLocked();
                    }
                }
                return false;
            }

        };
    }

}
