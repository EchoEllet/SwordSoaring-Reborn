package net.p1nero.ss.gameassets.skills;

import net.p1nero.ss.gameassets.SwordSoaringSkills;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import net.p1nero.ss.item.SwordSoaringItems;
import net.p1nero.ss.skill.sword_controller.KillAuraSkill;
import net.p1nero.ss.skill.sword_controller.ScreenSwordSkill;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

public class SwordControllerSkills {
    public static Skill KILL_AURA_1;
    public static Skill KILL_AURA_2;
    public static Skill SCREEN_SWORD;

    public static void buildSwordControllerSkills(SkillBuildEvent event) {
        KILL_AURA_1 = SwordSoaringSkills.build(event, KillAuraSkill::new, KillAuraSkill.createKillAuraBuilder().setCreativeTab(SwordSoaringItems.SWORD_SOARING_ITEM_TAB)
                .setScreenSwordAnim(() -> ScreenSwordAnimations.KILL_AURA_1), "kill_aura_1");
        KILL_AURA_2 = SwordSoaringSkills.build(event, KillAuraSkill::new, KillAuraSkill.createKillAuraBuilder().setCreativeTab(SwordSoaringItems.SWORD_SOARING_ITEM_TAB)
                .setScreenSwordAnim(() -> ScreenSwordAnimations.KILL_AURA_2), "kill_aura_2");
        SCREEN_SWORD = SwordSoaringSkills.build(event, ScreenSwordSkill::new, ScreenSwordSkill.createKillAuraBuilder().setCreativeTab(SwordSoaringItems.SWORD_SOARING_ITEM_TAB)
                .setScreenSwordAnim(() -> ScreenSwordAnimations.SCREEN_SWORD), "screen_sword");
    }
}
