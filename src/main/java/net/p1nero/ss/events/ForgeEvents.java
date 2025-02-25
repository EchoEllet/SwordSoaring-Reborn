package net.p1nero.ss.events;

import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.Config;
import net.p1nero.ss.SwordSoaring;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID)
public class ForgeEvents {

    /**
     * 把技能书加到箱子里
     */
    @SubscribeEvent
    public static void modifyVanillaLootPools(final LootTableLoadEvent event) {
        if (!Config.ENABLE_LOOT_TABLE.get()) {
            return;
        }
        int modifier = ConfigManager.SKILL_BOOK_CHEST_LOOT_MODIFYER.get();
        int dropChance = 100 + modifier;
        int antiDropChance = 100 - modifier;
        float dropChanceModifier = dropChance / (float) (antiDropChance + dropChance);

        String[] skills = new String[]{
                "sword_soaring:sword_soaring_apprentice",
                "sword_soaring:sword_soaring_expert",
                "sword_soaring:sword_soaring_master"
        };

        if (event.getName().equals(BuiltInLootTables.END_CITY_TREASURE)) {
            event.getTable().addPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                    .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                            skills
                    )).when(LootItemRandomChanceCondition.randomChance(dropChanceModifier)))
                    .build());
        }

        if (event.getName().equals(BuiltInLootTables.FISHING_TREASURE)) {
            event.getTable().addPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                    .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                            skills
                    )).when(LootItemRandomChanceCondition.randomChance(dropChanceModifier)))
                    .build());
        }

        if (event.getName().equals(BuiltInLootTables.STRONGHOLD_LIBRARY)) {
            event.getTable().addPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 5.0F))
                    .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                            skills
                    ))).when(LootItemRandomChanceCondition.randomChance(dropChanceModifier * 0.3F))
                    .build());
        }

    }

}