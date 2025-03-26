package net.p1nero.ss.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.ss.SwordSoaringMod;

import java.util.function.Supplier;

public class SwordSoaringBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, SwordSoaringMod.MOD_ID);

    public static final Supplier<Block> MELIN_DD = BLOCKS.register("merlin_dd", MerlinDDBlock::new);

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
