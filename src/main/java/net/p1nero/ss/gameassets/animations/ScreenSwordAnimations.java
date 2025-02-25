package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.entity.vatansever_storm.VatanseverStormArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class ScreenSwordAnimations {
    public static StaticAnimation SCREEN_SWORD_IDLE;
    public static StaticAnimation SCREEN_SWORD_1;
    public static void buildScreenSwordAnim() {
        VatanseverStormArmature stormArmature = SwordSoaringArmatures.vatanseverStormArmature;
        SCREEN_SWORD_IDLE = new StaticAnimation(true, "screen_sword/screen_sword_idle", stormArmature);
        SCREEN_SWORD_1 = new StaticAnimation(true, "screen_sword/screen_sword_1", stormArmature);
    }

}
