package net.p1nero.ss.gameassets.animations;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.wraithon.WraithonArmature;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import net.p1nero.ss.util.AnimationUtils;
import net.p1nero.ss.util.vfx.ParticleVFX;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class WraithonAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> WRAITHON_WALK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_ROTATE_R;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WRAITHON_ROTATE_L;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> WRAITHON_1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> WRAITHON_3;

    public static final List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> DEBUG_ANIM_LIST = new ArrayList<>();

    public static void buildWraithonAnim(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<WraithonArmature> armature = SwordSoaringArmatures.WRAITHON_ARMATURE;

        Supplier<AttackAnimation.JointColliderPair[]> supplier = ()-> {
            List<AttackAnimation.JointColliderPair> atkJoints = List.of(AttackAnimation.JointColliderPair.of(armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK_1),
                    AttackAnimation.JointColliderPair.of(armature.get().weapon, SwordSoaringColliders.WRAITHON_BASIC_ATTACK_2));
            return atkJoints.toArray(new AttackAnimation.JointColliderPair[0]);
        };

        WRAITHON_IDLE = builder.nextAccessor("wraithon/wraithon_idle", (accessor -> new StaticAnimation(true, accessor, armature)
                .addEvents(AnimationEvent.InPeriodEvent.create(0, 3, (entityPatch, self, params) ->{
                } , AnimationEvent.Side.CLIENT))));
        WRAITHON_WALK = builder.nextAccessor("wraithon/wraithon_walk", (accessor -> new StaticAnimation(true, accessor, armature)));
        WRAITHON_ROTATE_R = builder.nextAccessor("wraithon/wraithon_rotate_r", (accessor -> new ActionAnimation(0.15F, accessor, armature)));
        WRAITHON_ROTATE_L = builder.nextAccessor("wraithon/wraithon_rotate_l", (accessor -> new ActionAnimation(0.15F, accessor, armature)));

        WRAITHON_1 = builder.nextAccessor("wraithon/wraithon_attack_1", (accessor -> new BasicAttackAnimation(0.15F, accessor, armature,
                new AttackAnimation.Phase(0.0F, 1, 1, 1.25F, 1.25F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, supplier.get()),
                new AttackAnimation.Phase(1.25F, 2.16F, 2.16F, 2.42F, 2.42F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, supplier.get()))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.FINISHER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1))
                .addEvents(
                        AnimationEvent.InPeriodEvent.create(1.218F, 1.25F, (entityPatch, self, params) ->{
                            Entity entity = entityPatch.getOriginal();
                            wraithonGroundSplit(entityPatch,3,100);
                            } , AnimationEvent.Side.BOTH),
                        AnimationEvent.InPeriodEvent.create(2.38F, 2.42F, (entityPatch, self, params) ->{
                            Entity entity = entityPatch.getOriginal();
                            wraithonGroundSplit(entityPatch,3,100);
                            } , AnimationEvent.Side.BOTH))));
        WRAITHON_3 = builder.nextAccessor("wraithon/wraithon_attack_3", (accessor -> new BasicAttackAnimation(0.15F, accessor, armature,
                new AttackAnimation.Phase(0.0F, 1, 1, 1.25F, 1.25F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, supplier.get()),
                new AttackAnimation.Phase(1.25F, 2.16F, 2.16F, 2.42F, 2.42F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, supplier.get()))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1F))));

        DEBUG_ANIM_LIST.add(WRAITHON_1);
    }
    private static void wraithonGroundSplit(LivingEntityPatch<?> entityPatch, float radius, int particleCount) {
        LivingEntity entity = entityPatch.getOriginal();
        float time = entityPatch.getAnimator().getPlayerFor(null).getElapsedTime();
        for (float i = 0; i<=0.005 ; i = i + 0.0001F){
            time = time - i;
            if (time > 0){
                Vec3 pos = wraithonGroundSplitjointRayDetection(entityPatch,SwordSoaringArmatures.WRAITHON_ARMATURE.get().weapon_s,time,2F,true);
                if (pos != null) {
                    if (entity.level() instanceof ServerLevel level) {
                        for (int dy = -1; dy <= 1; dy++) {
                            Vec3 newPos = pos.add(0, dy, 0);
                            LevelUtil.circleSlamFracture(entity, level, newPos, radius);
                        }
                    }
                }
            }
        }
    }
    private static Vec3 wraithonGroundSplitjointRayDetection(LivingEntityPatch<?> livingEntityPatch, Joint joint,float time, float distance, boolean defaultEndpoint) {
        LivingEntity entity = livingEntityPatch.getOriginal();
        Pose pose = livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation().get().getRawPose(time);
        OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBindedTransformFor(pose, joint);
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(entity.yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f rotatedMatrix = new OpenMatrix4f();
        OpenMatrix4f.mul(rotation, transformMatrix, rotatedMatrix);

        float sign = Math.signum(distance);
        float absoluteDistance = Math.abs(distance);
        for (int i = 0; i * 0.1 < absoluteDistance; i++) {
            OpenMatrix4f currentTransform = new OpenMatrix4f(rotatedMatrix);
            currentTransform.translate(new Vec3f(0.0F, 4.5 + i * 0.1 * sign, 0.0F));
            Vec3 pos = new Vec3(
                    currentTransform.m30*2F + (float) entity.getX(),
                    currentTransform.m31*2F + (float) entity.getY(),
                    currentTransform.m32*2F + (float) entity.getZ()
            );
            ParticleVFX.createSphereParticles(entity.level(),pos, ParticleTypes.LARGE_SMOKE,0.8,0.01,0.05,3);

            BlockPos center = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
            if (checkRadiusBlocks(entity, center, 1)) {
                return pos;
            }
        }
        if (defaultEndpoint){
            OpenMatrix4f endTransform = new OpenMatrix4f(rotatedMatrix);
            endTransform.translate(new Vec3f(0.0F, 4.5 + absoluteDistance, 0.0F));
            Vec3 endPos = new Vec3(
                    endTransform.m30*2F + (float) entity.getX(),
                    endTransform.m31*2F + (float) entity.getY(),
                    endTransform.m32*2F + (float) entity.getZ()
            );
            return endPos;
        }
        return null;
    }
    private static boolean checkRadiusBlocks(LivingEntity entity, BlockPos center, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = center.offset(x, y, z);
                    if (entity.level().getBlockState(checkPos).isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }




}
