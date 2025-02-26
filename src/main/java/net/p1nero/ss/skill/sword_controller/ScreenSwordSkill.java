package net.p1nero.ss.skill.sword_controller;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSword;
import net.p1nero.ss.gameassets.SwordSoaringSkillCategories;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class ScreenSwordSkill extends Skill {
    public ScreenSwordSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Builder<Skill> createScreenSwordBuilder() {
        return Skill.createBuilder().setCategory(SwordSoaringSkillCategories.SWORD_CONTROLLER).setResource(Resource.NONE);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        return SwordSoaring.isValidSword(executer.getValidItemInHand(InteractionHand.MAIN_HAND));
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        ScreenSword screenSword = new ScreenSword(executer.getOriginal());
        executer.getOriginal().level.addFreshEntity(screenSword);
    }
}
