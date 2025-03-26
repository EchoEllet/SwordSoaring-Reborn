package net.p1nero.ss.block;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MerlinDDBlock extends Block {
    public MerlinDDBlock() {
        super(Properties.copy(Blocks.STONE).strength(5f));
    }
}
