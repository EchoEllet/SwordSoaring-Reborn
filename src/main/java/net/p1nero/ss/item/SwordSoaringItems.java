package net.p1nero.ss.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.ss.SwordSoaringMod;

public class SwordSoaringItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SwordSoaringMod.MOD_ID);
    public static final RegistryObject<Item> VATANSEVER = ITEMS.register("vatansever", () -> new VatanseverItem(Tiers.NETHERITE, 10, 0, (new Item.Properties()).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> MERLIN_GG = ITEMS.register("merlin_gg", () ->
            new MerlinSuperGG(Tiers.NETHERITE, 99999999, 1.6F, new Item.Properties()));
    //测试物品：merlin超级神牛

    public static final DeferredRegister<CreativeModeTab> SWORD_SOARING_ITEM_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SwordSoaringMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> DEFAULT_TAB = SWORD_SOARING_ITEM_TAB.register("sword_soaring_items",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.sword_soaring.items"))
                    .icon(() -> new ItemStack(VATANSEVER.get()))
                    .displayItems((parameters, tabData) -> tabData.accept(VATANSEVER.get())).build());

}
