package net.p1nero.ss.gameassets;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.gameassets.animations.FlyAnimations;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import net.p1nero.ss.gameassets.animations.VatanseverStormAnimations;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwordSoaringAnimations {

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(SwordSoaring.MOD_ID, ()->{
            FlyAnimations.buildFlyAnim();
            VatanseverAnimations.buildVatanseverAnim();
            VatanseverStormAnimations.buildVatanseverStormAnim();
        });
    }


}
