package net.p1nero.ss.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class ItemUtils {
    public static double getItemAttackDamage(LivingEntity livingEntity, ItemStack itemStack){
        if(itemStack.isEmpty()){
            return 0;
        }
        AtomicReference<Double> totalDamage;
        if(livingEntity != null){
            totalDamage = new AtomicReference<>(livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
        } else {
            totalDamage = new AtomicReference<>(0.0);
        }
        itemStack.getAttributeModifiers().forEach(EquipmentSlot.MAINHAND, ((attributeHolder, attributeModifier) -> {
            if(attributeHolder == Attributes.ATTACK_DAMAGE){
                if(attributeModifier.operation().equals(AttributeModifier.Operation.ADD_VALUE)){
                    totalDamage.updateAndGet(v -> v + attributeModifier.amount());
                }
            }
        }));
        return totalDamage.get();
    }

    public static ArrayList<ItemStack> calculateValidBabylonItems(ServerPlayer player, boolean shouldDelete) {
        return calculateValidBabylonItems(player, shouldDelete, (itemStack -> true));
    }

    /**
     * 获取玩家身上以及末影箱的所有物品
     */
    public static ArrayList<ItemStack> calculateValidBabylonItems(ServerPlayer player, boolean shouldDelete, Predicate<ItemStack> predicate) {
        ArrayList<ItemStack> validBabylonItems = new ArrayList<>();
        player.getInventory().items.forEach(itemStack -> {

            //包括背包，潜影贝等
            IItemHandler itemHandler = itemStack.getCapability(Capabilities.ItemHandler.ITEM);
            if (itemHandler != null) {
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    ItemStack inSideItem = itemHandler.getStackInSlot(i);
                    if (!inSideItem.isEmpty() && predicate.test(inSideItem)) {
                        validBabylonItems.add(inSideItem.copy());
                        if(shouldDelete){
                            inSideItem.setCount(0);
                        }
                    }
                }
            } else {
                if (!itemStack.isEmpty() && predicate.test(itemStack)) {
                    validBabylonItems.add(itemStack.copy());
                    if(shouldDelete){
                        itemStack.setCount(0);
                    }
                }
            }
        });
        ArrayList<ItemStack> enderChestStacks = new ArrayList<>();
        PlayerEnderChestContainer enderChestContainer = player.getEnderChestInventory();
        for (int i = 0; i < enderChestContainer.getContainerSize(); i++) {
            ItemStack itemStack = enderChestContainer.getItem(i);
            if (!itemStack.isEmpty() && predicate.test(itemStack)) {
                enderChestStacks.add(itemStack.copy());
                if(shouldDelete){
                    itemStack.setCount(0);
                }
            }
        }
        validBabylonItems.addAll(enderChestStacks);
        return validBabylonItems;
    }

}
