package net.p1nero.ss.gameassets;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.gameassets.animations.*;
import yesman.epicfight.api.animation.AnimationManager;

@Mod.EventBusSubscriber(modid = SwordSoaringMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwordSoaringAnimations {

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(SwordSoaringMod.MOD_ID, (builder)->{
            SwordConvergenceAnimations.buildSwordConvergenceAnim(builder);
            BabylonAnimations.buildBabylonAnim(builder);
            FlyAnimations.buildFlyAnim(builder);
            FlySwordAnimations.buildFlySwordAnim(builder);
            ScreenSwordAnimations.buildScreenSwordAnim(builder);
            VatanseverAnimations.buildVatanseverAnim(builder);
            VatanseverStormAnimations.buildVatanseverStormAnim(builder);
        });
    }


}
