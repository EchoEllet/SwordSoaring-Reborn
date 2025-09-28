package net.p1nero.ss.gameassets;

import yesman.epicfight.api.animation.LivingMotion;

public enum SwordSoaringLivingMotions implements LivingMotion {
    SWORD_SOARING_BASIC,
    SWORD_SOARING_EXPERT,
    SWORD_SOARING_MASTER,
    SPEED_UP_BASIC,
    SPEED_UP_EXPERT,
    SPEED_UP_MASTER;
    final int id;
    SwordSoaringLivingMotions() {
        this.id = LivingMotion.ENUM_MANAGER.assign(this);
    }
    @Override
    public int universalOrdinal() {
        return id;
    }
}
