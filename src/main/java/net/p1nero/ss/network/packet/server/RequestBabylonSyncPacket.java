package net.p1nero.ss.network.packet.server;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.network.packet.client.SyncBabylonPacket;
import org.jetbrains.annotations.NotNull;

public record RequestBabylonSyncPacket(int entityId) implements CustomPacketPayload {

    public static final Type<RequestBabylonSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "request_babylon_sync_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestBabylonSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            RequestBabylonSyncPacket::entityId,
            RequestBabylonSyncPacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void execute(RequestBabylonSyncPacket packet, IPayloadContext context) {
        Player player = context.player();
        if(player instanceof ServerPlayer serverPlayer){
            Entity entity = player.level().getEntity(packet.entityId);
            if(entity instanceof BabylonEntity babylonEntity){
                PacketDistributor.sendToPlayer(serverPlayer, new SyncBabylonPacket(babylonEntity.getId(), babylonEntity.getValidBabylonItems()));
            }
        }
    }
}
