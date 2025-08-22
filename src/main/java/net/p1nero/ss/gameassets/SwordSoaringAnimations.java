package net.p1nero.ss.gameassets;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.gameassets.animations.*;
import yesman.epicfight.api.animation.AnimationManager;

@EventBusSubscriber(modid = SwordSoaringMod.MOD_ID)
public class SwordSoaringAnimations {

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(SwordSoaringMod.MOD_ID, (builder)->{
            WanAnimations.buildWanAnim(builder);
            BabylonAnimations.buildBabylonAnim(builder);
            FlyAnimations.buildFlyAnim(builder);
            FlySwordAnimations.buildFlySwordAnim(builder);
            ScreenSwordAnimations.buildScreenSwordAnim(builder);
            VatanseverAnimations.buildVatanseverAnim(builder);
            VatanseverStormAnimations.buildVatanseverStormAnim(builder);
        });
    }


}
