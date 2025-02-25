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
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEMS_CAN_FLY;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEMS_CAN_NOT_FLY;

    static final ForgeConfigSpec SPEC;

    static {
        ENABLE_LOOT_TABLE = createBool("if true, you can get all skill books via fishing, end city, ancient city and strong hold library. or you have to add loot table yourself(for mod pack author)","enable_loot_table", true);

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

    private static ForgeConfigSpec.BooleanValue createBool(String comment, String key, boolean defaultValue){
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
