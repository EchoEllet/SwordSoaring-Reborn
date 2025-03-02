package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class BabylonAnimations {

    public static StaticAnimation BABYLON_IDLE;
    public static StaticAnimation BABYLON_SHOOT;

    public static void buildBabylonAnim() {
        BabylonArmature babylonArmature = SwordSoaringArmatures.babylonArmature;
        BABYLON_IDLE = new StaticAnimation(true, "babylon/babylon_idle", babylonArmature);
        BABYLON_SHOOT = new StaticAnimation(true, "babylon/babylon_shoot", babylonArmature);
    }

}
