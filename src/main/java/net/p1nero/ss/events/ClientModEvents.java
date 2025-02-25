package net.p1nero.ss.events;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.client.SwordEntityRenderer;
import net.p1nero.ss.entity.vatansever.client.PatchedVatanseverRenderer;
import net.p1nero.ss.entity.vatansever.client.VatanseverRenderer;
import net.p1nero.ss.entity.vatansever_storm.client.PatchedVatanseverStormRenderer;
import net.p1nero.ss.entity.vatansever_storm.client.VatanseverStormRenderer;
import net.p1nero.ss.item.SwordSoaringItems;
import net.p1nero.ss.item.client.RenderVatansever;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(SwordSoaringEntities.SCREEN_SWORD.get(), SwordEntityRenderer::new);
        EntityRenderers.register(SwordSoaringEntities.VATANSEVER.get(), VatanseverRenderer::new);
        EntityRenderers.register(SwordSoaringEntities.VATANSEVER_STORM.get(), VatanseverStormRenderer::new);
    }

    @SubscribeEvent
    public static void onRenderItem(final PatchedRenderersEvent.Add event) {
        event.addItemRenderer(SwordSoaringItems.VATANSEVER.get(), new RenderVatansever());
    }

    @SubscribeEvent
    public static void onPatchedRenderer(PatchedRenderersEvent.Add event){
        event.addPatchedEntityRenderer(SwordSoaringEntities.VATANSEVER.get(), PatchedVatanseverRenderer::new);
        event.addPatchedEntityRenderer(SwordSoaringEntities.VATANSEVER_STORM.get(), PatchedVatanseverStormRenderer::new);
    }

}