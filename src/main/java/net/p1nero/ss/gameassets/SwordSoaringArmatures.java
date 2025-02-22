package net.p1nero.ss.gameassets;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.vatansever.VatanseverArmature;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormArmature;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.gameasset.Armatures;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwordSoaringArmatures {
    public static VatanseverArmature vatanseverArmature;
    public static VatanseverStormArmature vatanseverStormArmature;

    @SubscribeEvent
    public static void build(ModelBuildEvent.ArmatureBuild event) {
        vatanseverArmature = event.get(SwordSoaring.MOD_ID, "entity/vatansever", VatanseverArmature::new);
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.VATANSEVER.get(), vatanseverArmature);
        vatanseverStormArmature = event.get(SwordSoaring.MOD_ID, "entity/vatansever_swordgroup", VatanseverStormArmature::new);
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.VATANSEVER_STORM.get(), vatanseverStormArmature);
    }
}