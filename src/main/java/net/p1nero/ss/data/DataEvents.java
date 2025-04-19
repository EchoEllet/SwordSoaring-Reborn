package net.p1nero.ss.data;

import com.yesman.epicskills.common.data.SkillTreeProvider;
import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.p1nero.ss.SwordSoaringMod;

@EventBusSubscriber(modid = SwordSoaringMod.MOD_ID, bus = Bus.MOD)
public final class DataEvents {

    @SubscribeEvent
    public static void sword_soaring$gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(true, (DataProvider.Factory<SkillTreeProvider>)SwordSoaringSkillTreeProvider::new);
    }
}
