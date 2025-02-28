package net.p1nero.ss.gameassets;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.sword.fly_sword.client.FlySwordMesh;
import net.p1nero.ss.entity.sword.screen_sword.client.ScreenSwordMesh;
import net.p1nero.ss.entity.vatansever.client.VatanseverMesh;
import net.p1nero.ss.entity.vatansever_storm.client.VatanseverStormMesh;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SwordSoaringMeshes {
    public static FlySwordMesh flySwordMesh;
    public static ScreenSwordMesh screenSwordMesh;
    public static VatanseverMesh vatanseverMesh;
    public static VatanseverStormMesh vatanseverStormMesh;
    @SubscribeEvent
    public static void build(ModelBuildEvent.MeshBuild event) {
        flySwordMesh = event.getAnimated(SwordSoaring.MOD_ID, "entity/fly_sword", FlySwordMesh::new);
        screenSwordMesh = event.getAnimated(SwordSoaring.MOD_ID, "entity/screen_sword", ScreenSwordMesh::new);
        vatanseverMesh = event.getAnimated(SwordSoaring.MOD_ID, "entity/vatansever", VatanseverMesh::new);
        vatanseverStormMesh = event.getAnimated(SwordSoaring.MOD_ID, "entity/vatansever_swordgroup", VatanseverStormMesh::new);
    }
}