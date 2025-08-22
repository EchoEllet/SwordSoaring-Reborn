package net.p1nero.ss.capability;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.p1nero.ss.SwordSoaringMod;

import java.util.function.Supplier;

@SuppressWarnings("ALL")
@EventBusSubscriber(modid = SwordSoaringMod.MOD_ID)
public class SwordSoaringAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SwordSoaringMod.MOD_ID);
    public static final Supplier<AttachmentType<SSPlayer>> SS_PLAYER = ATTACHMENT_TYPES.register(
            "ss_player", () -> AttachmentType.builder(SSPlayer::new).build()
    );

    public static SSPlayer get(Player player){
        return player.getData(SS_PLAYER);
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath() && event.getOriginal().hasData(SS_PLAYER)) {
            event.getEntity().getData(SS_PLAYER).copyFrom(event.getOriginal().getData(SS_PLAYER));
        }
    }

}
