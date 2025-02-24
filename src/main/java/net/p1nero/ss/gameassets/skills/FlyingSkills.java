package net.p1nero.ss.gameassets.skills;

import net.p1nero.ss.gameassets.SwordSoaringSkills;
import net.p1nero.ss.gameassets.animations.FlyAnimations;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkill;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

public class FlyingSkills {
    public static Skill SWORD_SOARING_APPRENTICE;
    public static Skill SWORD_SOARING_EXPERT;
    public static Skill SWORD_SOARING_MASTER;

    public static void buildSwordSoaringSkills(SkillBuildEvent event) {
        SWORD_SOARING_APPRENTICE = SwordSoaringSkills.build(event, SwordSoaringSkill::new, SwordSoaringSkill.createSwordSoaringSkill()
                .setFlyingAnimations(() -> FlyAnimations.APPRENTICE_INIT, () -> FlyAnimations.APPRENTICE_FLYING, () -> FlyAnimations.APPRENTICE_ACCELERATION), "sword_soaring_apprentice");
        SWORD_SOARING_EXPERT = SwordSoaringSkills.build(event, SwordSoaringSkill::new, SwordSoaringSkill.createSwordSoaringSkill().setPriorSkill(() -> SWORD_SOARING_APPRENTICE)
                .setFlyingAnimations(() -> FlyAnimations.EXPERT_INIT, () -> FlyAnimations.EXPERT_FLYING, () -> FlyAnimations.EXPERT_ACCELERATION), "sword_soaring_expert");
        SWORD_SOARING_MASTER = SwordSoaringSkills.build(event, SwordSoaringSkill::new, SwordSoaringSkill.createSwordSoaringSkill().setPriorSkill(() -> SWORD_SOARING_EXPERT)
                .setFlyingAnimations(() -> FlyAnimations.MASTER_INIT, () -> FlyAnimations.MASTER_FLYING, () -> FlyAnimations.MASTER_ACCELERATION), "sword_soaring_master");

    }
}
