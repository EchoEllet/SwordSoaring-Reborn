package net.p1nero.ss.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;

public class SwordSoaringEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SwordSoaring.MOD_ID);
    public static final RegistryObject<EntityType<FlySwordEntity>> FLY_SWORD = register("fly_sword",
            EntityType.Builder.<FlySwordEntity>of(FlySwordEntity::new, MobCategory.MISC).sized(1, 1).clientTrackingRange(20).updateInterval(1).noSave());
    public static final RegistryObject<EntityType<ScreenSwordEntity>> SCREEN_SWORD = register("screen_sword",
            EntityType.Builder.<ScreenSwordEntity>of(ScreenSwordEntity::new, MobCategory.MISC).sized(0, 0).clientTrackingRange(20).updateInterval(1).noSave());
    public static final RegistryObject<EntityType<VatanseverEntity>> VATANSEVER = register("vatansever",
            EntityType.Builder.<VatanseverEntity>of(VatanseverEntity::new, MobCategory.MISC).sized(0, 0).clientTrackingRange(64).updateInterval(1).noSummon().noSave());
    public static final RegistryObject<EntityType<VatanseverStormEntity>> VATANSEVER_STORM = register("vatansever_storm",
            EntityType.Builder.<VatanseverStormEntity>of(VatanseverStormEntity::new, MobCategory.MISC).sized(0, 0).clientTrackingRange(64).updateInterval(1).noSave());
    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
        return ENTITIES.register(name, () -> entityTypeBuilder.build(new ResourceLocation(SwordSoaring.MOD_ID, name).toString()));
    }

}
