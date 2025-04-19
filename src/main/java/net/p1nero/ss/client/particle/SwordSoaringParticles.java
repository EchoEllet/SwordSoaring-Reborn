package net.p1nero.ss.client.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.ss.SwordSoaringMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SwordSoaringParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SwordSoaringMod.MOD_ID);
    public static final RegistryObject<SimpleParticleType> WRAITHON_BOOM = PARTICLES.register("wraithon_boom", () -> new SimpleParticleType(true));

    @SubscribeEvent
    public static void onRegisterParticle(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(WRAITHON_BOOM.get(), WraithonBoomParticle.WraithonBoomParticleProvider::new);
    }
}
