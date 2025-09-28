package net.p1nero.ss.gameassets;

import net.minecraft.resources.ResourceLocation;
import net.p1nero.ss.SwordSoaringMod;
import yesman.epicfight.skill.SkillCategory;

public enum SwordSoaringSkillCategories implements SkillCategory {
    SWORD_CONTROLLER(true, true, true, ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "skillbook_sword_controller")),
    SWORD_SOARING(true, true, true, ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "skillbook_fly_skill"));

    final boolean save;
    final boolean sync;
    final boolean modifiable;
    final ResourceLocation bookIcon;
    final int id;

    SwordSoaringSkillCategories(boolean shouldSave, boolean shouldSync, boolean modifiable){
        this(shouldSave, shouldSync, modifiable, null);
    }

    SwordSoaringSkillCategories(boolean shouldSave, boolean shouldSync, boolean modifiable, ResourceLocation bookIcon){
        this.modifiable = modifiable;
        this.save = shouldSave;
        this.sync = shouldSync;
        this.id = SkillCategory.ENUM_MANAGER.assign(this);
        this.bookIcon = bookIcon;
    }

    @Override
    public boolean shouldSave()
    {
        return this.save;
    }

    @Override
    public boolean shouldSynchronize()
    {
        return this.sync;
    }

    @Override
    public boolean learnable()
    {
        return this.modifiable;
    }
    @Override
    public int universalOrdinal()
    {
        return this.id;
    }

    @Override
    public ResourceLocation bookIcon() {
        return bookIcon == null ? SkillCategory.DEFAULT_BOOK_ICON : bookIcon;
    }
}