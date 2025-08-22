package net.p1nero.ss.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public interface ByteBufCodecsExtends {
    StreamCodec<RegistryFriendlyByteBuf, ArrayList<ItemStack>> ITEM_STACKS = new StreamCodec<>() {
        public void encode(RegistryFriendlyByteBuf buf, ArrayList<ItemStack> tags) {
            buf.writeInt(tags.size());
            tags.forEach(itemStack -> {
                buf.writeNbt(itemStack.save(buf.registryAccess()));
            });
        }

        public @NotNull ArrayList<ItemStack> decode(RegistryFriendlyByteBuf buf) {
            int size = buf.readInt();
            ArrayList<ItemStack> tags = new ArrayList<>();
            for(int i = 0; i < size; i++){
                CompoundTag compoundTag = buf.readNbt();
                if(compoundTag != null) {
                    ItemStack.parse(buf.registryAccess(), compoundTag).ifPresent(tags::add);
                }
            }
            return tags;
        }
    };
}
