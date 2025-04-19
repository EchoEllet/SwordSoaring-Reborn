package net.p1nero.ss.collider;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;
import net.p1nero.ss.entity.ReplaceableArmature;
import net.p1nero.ss.mixin.ColliderInvoker;
import net.p1nero.ss.mixin.MultiColliderAccessor;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiCollider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Iterator;
import java.util.List;

/**
 * 包一层，方便旋转
 */
public class WrappedCollider<T extends Collider> extends Collider {
    public T originalCollider;
    boolean corrected;
    protected Armature originalArmature = null;
    public WrappedCollider(T originalCollider) {
        super(Vec3.ZERO, null);
        this.originalCollider = originalCollider;
    }

    public T getOriginalCollider() {
        return originalCollider;
    }

    @Override
    protected void transform(OpenMatrix4f mat) {
        if(originalArmature != null && !corrected){
            mat.rotateDeg(90, Vec3f.X_AXIS);
            mat.rotateDeg(90, Vec3f.Z_AXIS);
            corrected = true;
        }
        ((ColliderInvoker)originalCollider).sword_soaring$transform(mat);
    }

    @Override
    protected AABB getHitboxAABB() {
        return ((ColliderInvoker)originalCollider).sword_soaring$getHitboxAABB();
    }

    @Override
    public List<Entity> getCollideEntities(Entity entity) {
        return originalCollider.getCollideEntities(entity);
    }

    @Override
    public List<Entity> updateAndSelectCollideEntity(LivingEntityPatch<?> entityPatch, AttackAnimation attackAnimation, float prevElapsedTime, float elapsedTime, Joint joint, float attackSpeed) {
        if(originalArmature == null){
            originalArmature = entityPatch.getArmature();
        }
        if(this.originalCollider instanceof MultiCollider<?> multiCollider){
            int numberOf = Math.max(Math.round((float)(((MultiColliderAccessor<?>)multiCollider).getNumberOfColliders() + attackAnimation.getProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS).orElse(0)) * attackSpeed), ((MultiColliderAccessor<?>)multiCollider).getNumberOfColliders());
            float partialScale = 1.0F / (float)(numberOf - 1);
            float interpolation = 0.0F;
            List<Collider> colliders = Lists.newArrayList();
            LivingEntity original = entityPatch.getOriginal();
            float index = 0.0F;
            float interIndex = Math.min((float)(((MultiColliderAccessor<?>)multiCollider).getNumberOfColliders() - 1) / (float)(numberOf - 1), 1.0F);

            for(int i = 0; i < numberOf; ++i) {
                colliders.add(((MultiColliderAccessor<?>)multiCollider).getColliders().get((int)index).deepCopy());
                index += interIndex;
            }

            AABB outerBox = null;

            for (Collider collider : colliders) {
                Armature armature = entityPatch.getArmature();
                OpenMatrix4f transformMatrix;
                if (armature.rootJoint.equals(joint)) {
                    Pose rootPose = new Pose();
                    rootPose.putJointData("Root", JointTransform.empty());
                    attackAnimation.modifyPose(attackAnimation, rootPose, entityPatch, elapsedTime, 1.0F);
                    transformMatrix = rootPose.get("Root").getAnimationBoundMatrix(entityPatch.getArmature().rootJoint, new OpenMatrix4f()).removeTranslation();
                } else {
                    float interpolateTime = prevElapsedTime + (elapsedTime - prevElapsedTime) * interpolation;
                    transformMatrix = armature.getBoundTransformFor(attackAnimation.getPoseByTime(entityPatch, interpolateTime, 1.0F), joint);
                }

                double x = entityPatch.getXOld() + (original.getX() - entityPatch.getXOld()) * (double) interpolation;
                double y = entityPatch.getYOld() + (original.getY() - entityPatch.getYOld()) * (double) interpolation;
                double z = entityPatch.getZOld() + (original.getZ() - entityPatch.getZOld()) * (double) interpolation;
                OpenMatrix4f mvMatrix = OpenMatrix4f.createTranslation(-((float) x), (float) y, -((float) z));
                transformMatrix.mulFront(mvMatrix.mulBack(entityPatch.getModelMatrix(interpolation)));

                if(armature instanceof ReplaceableArmature){
                    transformMatrix.rotateDeg(90, Vec3f.X_AXIS);
                    transformMatrix.rotateDeg(90, Vec3f.Z_AXIS);
                }

                ((ColliderInvoker)collider).sword_soaring$transform(transformMatrix);
                interpolation += partialScale;
                if (outerBox == null) {
                    outerBox = ((ColliderInvoker)collider).sword_soaring$getHitboxAABB();
                } else {
                    outerBox.minmax( ((ColliderInvoker)collider).sword_soaring$getHitboxAABB());
                }
            }

            List<Entity> entities = entityPatch.getOriginal().level().getEntities(entityPatch.getOriginal(), outerBox, (entity) -> {
                if (entity.isSpectator()) {
                    return false;
                } else if (entity instanceof PartEntity && ((PartEntity<?>)entity).getParent().is(entityPatch.getOriginal())) {
                    return false;
                } else {
                    Iterator<Collider> var3 = colliders.iterator();

                    Collider collider;
                    do {
                        if (!var3.hasNext()) {
                            return false;
                        }

                        collider = var3.next();
                    } while(!collider.isCollide(entity));

                    return true;
                }
            });
            return entities;
        }
        return originalCollider.updateAndSelectCollideEntity(entityPatch, attackAnimation, prevElapsedTime, elapsedTime, joint, attackSpeed);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawInternal(PoseStack poseStack, VertexConsumer vertexConsumer, Armature armature, Joint joint, Pose pose, Pose pose1, float v, int i) {
        originalCollider.drawInternal(poseStack, vertexConsumer, armature, joint, pose, pose1, v, i);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(PoseStack poseStack, MultiBufferSource buffer, LivingEntityPatch<?> entityPatch, AttackAnimation animation, Joint joint, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed) {
        originalCollider.draw(poseStack, buffer, entityPatch, animation, joint, prevElapsedTime, elapsedTime, partialTicks, attackSpeed);
    }

    @Override
    public Collider deepCopy() {
        return originalCollider.deepCopy();
    }

    @Override
    public boolean isCollide(Entity entity) {
        return originalCollider.isCollide(entity);
    }

    @Override
    public RenderType getRenderType() {
        return originalCollider.getRenderType();
    }

    @Override
    public CompoundTag serialize(CompoundTag resultTag) {
        return originalCollider.serialize(resultTag);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof WrappedCollider<?>)){
            return false;
        }
        return this.originalCollider.equals(((WrappedCollider<?>) obj).originalCollider);
    }

    @Override
    public String toString() {
        return originalCollider.toString();
    }
}
