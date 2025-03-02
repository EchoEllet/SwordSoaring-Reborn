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
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

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
    public static StaticAnimation PLAYER_SUMMON_SWORD;

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

    public static AnimationEvent onEndPlay(StaticAnimationProvider provider) {
        return AnimationEvent.create(((livingEntityPatch, staticAnimation, objects) -> {
            livingEntityPatch.reserveAnimation(provider.get());
        }), AnimationEvent.Side.SERVER);
    }

    public static void buildScreenSwordAnim() {
        ScreenSwordArmature screenSwordArmature = SwordSoaringArmatures.screenSwordArmature;
        AttackAnimation.Phase[] phases = new AttackAnimation.Phase[]{
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W1, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W2, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W3, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W4, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W5, null),
                new ArtifactSpiritMultiPhaseAttackAnimation.MultiAttackPhase(0.01F, 0.01F, 0.5F, 0.5F, Float.MAX_VALUE, screenSwordArmature.W6, null)};

        List<Joint> joints = List.of(screenSwordArmature.W1, screenSwordArmature.W2, screenSwordArmature.W3, screenSwordArmature.W4, screenSwordArmature.W5, screenSwordArmature.W6);

        SCREEN_SWORD_IDLE = new StaticAnimation(true, "screen_sword/screen_sword_idle", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 2.0F));
        KILL_AURA_1 = new ArtifactSpiritMultiPhaseAttackAnimation(0.001F, "screen_sword/kill_aura_1", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM, spawnParticles(0.2F, joints, 5, () -> ParticleTypes.SOUL_FIRE_FLAME), spawnParticles(0.4F, joints, 5, () -> ParticleTypes.END_ROD));
        KILL_AURA_1_SUMMON = new ActionAnimation(0.15F, "screen_sword/kill_aura_1_summon", screenSwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> KILL_AURA_1));
        KILL_AURA_2 = new ArtifactSpiritMultiPhaseAttackAnimation(0.001F, "screen_sword/kill_aura_2", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> livingEntityPatch.playSound(SoundEvents.FIRE_AMBIENT, 0.0F, 0.0F), AnimationEvent.Side.SERVER))
                .addEvents(RESET_ANIM, spawnFireParticle(0.1F, joints, 5), spawnFireParticle(0.3F, joints, 5), spawnFireParticle(0.49F, joints, 5));
        KILL_AURA_2_SUMMON = new ActionAnimation(0.15F, "screen_sword/kill_aura_2_summon", screenSwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> KILL_AURA_2));
        SCREEN_SWORD = new ActionAnimation(0.001F, "screen_sword/screen_sword", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        SCREEN_SWORD_SUMMON = new ActionAnimation(0.15F, "screen_sword/screen_sword_summon", screenSwordArmature)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, onEndPlay(() -> SCREEN_SWORD));

        HumanoidArmature biped = Armatures.BIPED;
        PLAYER_SUMMON_SWORD = new ActionAnimation(0.15F, "screen_sword/sword_summon_owner", biped)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.7F, ((livingEntityPatch, staticAnimation, objects) -> {
                    VatanseverAnimations.groundSplit(livingEntityPatch, 0, 0, 0, 0, 0, 2, 500);
                }), AnimationEvent.Side.BOTH));
    }
}
