package net.p1nero.ss.gameassets.animations;

import net.p1nero.ss.animation.VatanseverStormAnimation;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class VatanseverStormAnimations {

    public static StaticAnimation VATANSEVER_STORM_IDLE;
    public static StaticAnimation VATANSEVER_STORM_1;
    public static StaticAnimation VATANSEVER_STORM_2;
    public static StaticAnimation VATANSEVER_STORM_3;
    public static StaticAnimation VATANSEVER_STORM_4;
    protected static void buildVatanseverStormAnim() {
        VatanseverStormArmature stormArmature = SwordSoaringArmatures.vatanseverStormArmature;
        VATANSEVER_STORM_IDLE = new StaticAnimation(true, "vatansever_storm/vatansever_swordgroup_idle", stormArmature);
        VATANSEVER_STORM_1 = new VatanseverStormAnimation(0.15F, "vatansever_storm/vatansever_storm_1_l", stormArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 10.0F, 10.0F, Float.MAX_VALUE, stormArmature.rootJoint, SwordSoaringColliders.VATANSEVER_STORM_PART));
        VATANSEVER_STORM_2 = new VatanseverStormAnimation(0.15F, "vatansever_storm/vatansever_storm_1_r", stormArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 10.0F, 10.0F, Float.MAX_VALUE, stormArmature.rootJoint, SwordSoaringColliders.VATANSEVER_STORM_PART));
        VATANSEVER_STORM_3 = new VatanseverStormAnimation(0.15F, "vatansever_storm/vatansever_storm_2_l", stormArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 10.0F, 10.0F, Float.MAX_VALUE, stormArmature.rootJoint, SwordSoaringColliders.VATANSEVER_STORM_PART));
        VATANSEVER_STORM_4 = new VatanseverStormAnimation(0.15F, "vatansever_storm/vatansever_storm_2_r", stormArmature,
                new AttackAnimation.Phase(0.0F, 0.0F, 10.0F, 10.0F, Float.MAX_VALUE, stormArmature.rootJoint, SwordSoaringColliders.VATANSEVER_STORM_PART));
    }

}
