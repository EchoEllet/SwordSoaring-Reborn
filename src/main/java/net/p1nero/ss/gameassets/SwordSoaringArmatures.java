package net.p1nero.ss.gameassets;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordArmature;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonArmature;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordArmature;
import net.p1nero.ss.entity.sword.sword_convergence.WanArmature;
import net.p1nero.ss.entity.vatansever.VatanseverArmature;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormArmature;
import yesman.epicfight.gameasset.Armatures;

public class SwordSoaringArmatures {
    public static Armatures.ArmatureAccessor<WanArmature> WAN_ARMATURE = Armatures.ArmatureAccessor.create(SwordSoaringMod.MOD_ID, "entity/wan", WanArmature::new);
    public static Armatures.ArmatureAccessor<BabylonArmature> BABYLON_ARMATURE = Armatures.ArmatureAccessor.create(SwordSoaringMod.MOD_ID, "entity/babylon", BabylonArmature::new);
    public static Armatures.ArmatureAccessor<FlySwordArmature> FLY_SWORD_ARMATURE = Armatures.ArmatureAccessor.create(SwordSoaringMod.MOD_ID, "entity/fly_sword", FlySwordArmature::new);
    public static Armatures.ArmatureAccessor<ScreenSwordArmature> SCREEN_SWORD_ARMATURE = Armatures.ArmatureAccessor.create(SwordSoaringMod.MOD_ID, "entity/screen_sword", ScreenSwordArmature::new);
    public static Armatures.ArmatureAccessor<VatanseverArmature> VATANSEVER_ARMATURE = Armatures.ArmatureAccessor.create(SwordSoaringMod.MOD_ID, "entity/vatansever", VatanseverArmature::new);
    public static Armatures.ArmatureAccessor<VatanseverStormArmature> VATANSEVER_STORM_ARMATURE = Armatures.ArmatureAccessor.create(SwordSoaringMod.MOD_ID, "entity/vatansever_swordgroup", VatanseverStormArmature::new);

    public static void registerArmatures(){
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.SWORD_CONVERGENCE_ENTITY.get(), WAN_ARMATURE);
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.BABYLON.get(), BABYLON_ARMATURE);
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.FLY_SWORD.get(), FLY_SWORD_ARMATURE);
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.SCREEN_SWORD.get(), SCREEN_SWORD_ARMATURE);
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.VATANSEVER.get(), VATANSEVER_ARMATURE);
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.VATANSEVER_STORM.get(), VATANSEVER_STORM_ARMATURE);
    }

}