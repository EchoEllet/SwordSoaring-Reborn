package net.p1nero.ss.util.vfx;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.util.MathUtil;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

public class VatanseverVFX {
    public static void flyVFX(LivingEntityPatch entitypatch) {
        int particleCount = 1;
        Level world = entitypatch.getOriginal().level;
        if (entitypatch instanceof VatanseverEntityPatch) {
            if (world.isClientSide()) {
                for (int i = 0; i < particleCount; i++) {
                    jet(entitypatch, SwordSoaringArmatures.vatanseverArmature.L1, world);
                    jet(entitypatch, SwordSoaringArmatures.vatanseverArmature.L2, world);
                    jet(entitypatch, SwordSoaringArmatures.vatanseverArmature.L3, world);
                    jet(entitypatch, SwordSoaringArmatures.vatanseverArmature.R1, world);
                    jet(entitypatch, SwordSoaringArmatures.vatanseverArmature.R2, world);
                    jet(entitypatch, SwordSoaringArmatures.vatanseverArmature.R3, world);
                }
            }
        }
    }



    private static void jet(LivingEntityPatch entitypatch, Joint toolJoint, Level world) {
        if (!(entitypatch.getOriginal() instanceof VatanseverEntity)) {
            return;
        }
        if (!world.isClientSide()) {
            return;
        }
        VatanseverEntity vatanseverEntity = (VatanseverEntity) entitypatch.getOriginal();
        Random random = world.random;

        // 获取骨骼变换矩阵
        OpenMatrix4f transformMatrix = entitypatch.getArmature()
                .getBindedTransformFor(
                        entitypatch.getArmature().getCurrentPose(),
                        toolJoint
                );

        // 初始变换（位置偏移和基础旋转）
        transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(
                -(float) Math.toRadians(vatanseverEntity.yBodyRot + 180.0F),
                new Vec3f(0.0F, 1.0F, 0.0F)
        );
        OpenMatrix4f.mul(rotation, transformMatrix, transformMatrix);
        //飞行下的额外计算
        if (vatanseverEntity.getOwner() != null && vatanseverEntity.getOwner().isFallFlying()) {
            Vector3f selfView = new Vector3f(vatanseverEntity.getViewVector(1.0F));
            selfView.add(0.0F, -0.5F, 0.0F);
            Vector3f ownerView = new Vector3f(vatanseverEntity.getOwner().getViewVector(1.0F));
            Quaternion renderQuat = MathUtil.rotateTo(
                    ownerView.x(), ownerView.y(), ownerView.z(),
                    selfView.x(), selfView.y(), selfView.z()
            );
            float x = renderQuat.i();
            float y = renderQuat.j();
            float z = renderQuat.k();
            float w = renderQuat.r();
            OpenMatrix4f renderRotationMatrix = OpenMatrix4f.fromQuaternion(new Quaternion(x, y, z, w));
            OpenMatrix4f.mul(renderRotationMatrix, transformMatrix, transformMatrix);
        }
        Entity owner = vatanseverEntity.getOwner();

        // 提取旋转后的 Y 轴方向作为基础速度
        Vec3f baseVelocity = new Vec3f(
                transformMatrix.m10 * 0.1F,
                transformMatrix.m11 * 0.1F,
                transformMatrix.m12 * 0.1F
        );

        // 获取玩家当前速度
        Vec3 playerVelocity = owner.getDeltaMovement();
        Vec3f relativeVelocity = new Vec3f(
                (float) playerVelocity.x + baseVelocity.x,
                (float) playerVelocity.y + baseVelocity.y,
                (float) playerVelocity.z + baseVelocity.z
        );

        // 生成粒子
        for (int i = 0; i < 50; i++) {
            world.addParticle(
                    ParticleTypes.CLOUD,
                    transformMatrix.m30 + (float) vatanseverEntity.getX(),
                    transformMatrix.m31 + (float) vatanseverEntity.getY(),
                    transformMatrix.m32 + (float) vatanseverEntity.getZ(),
                    relativeVelocity.x,
                    relativeVelocity.y,
                    relativeVelocity.z
            );
        }
        for (int i = 0; i < 30; i++) {
            world.addParticle(
                    ParticleTypes.END_ROD,
                    transformMatrix.m30 + (float) vatanseverEntity.getX(),
                    transformMatrix.m31 + (float) vatanseverEntity.getY(),
                    transformMatrix.m32 + (float) vatanseverEntity.getZ(),
                    relativeVelocity.x,
                    relativeVelocity.y,
                    relativeVelocity.z
            );
        }
    }}