package net.p1nero.ss.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.ss.SwordSoaring;
import yesman.epicfight.world.item.WeaponItem;

public class SwordSoaringItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SwordSoaring.MOD_ID);
    public static final RegistryObject<Item> VATANSEVER = ITEMS.register("vatansever", () -> new WeaponItem(Tiers.NETHERITE, 1, 0, (new Item.Properties()).rarity(Rarity.EPIC)) {
        @Override
        public boolean isCorrectToolForDrops(BlockState blockIn) {
            return super.isCorrectToolForDrops(blockIn);
        }
    });

}
