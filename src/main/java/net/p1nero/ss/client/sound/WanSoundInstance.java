package net.p1nero.ss.client.sound;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkill;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkillElytra;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;


@OnlyIn(Dist.CLIENT)
public class WanSoundInstance extends AbstractTickableSoundInstance {
    private final LocalPlayer player;
    private int time;

    public WanSoundInstance(LocalPlayer pPlayer) {
        super(SwordSoaringSounds.SWORD_CONVERGENCE.get(), SoundSource.PLAYERS);
        this.player = pPlayer;
        this.looping = true;
        this.delay = 0;
        this.volume = 1F;
    }

    public void tick() {
        ++this.time;
        LocalPlayerPatch localPlayerPatch = EpicFightCapabilities.getEntityPatch(player, LocalPlayerPatch.class);
        if(localPlayerPatch == null ){
            this.stop();
            return;
        }
    }
}