package net.p1nero.ss.network.packet.server;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p1nero.ss.SwordSoaringMod;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public record RequestEntityPlayAnimationPacket(int entityId, int animationId, float modifyTime) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestEntityPlayAnimationPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "requestentityplayanimationpacket"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestEntityPlayAnimationPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            RequestEntityPlayAnimationPacket::entityId,
            ByteBufCodecs.INT,
            RequestEntityPlayAnimationPacket::animationId,
            ByteBufCodecs.FLOAT,
            RequestEntityPlayAnimationPacket::modifyTime,
            RequestEntityPlayAnimationPacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void execute(RequestEntityPlayAnimationPacket packet, IPayloadContext context) {
        if(context.player() instanceof ServerPlayer player){
            Entity entity = player.level().getEntity(packet.entityId);
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            if(entityPatch != null){
                entityPatch.playAnimationSynchronized(AnimationManager.byId(packet.animationId), packet.modifyTime);
            }
        }
    }
}
