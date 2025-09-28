package net.p1nero.ss.gameassets;

import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum SwordSoaringWeaponCategories implements WeaponCategory {
    ARTIFACT_SPIRIT;
    SwordSoaringWeaponCategories(){
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }
    final int id;
    @Override
    public int universalOrdinal() {
        return this.id;
    }

}