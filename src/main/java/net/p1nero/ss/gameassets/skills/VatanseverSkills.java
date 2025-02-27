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
        ComboNode aab = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO3_B);
        ComboNode aaaa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO4);
        ComboNode aaab = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO4_B);
        ComboNode storm = ComboNode.createNode(()->VatanseverAnimations.PLAYER_STORM_START);
        root.key1(a);
        a.key1(aa);
        aa.key1(aaa);
        aa.key2(aab);
        aaa.key1(aaaa);
        aaa.key2(aaab);
        root.key3(storm);
        VATANSEVER_INNATE = SwordSoaringSkills.build(event, VatanseverWeaponInnateSkill::new, ComboBasicAttack.createComboBasicAttack().setCombo(root), "vatansever_innate");
        VATANSEVER_PASSIVE = SwordSoaringSkills.build(event, VatanseverPassive::new, Skill.createBuilder().setCategory(SkillCategories.WEAPON_PASSIVE).setResource(Skill.Resource.NONE), "vatansever_passive");
    }
}
