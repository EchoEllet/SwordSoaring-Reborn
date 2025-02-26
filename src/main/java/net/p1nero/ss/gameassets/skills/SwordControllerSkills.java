package net.p1nero.ss.gameassets.skills;

import net.p1nero.ss.gameassets.SwordSoaringSkills;
import net.p1nero.ss.item.SwordSoaringItems;
import net.p1nero.ss.skill.sword_controller.ScreenSwordSkill;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

public class SwordControllerSkills {
    public static Skill SCREEN_SWORD;
    public static void buildSwordControllerSkills(SkillBuildEvent event) {
        SCREEN_SWORD = SwordSoaringSkills.build(event, ScreenSwordSkill::new, ScreenSwordSkill.createScreenSwordBuilder().setCreativeTab(SwordSoaringItems.SWORD_SOARING_ITEM_TAB), "sword_screen");
    }
}
