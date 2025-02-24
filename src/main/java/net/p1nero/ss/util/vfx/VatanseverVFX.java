package net.p1nero.ss.util.vfx;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.api.utils.math.Vec4f;
import yesman.epicfight.gameasset.Armatures;
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



    private static void jet (LivingEntityPatch entitypatch, Joint toolJoint, Level world) {
        LivingEntity entity = (LivingEntity) entitypatch.getOriginal();
        Random random = world.random;

        // 生成随机偏移量
        float min = 0.5f;
        float max = 1.9f;
        float rz = min + (max - min) * random.nextFloat();

        // 获取骨骼变换矩阵
        OpenMatrix4f transformMatrix = entitypatch.getArmature()
                .getBindedTransformFor(
                        entitypatch.getArmature().getCurrentPose(),
                        toolJoint
                );

        // 应用变换
        transformMatrix.translate(new Vec3f(0.0F, 0.5F, 0));
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(
                -(float) Math.toRadians(entity.yBodyRotO + 180.0F),
                new Vec3f(0.0F, 1.0F, 0.0F)
        );
        OpenMatrix4f.mul(rotation, transformMatrix, transformMatrix);

        // 提取骨骼变换矩阵的原始Y轴方向(头疼砍头)
        Vec4f boneYAxis = new Vec4f(
                transformMatrix.m01,
                transformMatrix.m11,
                transformMatrix.m21,
                1.0F
        );

        // 创建基于 yBodyRotO 的旋转矩阵（绕世界Y轴旋转）
        float yaw = -(float) Math.toRadians(entity.yBodyRotO);
        OpenMatrix4f yawRotation = new OpenMatrix4f().rotate(yaw, new Vec3f(0.0F, 1.0F, 0.0F));

        // 将骨骼的Y轴方向通过旋转矩阵转换到世界坐标系
        OpenMatrix4f.transform(yawRotation, boneYAxis, boneYAxis);

        // 应用速度系数
        float speedFactor = 0.2f;
        boneYAxis.scale(speedFactor);

        float speedX = boneYAxis.x;
        float speedY = boneYAxis.y;
        float speedZ = boneYAxis.z;

        // 生成粒子
        for (int i = 0; i < 10; i++) {
            world.addParticle(ParticleTypes.SMOKE,
                    transformMatrix.m30 + (float) entity.getX(),
                    transformMatrix.m31 + (float) entity.getY(),
                    transformMatrix.m32 + (float) entity.getZ(),
                    speedX,
                    speedY,
                    speedZ
            );
        }
    }




}
