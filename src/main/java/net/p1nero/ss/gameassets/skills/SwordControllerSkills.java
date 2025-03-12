package net.p1nero.ss.gameassets.skills;

import net.p1nero.ss.gameassets.SwordSoaringSkillCategories;
import net.p1nero.ss.gameassets.SwordSoaringSkills;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import net.p1nero.ss.item.SwordSoaringItems;
import net.p1nero.ss.skill.sword_controller.*;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

public class SwordControllerSkills {
    public static Skill KILL_AURA_1;
    public static Skill KILL_AURA_2;
    public static Skill SCREEN_SWORD;
    public static Skill RAIN_SWORD;
    public static Skill GATE_OF_BABYLON;
    public static Skill WAN_JIAN_GUI_ZONG;

    public static void buildSwordControllerSkills(SkillBuildEvent.ModRegistryWorker registryWorker) {
        KILL_AURA_1 = registryWorker.build("kill_aura_1", KillAuraSkill::new, KillAuraSkill.createKillAuraBuilder()
                .setCreativeTab(SwordSoaringItems.DEFAULT_TAB.get())
                .setPlayerSummonAnim(ScreenSwordAnimations.PLAYER_SUMMON_KILL_AURA_1)
                .setSwordSummonAnim(ScreenSwordAnimations.KILL_AURA_1_SUMMON));
        KILL_AURA_2 = registryWorker.build("kill_aura_2", KillAuraSkill::new, KillAuraSkill.createKillAuraBuilder()
                .setCreativeTab(SwordSoaringItems.DEFAULT_TAB.get())
                .setPlayerSummonAnim(ScreenSwordAnimations.PLAYER_SUMMON_KILL_AURA_2)
                .setSwordSummonAnim(ScreenSwordAnimations.KILL_AURA_2_SUMMON));
        SCREEN_SWORD = registryWorker.build("screen_sword", ScreenSwordSkill::new, ScreenSwordSkill.createKillAuraBuilder()
                .setCreativeTab(SwordSoaringItems.DEFAULT_TAB.get())
                .setPlayerSummonAnim(ScreenSwordAnimations.PLAYER_SUMMON_SCREEN_SWORD)
                .setSwordSummonAnim(ScreenSwordAnimations.SCREEN_SWORD_SUMMON));
        RAIN_SWORD = registryWorker.build("rain_sword", RainSwordSkill::new, RainSwordSkill.createBuilder()
                .setCreativeTab(SwordSoaringItems.DEFAULT_TAB.get())
                .setCategory(SwordSoaringSkillCategories.SWORD_CONTROLLER)
                .setResource(Skill.Resource.NONE));
        GATE_OF_BABYLON = registryWorker.build("babylon", GateOfBabylonSkill::new, GateOfBabylonSkill.createBuilder()
                .setCreativeTab(SwordSoaringItems.DEFAULT_TAB.get())
                .setCategory(SwordSoaringSkillCategories.SWORD_CONTROLLER)
                .setResource(Skill.Resource.NONE));
        WAN_JIAN_GUI_ZONG = registryWorker.build("wan_jian_gui_zong", WanJianGuiZongSkill::new, WanJianGuiZongSkill.createBuilder()
                .setCreativeTab(SwordSoaringItems.DEFAULT_TAB.get())
                .setCategory(SwordSoaringSkillCategories.SWORD_CONTROLLER)
                .setResource(Skill.Resource.NONE));
    }
}
