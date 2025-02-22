package net.p1nero.ss.gameassets.animations;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaring;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwordSoaringAnimations {
    public static StaticAnimation FLY_ON_SWORD_BASIC;
    public static StaticAnimation FLY_ON_SWORD_ADVANCED;

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(SwordSoaring.MOD_ID, ()->{
            buildFlyAnim();
            VatanseverAnimations.buildVatanseverAnim();
            VatanseverStormAnimations.buildVatanseverStormAnim();
        });
    }

    private static void buildFlyAnim() {
        HumanoidArmature biped = Armatures.BIPED;

        FLY_ON_SWORD_BASIC = new StaticAnimation(false, "biped/fly_anim/fly_on_sword_beginner", biped);
        FLY_ON_SWORD_ADVANCED = new StaticAnimation(false, "biped/fly_anim/fly_on_sword_master", biped);

    }


}
