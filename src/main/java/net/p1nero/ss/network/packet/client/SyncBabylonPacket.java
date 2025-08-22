package net.p1nero.ss.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.capability.SwordSoaringAttachments;
import net.p1nero.ss.entity.sword.gate_of_babylon.BabylonEntity;
import net.p1nero.ss.utils.ByteBufCodecsExtends;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public record SyncBabylonPacket(int id, ArrayList<ItemStack> itemStacks) implements CustomPacketPayload {

    public static final Type<SyncBabylonPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "sync_babylon_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncBabylonPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SyncBabylonPacket::id,
            ByteBufCodecsExtends.ITEM_STACKS,
            SyncBabylonPacket::itemStacks,
            SyncBabylonPacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void execute(SyncBabylonPacket packet, IPayloadContext context) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().level != null){
            Entity entity = Minecraft.getInstance().level.getEntity(packet.id);
            if(entity instanceof Player localPlayer){
                localPlayer.getData(SwordSoaringAttachments.SS_PLAYER).updateBabylonItems(packet.itemStacks);
            }
            if(entity instanceof BabylonEntity babylonEntity){
                babylonEntity.updateBabylonItems(packet.itemStacks);
            }
        }
    }
}
