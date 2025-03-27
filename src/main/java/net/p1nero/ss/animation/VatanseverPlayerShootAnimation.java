package net.p1nero.ss.animation;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.entity.PartEntity;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Comparator;
import java.util.List;

public class VatanseverPlayerShootAnimation extends PlayerScanAnimation{


    public VatanseverPlayerShootAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
    }

    public VatanseverPlayerShootAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, accessor, armature);
    }

    public VatanseverPlayerShootAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(transitionTime, accessor, armature, phases);
    }

    public VatanseverPlayerShootAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
    }

    public VatanseverPlayerShootAnimation(float convertTime, String path, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    @Override
    protected void searchAndSetTarget(LivingEntityPatch<?> entityPatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {
        float prevPoseTime = prevState.attacking() ? prevElapsedTime : phase.preDelay;
        float poseTime = state.attacking() ? elapsedTime : phase.contact;
        List<Entity> list = this.getPhaseByTime(elapsedTime).getCollidingEntities(entityPatch, this, prevPoseTime, poseTime, this.getPlaySpeed(entityPatch, this));
        if (list.contains(entityPatch.getTarget())) {
            return;
        }
        list.sort(Comparator.comparingDouble((entity) -> entity.distanceTo(entityPatch.getOriginal())));
        for(Entity target : list){
            LivingEntity trueEntity = this.getTrueEntity(target);
            if (trueEntity != null && trueEntity.isAlive() && !entityPatch.isTargetInvulnerable(trueEntity)) {
                if (target instanceof LivingEntity || target instanceof PartEntity) {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        if(target instanceof AbstractArtifactSpiritEntity artifactSpiritEntity && serverPlayerPatch.getOriginal().equals(artifactSpiritEntity.getOwner())){
                            continue;
                        }
                        SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                        VatanseverEntityPatch vatanseverEntityPatch = EpicFightCapabilities.getEntityPatch(serverPlayerPatch.getOriginal().level().getEntity(manager.getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get())), VatanseverEntityPatch.class);
                        if(vatanseverEntityPatch != null){
                            vatanseverEntityPatch.setAttakTargetSync(trueEntity);
                            break;
                        }
                    }
                }
            }
        }
    }
}
