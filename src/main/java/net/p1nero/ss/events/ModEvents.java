package net.p1nero.ss.events;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.gameasset.Armatures;

@Mod.EventBusSubscriber(modid = SwordSoaring.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents{

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(SwordSoaringEntities.VATANSEVER.get(), AbstractArtifactSpiritEntity.getDefaultAttribute());
        event.put(SwordSoaringEntities.VATANSEVER_STORM.get(), AbstractArtifactSpiritEntity.getDefaultAttribute());
    }

    @SubscribeEvent
    public static void setPatch(EntityPatchRegistryEvent event) {
        event.getTypeEntry().put(SwordSoaringEntities.VATANSEVER.get(), (entity) -> VatanseverEntityPatch::new);
        event.getTypeEntry().put(SwordSoaringEntities.VATANSEVER_STORM.get(), (entity) -> VatanseverStormEntityPatch::new);
    }

    @SubscribeEvent
    public static void setArmature(ModelBuildEvent.ArmatureBuild event) {
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.VATANSEVER.get(), SwordSoaringArmatures.vatanseverArmature);
        Armatures.registerEntityTypeArmature(SwordSoaringEntities.VATANSEVER_STORM.get(), SwordSoaringArmatures.vatanseverStormArmature);
    }

}