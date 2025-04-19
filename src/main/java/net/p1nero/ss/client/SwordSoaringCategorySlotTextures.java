package net.p1nero.ss.client;

import com.yesman.epicskills.client.gui.screen.CategorySlotTexture;

public enum SwordSoaringCategorySlotTextures implements CategorySlotTexture {
    SWORD_SOARING(6, 6, 44, 44),
    SWORD_CONTROLLER(6, 6, 44, 44);
    private int offsetX;
    private int offsetY;
    private int texWidth;
    private int texHeight;
    private int universalOrder;

    private SwordSoaringCategorySlotTextures(int offsetX, int offsetY, int texWidth, int texHeight) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.universalOrder = CategorySlotTexture.ENUM_MANAGER.assign(this);
    }

    public int offsetX() {
        return this.offsetX;
    }

    public int offsetY() {
        return this.offsetY;
    }

    public int texWidth() {
        return this.texWidth;
    }

    public int texHeight() {
        return this.texHeight;
    }

    public int universalOrdinal() {
        return this.universalOrder;
    }
}
