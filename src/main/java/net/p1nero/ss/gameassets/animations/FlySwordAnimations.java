package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.entity.sword.fly_sword.FlySwordArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class FlySwordAnimations {
    public static StaticAnimation FLY_SWORD_ATK_1;
    public static StaticAnimation FLY_SWORD_ATK_2;
    public static StaticAnimation FLY_SWORD_ATK_3;
    public static StaticAnimation FLY_SWORD_ATK_IDLE;
    public static StaticAnimation FLY_SWORD_ATK_FLY;
    public static void buildFlySwordAnim() {
        FlySwordArmature flySwordArmature = SwordSoaringArmatures.flySwordArmature;
        FLY_SWORD_ATK_1 = new AttackAnimation(0.15F, "fly_sword/fly_sword_atk_1", flySwordArmature);
        FLY_SWORD_ATK_2 = new AttackAnimation(0.15F, "fly_sword/fly_sword_atk_2", flySwordArmature);
        FLY_SWORD_ATK_3 = new AttackAnimation(0.15F, "fly_sword/fly_sword_atk_3", flySwordArmature);
        FLY_SWORD_ATK_IDLE = new StaticAnimation(true, "fly_sword/fly_sword_idle", flySwordArmature);
        FLY_SWORD_ATK_FLY = new StaticAnimation(true, "fly_sword/fly_sword_fly", flySwordArmature);
    }
}
