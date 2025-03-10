package net.p1nero.ss.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.skill.sword_controller.WanJianGuiZongSkill;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

@OnlyIn(Dist.CLIENT)
public class WanSoundInstance extends AbstractTickableSoundInstance {
    private final LocalPlayerPatch playerPatch;
    private int time = 0;

    public WanSoundInstance(LocalPlayerPatch pPlayer) {
        super(SwordSoaringSounds.SWORD_CONVERGENCE.get(), SoundSource.PLAYERS);
        this.playerPatch = pPlayer;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.5F;
    }

    public void tick() {
        ++this.time;
        if(playerPatch.getSkill(SwordSoaringSkillSlots.SWORD_CONTROLLER).getDataManager().getDataValue(WanJianGuiZongSkill.IS_CHARGING)){
            this.time = 120;
        }
        if(this.time > 140 && this.time < 160){
            this.volume = 0.5F + (this.time - 140) / 10.0F;
        }
        if(this.time >= 160) {
            this.volume = 2.5F - (this.time - 160) / 20.0F;
            if(this.volume < 0){
                stop();
            }
        }
    }
}