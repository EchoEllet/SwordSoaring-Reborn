package net.p1nero.ss.network.packet.server;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.capability.SSPlayer;
import net.p1nero.ss.capability.SwordSoaringAttachments;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public record RequestVatanseverSwordBackPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestVatanseverSwordBackPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "requestvatanseverswordbackpacket"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestVatanseverSwordBackPacket> STREAM_CODEC = StreamCodec.unit(new RequestVatanseverSwordBackPacket());

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void execute(RequestVatanseverSwordBackPacket packet, IPayloadContext context) {
        if(context.player() instanceof ServerPlayer player){
            SSPlayer ssPlayer = player.getData(SwordSoaringAttachments.SS_PLAYER);
            Iterator<FlySwordEntity> iterator = ssPlayer.getVatanseverShootEntities().iterator();
            while (iterator.hasNext()){
                FlySwordEntity flySwordEntity = iterator.next();
                if(flySwordEntity != null && flySwordEntity.isAlive()){
                    if(flySwordEntity.callFlyingBack()){
                        iterator.remove();
                        break;
                    }
                } else {
                    iterator.remove();
                }
            }
        }
    }
}
