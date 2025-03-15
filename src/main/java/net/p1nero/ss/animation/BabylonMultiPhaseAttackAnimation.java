package net.p1nero.ss.animation;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.entity.PartEntity;
import net.p1nero.ss.capability.SSCapabilityProvider;
import net.p1nero.ss.capability.SSPlayer;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.sword.gate_of_babylon.AbstractBabylonPatch;
import net.p1nero.ss.entity.sword.sword_convergence.SwordConvergenceEntity;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Arrays;
import java.util.List;

public class BabylonMultiPhaseAttackAnimation extends ArtifactSpiritMultiPhaseAttackAnimation {

    public BabylonMultiPhaseAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
    }

    public BabylonMultiPhaseAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, accessor, armature);
    }

    public BabylonMultiPhaseAttackAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(transitionTime, accessor, armature, phases);
    }

    public BabylonMultiPhaseAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
    }

    /**
     * 自己在Capability里实现根据phase判断是否攻击过，不同phase独立判断
     */
    protected void hurtCollidingEntities(LivingEntityPatch<?> entityPatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {
        float prevPoseTime = prevState.attacking() ? prevElapsedTime : phase.preDelay;
        float poseTime = state.attacking() ? elapsedTime : phase.contact;
        List<Entity> list = phase.getCollidingEntities(entityPatch, this, prevPoseTime, poseTime, this.getPlaySpeed(entityPatch, this));

        if (!list.isEmpty()) {
            HitEntityList hitEntities = new HitEntityList(entityPatch, list, phase.getProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY).orElse(HitEntityList.Priority.DISTANCE));
            if (entityPatch instanceof AbstractBabylonPatch<?> babylonPatch && babylonPatch.getOwnerPatch() != null) {
                SSPlayer ssPlayer = babylonPatch.getOwnerPatch().getOriginal().getCapability(SSCapabilityProvider.SS_PLAYER).orElse(new SSPlayer());
                while (hitEntities.next()) {
                    Entity hit = hitEntities.getEntity();
                    LivingEntity trueEntity = this.getTrueEntity(hit);
                    if (trueEntity != null && trueEntity.isAlive() && !ssPlayer.getCurrentlyHurtEntities(phase).contains(trueEntity) && !trueEntity.is(babylonPatch.getOwnerPatch().getOriginal()) && !trueEntity.is(babylonPatch.getOriginal())) {
                        if (hit instanceof LivingEntity || hit instanceof PartEntity) {
                            EpicFightDamageSource source = this.getEpicFightDamageSource(entityPatch, hit, phase);
                            int prevInvulTime = hit.invulnerableTime;
                            hit.invulnerableTime = 0;
                            double damage = babylonPatch.getOriginal().getJointDamage(List.of(phase.colliders).get(0).getFirst());
                            //万剑不知道为何没法计算各部伤害？
                            if(entityPatch.getOriginal() instanceof SwordConvergenceEntity){
                                damage = babylonPatch.getOwnerPatch().getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE);
                            }
                            if(damage == 0){
                                continue;
                            }
                            babylonPatch.setModifiedBaseDamage((float) damage);
                            AttackResult attackResult = entityPatch.attack(source, hit, phase.hand);
                            hit.invulnerableTime = prevInvulTime;

                            if (attackResult.resultType.dealtDamage()) {
                                hit.level().playSound(null, hit.getX(), hit.getY(), hit.getZ(), this.getHitSound(entityPatch, phase), hit.getSoundSource(), 1.0F, 1.0F);
                                this.spawnHitParticle((ServerLevel) hit.level(), entityPatch, hit, phase);
                            }

                            ssPlayer.getCurrentlyHurtEntities(phase).add(trueEntity);

                            if (attackResult.resultType.shouldCount()) {
                                entityPatch.getCurrenltyHurtEntities().add(trueEntity);
                            }
                        }
                    }
                }
            }
        }
    }

}
