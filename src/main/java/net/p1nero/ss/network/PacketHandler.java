package net.p1nero.ss.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.network.packet.BasePacket;
import net.p1nero.ss.network.packet.client.SyncBabylonPacket;
import net.p1nero.ss.network.packet.client.SyncBossBarPacket;
import net.p1nero.ss.network.packet.server.RequestBabylonSyncPacket;
import net.p1nero.ss.network.packet.server.RequestEntityPlayAnimationPacket;
import net.p1nero.ss.network.packet.server.RequestVatanseverSwordBackPacket;

import java.util.function.Function;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    private static int index;

    public static synchronized void register() {
        register(RequestEntityPlayAnimationPacket.class, RequestEntityPlayAnimationPacket::decode);
        register(RequestVatanseverSwordBackPacket.class, RequestVatanseverSwordBackPacket::decode);
        register(RequestBabylonSyncPacket.class, RequestBabylonSyncPacket::decode);

        register(SyncBabylonPacket.class, SyncBabylonPacket::decode);
        register(SyncBossBarPacket.class, SyncBossBarPacket::decode);
    }

    private static <MSG extends BasePacket> void register(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
        INSTANCE.messageBuilder(packet, index++).encoder(BasePacket::encode).decoder(decoder).consumerMainThread(BasePacket::handle).add();
    }
}
