package net.p1nero.ss.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public class ItemUtils {
    public static double getItemAttackDamage(LivingEntity livingEntity, ItemStack itemStack){
        AtomicReference<Double> totalDamage;
        if(livingEntity != null){
            totalDamage = new AtomicReference<>(livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
        } else {
            totalDamage = new AtomicReference<>(0.0);
        }
        itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).forEach(attributeModifier -> {
            if(attributeModifier.getOperation().equals(AttributeModifier.Operation.ADDITION)){
                totalDamage.updateAndGet(v -> v + attributeModifier.getAmount());
            }
        });
        return totalDamage.get();
    }
}
