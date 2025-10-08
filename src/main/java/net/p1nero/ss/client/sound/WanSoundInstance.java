package net.p1nero.ss.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillDataManager;

@OnlyIn(Dist.CLIENT)
public class WanSoundInstance extends AbstractTickableSoundInstance {
    private final LocalPlayerPatch playerPatch;
    private int time = 0;

    public WanSoundInstance(LocalPlayerPatch localPlayerPatch) {
        super(SwordSoaringSounds.WAN_GATHERING.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.playerPatch = localPlayerPatch;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.5F;
    }

    @Override
    public void tick() {
        ++this.time;
        if(playerPatch == null){
            stop();
            return;
        }
        SkillDataManager manager = playerPatch.getSkill(SwordSoaringSkillSlots.SWORD_CONTROLLER).getDataManager();
        if(!manager.hasData(SwordSoaringDatakeys.IS_CHARGING)){
            stop();
            return;
        }
        if(manager.getDataValue(SwordSoaringDatakeys.IS_CHARGING)){
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