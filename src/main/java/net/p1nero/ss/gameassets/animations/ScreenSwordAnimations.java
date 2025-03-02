package net.p1nero.ss.gameassets.animations;

import com.p1nero.invincible.api.animation.StaticAnimationProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.animation.ArtifactSpiritMultiPhaseAttackAnimation;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.util.AnimationUtils;
import net.p1nero.ss.util.vfx.ParticleVFX;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;

import java.util.List;
import java.util.function.Supplier;

public class ScreenSwordAnimations {
    public static StaticAnimation SCREEN_SWORD_IDLE;
    public static StaticAnimation KILL_AURA_1;
    public static StaticAnimation KILL_AURA_1_SUMMON;
    public static StaticAnimation KILL_AURA_2;
    public static StaticAnimation KILL_AURA_2_SUMMON;
    public static StaticAnimation SCREEN_SWORD;
    public static StaticAnimation SCREEN_SWORD_SUMMON;
    public static StaticAnimation PLAYER_SUMMON_SCREEN_SWORD;
    public static StaticAnimation PLAYER_SUMMON_KILL_AURA_1;
    public static StaticAnimation PLAYER_SUMMON_KILL_AURA_2;

    public static AnimationEvent.TimeStampedEvent RESET_ANIM = AnimationEvent.TimeStampedEvent.create(0.49F, ((livingEntityPatch, staticAnimation, objects) -> {
        livingEntityPatch.reserveAnimation(staticAnimation);
    }), AnimationEvent.Side.SERVER);

    public static AnimationEvent.TimeStampedEvent spawnParticles(float time, List<Joint> joints, int count, Supplier<ParticleOptions> particleOptions) {
        return AnimationEvent.TimeStampedEvent.create(time, ((livingEntityPatch, staticAnimation, objects) -> {
            for (Joint joint : joints) {
                Vec3 worldPos = AnimationUtils.getJointWorldPos(livingEntityPatch, joint);
                for (int i = 0; i < count; i++) {
                    ((ServerLevel) livingEntityPatch.getOriginal().level).sendParticles(particleOptions.get(), worldPos.x, worldPos.y, worldPos.z, count, 0, 0, 0, 0.1);
                }
            }
        }), AnimationEvent.Side.SERVER);
    }

    public static AnimationEvent.TimeStampedEvent spawnFireParticle(float time, List<Joint> joints, int count) {
        return spawnParticles(time, joints, count, () -> ParticleTypes.FLAME);
    }

    public static AnimationEvent.TimeStampedEvent spawnHexagram(float time, Supplier<ParticleOptions> particleOptions, float interval, float radius) {
        return AnimationEvent.TimeStampedEvent.create(time, ((livingEntityPatch, staticAnimation, objects) -> ParticleVFX.createHexagramParticle(particleOptions.get(), livingEntityPatch.getOriginal().level, livingEntityPatch.getOriginal().position().add(0, 1.0F, 0), interval, radius, time / staticAnimation.getTotalTime() * 360)), AnimationEvent.Side.CLIENT);
    }

