package net.p1nero.ss.client.keymapping;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = {Dist.CLIENT},bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwordSoaringKeyMappings {
    public static final KeyMapping TAKE_OFF = new KeyMapping("key.sword_soaring.take_off", GLFW.GLFW_KEY_TAB, "key.sword_soaring.common");
    public static final KeyMapping ACCELERATION = new KeyMapping("key.sword_soaring.acceleration", GLFW.GLFW_KEY_TAB, "key.sword_soaring.common");

    @SubscribeEvent
    public static void registerKeys(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(TAKE_OFF);
        ClientRegistry.registerKeyBinding(ACCELERATION);
    }

}
