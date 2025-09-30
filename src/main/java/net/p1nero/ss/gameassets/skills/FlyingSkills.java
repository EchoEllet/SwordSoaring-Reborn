package net.p1nero.ss.gameassets.skills;

import net.p1nero.ss.gameassets.animations.FlyAnimations;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkill;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkillElytra;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

public class FlyingSkills {
    public static Skill SWORD_SOARING_APPRENTICE;
    public static Skill SWORD_SOARING_EXPERT;
    public static Skill SWORD_SOARING_MASTER;
    public static Skill SWORD_SOARING_ELYTRA_APPRENTICE;
    public static Skill SWORD_SOARING_ELYTRA_EXPERT;
    public static Skill SWORD_SOARING_ELYTRA_MASTER;

    public static void buildSwordSoaringSkills(SkillBuildEvent.ModRegistryWorker registryWorker) {
        SWORD_SOARING_APPRENTICE = registryWorker.build("sword_soaring_apprentice", SwordSoaringSkill::new, SwordSoaringSkill.createSwordSoaringSkill()
                .setFlyingAnimations(FlyAnimations.APPRENTICE_INIT, FlyAnimations.APPRENTICE_FLYING, FlyAnimations.APPRENTICE_SPEED_UP));
        SWORD_SOARING_EXPERT = registryWorker.build("sword_soaring_expert", SwordSoaringSkill::new, SwordSoaringSkill.createSwordSoaringSkill()
                .setFlyingAnimations(FlyAnimations.EXPERT_INIT, FlyAnimations.EXPERT_FLYING, FlyAnimations.EXPERT_SPEED_UP));
        SWORD_SOARING_MASTER = registryWorker.build("sword_soaring_master", SwordSoaringSkill::new, SwordSoaringSkill.createSwordSoaringSkill()
                .setFlyingAnimations(FlyAnimations.MASTER_INIT, FlyAnimations.MASTER_FLYING, FlyAnimations.MASTER_SPEED_UP));
        SWORD_SOARING_ELYTRA_APPRENTICE = registryWorker.build("sword_soaring_elytra_apprentice", SwordSoaringSkillElytra::new, SwordSoaringSkill.createSwordSoaringSkill()
                .setFlyingAnimations(FlyAnimations.APPRENTICE_INIT, FlyAnimations.APPRENTICE_FLYING, FlyAnimations.APPRENTICE_SPEED_UP));
        SWORD_SOARING_ELYTRA_EXPERT = registryWorker.build("sword_soaring_elytra_expert", SwordSoaringSkillElytra::new, SwordSoaringSkill.createSwordSoaringSkill()
                .setFlyingAnimations(FlyAnimations.EXPERT_INIT, FlyAnimations.EXPERT_FLYING, FlyAnimations.EXPERT_SPEED_UP));
        SWORD_SOARING_ELYTRA_MASTER = registryWorker.build("sword_soaring_elytra_master", SwordSoaringSkillElytra::new, SwordSoaringSkill.createSwordSoaringSkill()
                .setFlyingAnimations(FlyAnimations.MASTER_INIT, FlyAnimations.MASTER_FLYING, FlyAnimations.MASTER_SPEED_UP));

    }
}
