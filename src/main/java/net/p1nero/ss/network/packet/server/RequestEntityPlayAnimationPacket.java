package net.p1nero.ss.network.packet.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.p1nero.ss.network.packet.BasePacket;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public record RequestEntityPlayAnimationPacket(int entityId, int animationId, float modifyTime) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(animationId);
        buf.writeFloat(modifyTime);
    }
    public static RequestEntityPlayAnimationPacket decode(FriendlyByteBuf buf){
        return new RequestEntityPlayAnimationPacket(buf.readInt(), buf.readInt(), buf.readFloat());
    }

    @Override
    public void execute(@Nullable Player player) {
        if(player != null){
            Entity entity = player.level().getEntity(entityId);
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            if(entityPatch != null){
                entityPatch.playAnimationSynchronized(AnimationManager.byId(animationId), modifyTime);
            }
        }
    }
}
