package net.p1nero.ss.client.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p1nero.ss.SwordSoaringMod;

public class SwordSoaringSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, SwordSoaringMod.MOD_ID);

    public static DeferredHolder<SoundEvent, SoundEvent> VATANSEVER_WHOOSH = registerSoundEvent("vatansever_whoosh");
    public static DeferredHolder<SoundEvent, SoundEvent> VATANSEVER_WHOOSH_BIG = registerSoundEvent("vatansever_whoosh_big");
    public static DeferredHolder<SoundEvent, SoundEvent> VATANSEVER_STORM = registerSoundEvent("vatansever_storm");
    public static DeferredHolder<SoundEvent, SoundEvent> WAN_GATHERING = registerSoundEvent("sword_convergence");
    public static DeferredHolder<SoundEvent, SoundEvent> NO_SOUND = registerSoundEvent("no_sound");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, name)));
    }

}
