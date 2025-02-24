package net.p1nero.ss;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.p1nero.ss.enchantment.SwordSoaringEnchantments;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.gameassets.SwordSoaringCategories;
import net.p1nero.ss.gameassets.SwordSoaringSkillCategories;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.item.SwordSoaringItems;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkill;
import org.slf4j.Logger;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillSlot;

import java.util.stream.Collectors;

@Mod(SwordSoaring.MOD_ID)
public class SwordSoaring {

    public static final String MOD_ID = "sword_soaring";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SwordSoaring() {
        SkillCategories.ENUM_MANAGER.loadPreemptive(SwordSoaringSkillCategories.class);
        SkillSlot.ENUM_MANAGER.loadPreemptive(SwordSoaringSkillSlots.class);
        SkillCategories.ENUM_MANAGER.loadPreemptive(SwordSoaringCategories.class);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        SwordSoaringItems.ITEMS.register(bus);
        SwordSoaringEntities.ENTITIES.register(bus);
        SwordSoaringEnchantments.ENCHANTMENTS.register(bus);
        MinecraftForge.EVENT_BUS.addListener(SwordSoaringSkill::onLivingEquipmentChange);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PacketHandler.register();
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

}
