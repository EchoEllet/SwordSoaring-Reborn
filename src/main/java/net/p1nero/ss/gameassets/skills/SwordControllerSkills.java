package net.p1nero.ss.gameassets.skills;

import net.p1nero.ss.gameassets.SwordSoaringSkills;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import net.p1nero.ss.item.SwordSoaringItems;
import net.p1nero.ss.skill.sword_controller.ScreenSwordSkill;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

public class SwordControllerSkills {
    public static Skill SCREEN_SWORD_1;
    public static Skill SCREEN_SWORD_2;
    public static Skill SCREEN_SWORD_3;
    public static void buildSwordControllerSkills(SkillBuildEvent event) {
        SCREEN_SWORD_1 = SwordSoaringSkills.build(event, ScreenSwordSkill::new, ScreenSwordSkill.createScreenSwordBuilder().setCreativeTab(SwordSoaringItems.SWORD_SOARING_ITEM_TAB)
                .setLifeTime(200).setScreenSwordAnim(()->ScreenSwordAnimations.SCREEN_SWORD_1), "screen_sword_1");
        SCREEN_SWORD_2 = SwordSoaringSkills.build(event, ScreenSwordSkill::new, ScreenSwordSkill.createScreenSwordBuilder().setCreativeTab(SwordSoaringItems.SWORD_SOARING_ITEM_TAB)
                .setLifeTime(200).setScreenSwordAnim(()->ScreenSwordAnimations.SCREEN_SWORD_2), "screen_sword_2");
        SCREEN_SWORD_3 = SwordSoaringSkills.build(event, ScreenSwordSkill::new, ScreenSwordSkill.createScreenSwordBuilder().setCreativeTab(SwordSoaringItems.SWORD_SOARING_ITEM_TAB)
                .setLifeTime(200).setScreenSwordAnim(()->ScreenSwordAnimations.SCREEN_SWORD_3), "screen_sword_3");
    }
}
