package net.p1nero.ss.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.p1nero.ss.capability.SSCapabilityProvider;
import net.p1nero.ss.network.packet.BasePacket;
import org.jetbrains.annotations.Nullable;

public record RequestValidBabylonSyncPacket() implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
    }
    public static RequestValidBabylonSyncPacket decode(FriendlyByteBuf buf){
        return new RequestValidBabylonSyncPacket();
    }

    @Override
    public void execute(@Nullable Player player) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().level != null){
            Minecraft.getInstance().player.getCapability(SSCapabilityProvider.SS_PLAYER).ifPresent(ssPlayer -> {
                ssPlayer.calculateValidBabylonItems(Minecraft.getInstance().player);
            });
        }
    }
}
