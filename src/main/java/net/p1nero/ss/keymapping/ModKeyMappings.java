package net.p1nero.ss.keymapping;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = {Dist.CLIENT},bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModKeyMappings {
    public static final MyKeyMapping TAKE_OFF = new MyKeyMapping("key.sword_soaring.take_off", GLFW.GLFW_KEY_SPACE, "key.sword_soaring.common");

    @SubscribeEvent
    public static void registerKeys(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(TAKE_OFF);
    }

}
