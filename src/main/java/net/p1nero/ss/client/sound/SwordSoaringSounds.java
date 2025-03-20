package net.p1nero.ss.client.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.ss.SwordSoaringMod;

public class SwordSoaringSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SwordSoaringMod.MOD_ID);

    public static RegistryObject<SoundEvent> VATANSEVER_WHOOSH = registerSoundEvent("vatansever_whoosh");
    public static RegistryObject<SoundEvent> VATANSEVER_WHOOSH_BIG = registerSoundEvent("vatansever_whoosh_big");
    public static RegistryObject<SoundEvent> VATANSEVER_STORM = registerSoundEvent("vatansever_storm");
    public static RegistryObject<SoundEvent> WAN_GATHERING = registerSoundEvent("wan");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SwordSoaringMod.MOD_ID, name)));
    }

}
