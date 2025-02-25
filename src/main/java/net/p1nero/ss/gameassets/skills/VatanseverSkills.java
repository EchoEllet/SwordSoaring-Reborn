package net.p1nero.ss.gameassets.skills;

import com.p1nero.invincible.skill.ComboBasicAttack;
import com.p1nero.invincible.skill.api.ComboNode;
import net.p1nero.ss.gameassets.SwordSoaringSkills;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import net.p1nero.ss.skill.weapon_innate.VatanseverWeaponInnateSkill;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;

public class VatanseverSkills {
    public static Skill VATANSEVER_INNATE;
    public static Skill VATANSEVER_PASSIVE;

    public static void buildVatanseverSkills(SkillBuildEvent event){
        ComboNode root = ComboNode.create();
        ComboNode a = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO1);
        ComboNode aa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO2);
        ComboNode aaa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO3);
        ComboNode aaaa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO4);
        ComboNode aaaaa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO5);
        ComboNode storm = ComboNode.createNode(()->VatanseverAnimations.PLAYER_STORM_START);
        root.key1(a);
        root.key3(storm);
        a.key1(aa);
        aa.key1(aaa);
        aaa.key1(aaaa);
        VATANSEVER_INNATE = SwordSoaringSkills.build(event, VatanseverWeaponInnateSkill::new, ComboBasicAttack.createComboBasicAttack().setCombo(root), "vatansever_innate");
        VATANSEVER_PASSIVE = SwordSoaringSkills.build(event, VatanseverPassive::new, Skill.createBuilder().setCategory(SkillCategories.WEAPON_PASSIVE).setResource(Skill.Resource.NONE), "vatansever_passive");
    }
}
