package net.p1nero.ss.gameassets;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.gameassets.skills.FlyingSkills;
import net.p1nero.ss.gameassets.skills.SwordControllerSkills;
import net.p1nero.ss.gameassets.skills.VatanseverSkills;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;

@Mod.EventBusSubscriber(modid = SwordSoaringMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwordSoaringSkills {

    @SubscribeEvent
    public static void buildSkills(SkillBuildEvent event){
        SkillBuildEvent.ModRegistryWorker registryWorker = event.createRegistryWorker(SwordSoaringMod.MOD_ID);
        SwordControllerSkills.buildSwordControllerSkills(registryWorker);
        FlyingSkills.buildSwordSoaringSkills(registryWorker);
        VatanseverSkills.buildVatanseverSkills(registryWorker);
    }

}
