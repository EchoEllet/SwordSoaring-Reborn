package net.p1nero.ss.animation;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Comparator;
import java.util.List;

/**
 * 扫描时间范围内的目标，转向目标，并set最近的为Target
 */
public class PlayerScanAnimation extends AttackAnimation implements ILinkArtifactSpiritAnimation{
    private AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation;

    public PlayerScanAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
    }

    public PlayerScanAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, accessor, armature);
    }

    public PlayerScanAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(transitionTime, accessor, armature, phases);
    }

    public PlayerScanAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
    }

    public PlayerScanAnimation(float convertTime, String path, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    public PlayerScanAnimation setArtifactSpiritAnimation(AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation) {
        this.artifactSpiritAnimation = artifactSpiritAnimation;
        return this;
    }

    @Override
    public void begin(LivingEntityPatch<?> entityPatch) {
        super.begin(entityPatch);
        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
            serverPlayerPatch.setAttackTarget(null);
        }
        this.callArtifactSpiritAnimation(entityPatch);
    }

    @Override
    protected void attackTick(LivingEntityPatch<?> entityPatch, AssetAccessor<? extends DynamicAnimation> animation) {
        AnimationPlayer player = entityPatch.getAnimator().getPlayerFor(this.getAccessor());
        float elapsedTime = player.getElapsedTime();
        float prevElapsedTime = player.getPrevElapsedTime();
        EntityState state = this.getState(entityPatch, elapsedTime);
        EntityState prevState = this.getState(entityPatch, prevElapsedTime);
        Phase phase = this.getPhaseByTime(elapsedTime);

        if (state.getLevel() == 1 && !state.turningLocked()) {
            if (entityPatch instanceof MobPatch<?> mobpatch) {
                mobpatch.getOriginal().getNavigation().stop();
                entityPatch.getOriginal().attackAnim = 2;
            }
        }

        LivingEntity target = entityPatch.getTarget();
        if (target != null && elapsedTime <= phase.contact) {
            entityPatch.rotateTo(target, entityPatch.getYRotLimit(), false);
            Vec3 pos = entityPatch.getOriginal().position();
            Vec3 targetPos = target.position();
            Vec3 toTarget = targetPos.subtract(pos);
            float yRot = (float) MathUtils.getYRotOfVector(toTarget);
            float clampedYRot = MathUtils.rotlerp(entityPatch.getOriginal().getYRot(), yRot, entityPatch.getYRotLimit());
            entityPatch.getOriginal().setYRot(clampedYRot);
        }

        if (prevState.attacking() || state.attacking() || (prevState.getLevel() < 2 && state.getLevel() > 2)) {
            if (!prevState.attacking() || (phase != this.getPhaseByTime(prevElapsedTime) && (state.attacking() || (prevState.getLevel() < 2 && state.getLevel() > 2)))) {
                entityPatch.removeHurtEntities();
            }

            this.searchAndSetTarget(entityPatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        }
    }

    /**
     * 搜索框内实体并按距离设为目标
     */
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
            if (trueEntity != null && trueEntity.isAlive() && !entityPatch.isTeammate(trueEntity)) {
                if (target instanceof LivingEntity || target instanceof PartEntity) {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        serverPlayerPatch.setAttackTarget(trueEntity);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getArtifactSpiritAnimation() {
        return artifactSpiritAnimation;
    }

    @Override
    public float getConvertTime() {
        return this.transitionTime;
    }
}
