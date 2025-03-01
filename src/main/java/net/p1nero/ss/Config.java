package net.p1nero.ss;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue ENABLE_LOOT_TABLE;
    public static final ForgeConfigSpec.BooleanValue ARACHNOPHOBIA_MODE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEMS_CAN_FLY;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEMS_CAN_NOT_FLY;

    static final ForgeConfigSpec SPEC;

    static {
        ENABLE_LOOT_TABLE = createBool("enable_loot_table", true, "", "若为true，击败boss将可获取技能书。否则你将自己添加技能书获取方式。");
        ARACHNOPHOBIA_MODE = createBool("arachnophobia_mode", false,"Arachnophobia mode, if true, the boss will have no legs.", "蜘蛛恐惧症模式：true时boss将不会有腿");
        BUILDER.push("Sword Soaring");
        ITEMS_CAN_FLY = BUILDER
                .comment("A list of items considered as sword.")
                .defineListAllowEmpty(List.of("items can fly"), List::of, Config::validateItemName);
        ITEMS_CAN_NOT_FLY = BUILDER
                .comment("A list of items not considered as sword.")
                .defineListAllowEmpty(List.of("items can't fly"), () -> List.of("sword_soaring:vatansever"), Config::validateItemName);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static Set<Item> swordItems = new HashSet<>();
    public static Set<Item> notSwordItems = new HashSet<>();

    private static ForgeConfigSpec.BooleanValue createBool(String key, boolean defaultValue, String ...comment){
        return BUILDER
                .comment(comment)
                .translation("config."+SwordSoaring.MOD_ID+"."+key)
                .define(key, defaultValue);
    }

    private static ForgeConfigSpec.DoubleValue createDouble(String comment ,String key, double defaultValue) {
        return BUILDER
                .comment(comment)
                .translation("config."+SwordSoaring.MOD_ID+"."+key)
                .defineInRange(key, defaultValue, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    private static boolean validateItemName(final Object obj){
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

}
