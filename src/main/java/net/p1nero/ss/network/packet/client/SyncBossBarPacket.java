package net.p1nero.ss.network.packet.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.p1nero.ss.client.gui.BossBar;
import net.p1nero.ss.network.packet.BasePacket;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * 同步客户端的uuid
 */
public record SyncBossBarPacket(UUID serverUuid, int id) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(serverUuid);
        buf.writeInt(id);
    }

    public static SyncBossBarPacket decode(FriendlyByteBuf buf) {
        return new SyncBossBarPacket(buf.readUUID(), buf.readInt());
    }

    @Override
    public void execute(@Nullable Player player) {
        BossBar.BOSSES.put(serverUuid, id);
    }
}