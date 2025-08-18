package net.p1nero.ss.compat;

import com.yesman.epicskills.client.gui.screen.CategorySlotTexture;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.client.SwordSoaringCategorySlotTextures;

public class EpicSkillsCompat {

    public static void registerCategorySlotTexture() {
        CategorySlotTexture.ENUM_MANAGER.registerEnumCls(SwordSoaringMod.MOD_ID, SwordSoaringCategorySlotTextures.class);
    }
}
