package net.p1nero.ss;

import com.mojang.logging.LogUtils;
import com.p1nero.invincible.api.skill.ComboType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.p1nero.ss.client.sound.SwordSoaringSounds;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.gameassets.*;
import net.p1nero.ss.item.SwordSoaringItems;
import org.slf4j.Logger;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod(SwordSoaringMod.MOD_ID)
public class SwordSoaringMod {

    public static final String MOD_ID = "sword_soaring";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SwordSoaringMod(FMLJavaModLoadingContext context) {
        SkillCategories.ENUM_MANAGER.registerEnumCls(SwordSoaringMod.MOD_ID, SwordSoaringSkillCategories.class);
        SkillSlot.ENUM_MANAGER.registerEnumCls(SwordSoaringMod.MOD_ID, SwordSoaringSkillSlots.class);
        CapabilityItem.WeaponCategories.ENUM_MANAGER.registerEnumCls(SwordSoaringMod.MOD_ID, SwordSoaringCategories.class);
        ComboType.ENUM_MANAGER.registerEnumCls(SwordSoaringMod.MOD_ID, SwordSoaringComboTypes.class);

        IEventBus bus = context.getModEventBus();
        SwordSoaringDatakeys.DATA_KEYS.register(bus);
        SwordSoaringItems.ITEMS.register(bus);
        SwordSoaringItems.SWORD_SOARING_ITEM_TAB.register(bus);
        SwordSoaringEntities.ENTITIES.register(bus);
        SwordSoaringSounds.SOUND_EVENTS.register(bus);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static boolean isArmourersWorkshopLoaded() {
        return ModList.get().isLoaded("armourers_workshop");
    }

    /**
     * 判断物品是否属于剑或者被视为剑。
     * 无法监听事件，干脆直接在这里初始化剑物品表。
     */
    public static boolean isValidSword(ItemStack sword) {
        if (Config.swordItems.isEmpty()) {
            Config.swordItems = Config.ITEMS_CAN_FLY.get().stream()
                    .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                    .collect(Collectors.toSet());
            Config.notSwordItems = Config.ITEMS_CAN_NOT_FLY.get().stream()
                    .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                    .collect(Collectors.toSet());
        }
        if (Config.notSwordItems.contains(sword.getItem())) {
            return false;
        }
        return sword.getItem() instanceof SwordItem || Config.swordItems.contains(sword.getItem());
    }

    public static void runInArmourersWorkshopLoaded(Supplier<Runnable> handler) {
        if (isArmourersWorkshopLoaded()) {
            handler.get().run();
        }
    }

}
