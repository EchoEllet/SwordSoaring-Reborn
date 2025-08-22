package net.p1nero.ss.events;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.fly_sword.client.FlySwordRenderer;
import net.p1nero.ss.entity.sword.fly_sword.client.PatchedFlySwordRenderer;
import net.p1nero.ss.entity.sword.gate_of_babylon.client.BabylonRenderer;
import net.p1nero.ss.entity.sword.gate_of_babylon.client.PatchedBabylonRenderer;
import net.p1nero.ss.entity.sword.screen_sword.client.PatchedScreenSwordRenderer;
import net.p1nero.ss.entity.sword.screen_sword.client.ScreenSwordRenderer;
import net.p1nero.ss.entity.sword.wan.client.PatchedWanRenderer;
import net.p1nero.ss.entity.sword.wan.client.WanRenderer;
import net.p1nero.ss.entity.vatansever.client.PatchedVatanseverRenderer;
import net.p1nero.ss.entity.vatansever.client.VatanseverRenderer;
import net.p1nero.ss.entity.vatansever_storm.client.PatchedVatanseverStormRenderer;
import net.p1nero.ss.entity.vatansever_storm.client.VatanseverStormRenderer;
import net.p1nero.ss.gameassets.SwordSoaringSkillCategories;
import yesman.epicfight.api.client.neoevent.PatchedRenderersEvent;
import yesman.epicfight.registry.entries.EpicFightItems;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.world.item.SkillBookItem;

import java.util.Optional;

@EventBusSubscriber(modid = SwordSoaringMod.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(SwordSoaringEntities.WAN_ENTITY.get(), WanRenderer::new);
        EntityRenderers.register(SwordSoaringEntities.BABYLON.get(), BabylonRenderer::new);
        EntityRenderers.register(SwordSoaringEntities.FLY_SWORD.get(), FlySwordRenderer::new);
        EntityRenderers.register(SwordSoaringEntities.SCREEN_SWORD.get(), ScreenSwordRenderer::new);
        EntityRenderers.register(SwordSoaringEntities.VATANSEVER.get(), VatanseverRenderer::new);
        EntityRenderers.register(SwordSoaringEntities.VATANSEVER_STORM.get(), VatanseverStormRenderer::new);

        ItemProperties.register(EpicFightItems.SKILLBOOK.get(), ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID,"skill"), (pStack, pLevel, pEntity, pSeed) -> {
            Optional<Holder<Skill>> optionalSkillHolder = SkillBookItem.getContainSkill(pStack);
            if(optionalSkillHolder.isEmpty()) {
                return 0;
            }
            Skill skill = optionalSkillHolder.get().value();
            if (skill.getCategory() == SkillCategories.GUARD) {
                return 1;
            } else if (skill.getCategory() == SkillCategories.PASSIVE) {
                return 2;
            } else if (skill.getCategory() == SkillCategories.DODGE) {
                return 3;
            } else if (skill.getCategory() == SkillCategories.IDENTITY) {
                return 4;
            } else if (skill.getCategory() == SkillCategories.MOVER) {
                return 5;
            } else if (skill.getCategory() == SwordSoaringSkillCategories.SWORD_SOARING) {
                return 6;
            } else if (skill.getCategory() == SwordSoaringSkillCategories.SWORD_CONTROLLER) {
                return 7;
            }

            return 0;
        });
    }

    @SubscribeEvent
    public static void onPatchedRenderer(PatchedRenderersEvent.Add event){
        event.addPatchedEntityRenderer(SwordSoaringEntities.WAN_ENTITY.get(), entityType -> new PatchedWanRenderer(event.getContext(), entityType).initLayerLast(event.getContext(), entityType));
        event.addPatchedEntityRenderer(SwordSoaringEntities.BABYLON.get(), entityType -> new PatchedBabylonRenderer<>(event.getContext(), entityType).initLayerLast(event.getContext(), entityType));
        event.addPatchedEntityRenderer(SwordSoaringEntities.FLY_SWORD.get(), entityType -> new PatchedFlySwordRenderer<>(event.getContext(), entityType).initLayerLast(event.getContext(), entityType));
        event.addPatchedEntityRenderer(SwordSoaringEntities.SCREEN_SWORD.get(),entityType -> new PatchedScreenSwordRenderer<>(event.getContext(), entityType).initLayerLast(event.getContext(), entityType));
        event.addPatchedEntityRenderer(SwordSoaringEntities.VATANSEVER.get(), entityType -> new PatchedVatanseverRenderer(event.getContext(), entityType).initLayerLast(event.getContext(), entityType));
        event.addPatchedEntityRenderer(SwordSoaringEntities.VATANSEVER_STORM.get(), entityType -> new PatchedVatanseverStormRenderer(event.getContext(), entityType).initLayerLast(event.getContext(), entityType));
    }

}