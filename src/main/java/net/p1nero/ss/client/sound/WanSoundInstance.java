package net.p1nero.ss.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;


@OnlyIn(Dist.CLIENT)
public class WanSoundInstance extends AbstractTickableSoundInstance {
    private final LocalPlayerPatch playerPatch;
    private int time;

    public WanSoundInstance(LocalPlayerPatch pPlayer) {
        super(SwordSoaringSounds.SWORD_CONVERGENCE.get(), SoundSource.PLAYERS);
        this.playerPatch = pPlayer;
        this.looping = true;
        this.delay = 0;
        this.volume = 1F;
    }

    public void tick() {
        ++this.time;
        if(playerPatch == null ){
            this.stop();
            //TODO 渐隐
        }
    }
}