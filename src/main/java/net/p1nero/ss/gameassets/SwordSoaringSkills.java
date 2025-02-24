package net.p1nero.ss.gameassets;

import com.p1nero.invincible.skill.ComboBasicAttack;
import com.p1nero.invincible.skill.api.ComboNode;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkill;
import net.p1nero.ss.skill.weapon_innate.VatanseverWeaponInnateSkill;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID)
public class SwordSoaringSkills {
    public static Skill SWORD_SOARING;
    public static Skill VATANSEVER_INNATE;
    public static Skill VATANSEVER_PASSIVE;

    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event){
        SWORD_SOARING = build(event, SwordSoaringSkill::new, Skill.createBuilder().setCategory(SwordSoaringSkillCategories.SWORD_SOARING).setResource(Skill.Resource.NONE), "sword_soaring");

        ComboNode root = ComboNode.create();
        ComboNode a = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO1);
        ComboNode aa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO2);
        ComboNode aaa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO3);
        ComboNode aaaa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO4);
        ComboNode aaaaa = ComboNode.createNode(() -> VatanseverAnimations.PLAYER_AUTO5);
        ComboNode storm = ComboNode.createNode(()->VatanseverAnimations.PLAYER_STORM_START);
        root.key1(a);
        root.keyWeaponInnate(storm);
        a.key1(aa);
        aa.key1(aaa);
        aaa.key1(aaaa);
        VATANSEVER_INNATE = build(event, VatanseverWeaponInnateSkill::new, ComboBasicAttack.createComboBasicAttack().setCombo(root), "vatansever_innate");
        VATANSEVER_PASSIVE = build(event, VatanseverPassive::new, Skill.createBuilder().setCategory(SkillCategories.WEAPON_PASSIVE).setResource(Skill.Resource.NONE), "vatansever_passive");
    }

    public static <T extends Skill, B extends Skill.Builder<T>> Skill build(SkillBuildEvent event, Function<B, T> constructor, B builder, String name){
        SkillManager.register(constructor, builder, SwordSoaring.MOD_ID, name);
        return event.build(SwordSoaring.MOD_ID, name);
    }

}
