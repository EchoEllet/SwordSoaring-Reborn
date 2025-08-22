package net.p1nero.ss.data;

import com.yesman.epicskills.common.data.SkillTreeProvider;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.p1nero.ss.SwordSoaringMod;

@EventBusSubscriber(modid = SwordSoaringMod.MOD_ID)
public final class DataEvents {

    @SubscribeEvent
    public static void sword_soaring$gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(true, (DataProvider.Factory<SkillTreeProvider>)SwordSoaringSkillTreeProvider::new);
    }
}