    public static AnimationEvent onEndPlay(StaticAnimationProvider provider) {
        return AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> {
            livingEntityPatch.reserveAnimation(provider.get());
        }), AnimationEvent.Side.SERVER);
    }

    public static AnimationEvent SET_GLOWING = AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.getOriginal().setGlowingTag(true)), AnimationEvent.Side.SERVER);

    public static void buildScreenSwordAnim() {
        ScreenSwordArmature screenSwordArmature = SwordSoaringArmatures.screenSwordArmature;

        SCREEN_SWORD_IDLE = new StaticAnimation(true, "screen_sword/screen_sword_idle", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 2.0F));
        KILL_AURA_1 = new ArtifactSpiritMultiPhaseAttackAnimation(0.001F, "screen_sword/kill_aura_1", screenSwordArmature, AnimationUtils.getPhases(screenSwordArmature.joints, 0.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM, spawnHexagram(0.15F, () -> ParticleTypes.WAX_ON, 0.2F, 4), spawnHexagram(0.25F, () -> ParticleTypes.END_ROD, 0.2F, 2), spawnHexagram(0.35F, () -> ParticleTypes.WAX_OFF, 0.2F, 4));
        KILL_AURA_1_SUMMON = new ActionAnimation(0.15F, "screen_sword/kill_aura_1_summon", screenSwordArmature)
                .addEvents(spawnHexagram(0.15F, () -> ParticleTypes.WAX_ON, 0.2F, 0.5F), spawnHexagram(0.25F, () -> ParticleTypes.END_ROD, 0.2F, 1), spawnHexagram(0.35F, () -> ParticleTypes.WAX_OFF, 0.2F, 2))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, SET_GLOWING)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> KILL_AURA_1));
        KILL_AURA_2 = new ArtifactSpiritMultiPhaseAttackAnimation(0.001F, "screen_sword/kill_aura_2", screenSwordArmature, AnimationUtils.getPhases(screenSwordArmature.joints, 0.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.playSound(SoundEvents.FIRE_AMBIENT, 0.0F, 0.0F), AnimationEvent.Side.SERVER))
                .addEvents(RESET_ANIM, spawnFireParticle(0.1F, screenSwordArmature.joints, 5), spawnParticles(0.3F, screenSwordArmature.joints, 5, EpicFightParticles.BLOOD::get), spawnFireParticle(0.49F, screenSwordArmature.joints, 5));
        KILL_AURA_2_SUMMON = new ActionAnimation(0.15F, "screen_sword/kill_aura_2_summon", screenSwordArmature)
                .addEvents(spawnFireParticle(0.1F, screenSwordArmature.joints, 1), spawnParticles(0.3F, screenSwordArmature.joints, 2, EpicFightParticles.BLOOD::get), spawnFireParticle(0.49F, screenSwordArmature.joints, 2))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> KILL_AURA_2));
        SCREEN_SWORD = new ActionAnimation(0.001F, "screen_sword/screen_sword", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        SCREEN_SWORD_SUMMON = new ActionAnimation(0.15F, "screen_sword/screen_sword_summon", screenSwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, SET_GLOWING)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> SCREEN_SWORD));

        HumanoidArmature biped = Armatures.BIPED;
        PLAYER_SUMMON_SCREEN_SWORD = new ActionAnimation(0.15F, "screen_sword/screen_sword_summon_player", biped)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.7F, ((livingEntityPatch, staticAnimation, objects) -> {
                    VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, 0, 2, 0);
                    ParticleVFX.createSphereParticles(livingEntityPatch.getOriginal().level, livingEntityPatch.getOriginal().getEyePosition(), ParticleTypes.END_ROD, 5, 0.1, 0.2, 10);
                }), AnimationEvent.Side.BOTH));
        PLAYER_SUMMON_KILL_AURA_1 = new ActionAnimation(0.15F, "screen_sword/kill_aura_1_summon_player", biped)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.7F, ((livingEntityPatch, staticAnimation, objects) -> {
                    VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, 0, 2, 0);
                    ParticleVFX.createSphereParticles(livingEntityPatch.getOriginal().level, livingEntityPatch.getOriginal().getEyePosition(), ParticleTypes.SOUL_FIRE_FLAME, 5, 0.1, 0.2, 10);
                }), AnimationEvent.Side.BOTH));
        PLAYER_SUMMON_KILL_AURA_2 = new ActionAnimation(0.15F, "screen_sword/kill_aura_2_summon_player", biped)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.7F, ((livingEntityPatch, staticAnimation, objects) -> {
                    VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, 0, 2, 0);
                    ParticleVFX.createSphereParticles(livingEntityPatch.getOriginal().level, livingEntityPatch.getOriginal().getEyePosition(), ParticleTypes.FLAME, 5, 0.1, 0.2, 10);
                }), AnimationEvent.Side.BOTH));
    }
}
