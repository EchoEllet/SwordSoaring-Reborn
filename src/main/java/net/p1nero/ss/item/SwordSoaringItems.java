package net.p1nero.ss.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p1nero.ss.SwordSoaringMod;
import yesman.epicfight.world.item.TieredWeaponItem;

public class SwordSoaringItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, SwordSoaringMod.MOD_ID);
    public static final DeferredHolder<Item, VatanseverItem> VATANSEVER = ITEMS.register("vatansever", () -> new VatanseverItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).attributes(TieredWeaponItem.createAttributes(10.0F, 0))));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SwordSoaringMod.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DEFAULT_TAB = CREATIVE_TABS.register("sword_soaring_items",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.sword_soaring.items"))
                    .icon(() -> new ItemStack(VATANSEVER.get()))
                    .displayItems((parameters, tabData) -> {
                                tabData.accept(VATANSEVER.get());
                            }
                    ).build());

}

