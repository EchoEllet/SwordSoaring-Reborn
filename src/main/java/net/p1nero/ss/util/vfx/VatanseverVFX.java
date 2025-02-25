package net.p1nero.ss.util.vfx;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class VatanseverVFX {
    public static void flyVFX(LivingEntityPatch<?> entityPatch) {
        int particleCount = 1;
        Level world = entityPatch.getOriginal().level;
        if (entityPatch instanceof VatanseverEntityPatch vatanseverEntityPatch && world.isClientSide) {
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.L1, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.L2, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.L3, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.R1, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.R2, particleCount);
            jet(vatanseverEntityPatch, SwordSoaringArmatures.vatanseverArmature.R3, particleCount);
        }
    }


    private static void jet(VatanseverEntityPatch vatanseverEntityPatch, Joint toolJoint, int particleCount) {
        VatanseverEntity vatanseverEntity = vatanseverEntityPatch.getOriginal();
        if(vatanseverEntity.getOwner() == null){
            return;
        }
        Level world = vatanseverEntity.level;
        // 获取骨骼变换矩阵
        OpenMatrix4f transformMatrix = vatanseverEntityPatch.getArmature()
                .getBindedTransformFor(
                        vatanseverEntityPatch.getArmature().getCurrentPose(),
                        toolJoint
                );

        // 初始变换（位置偏移和基础旋转）
        transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(
                -(float) Math.toRadians(vatanseverEntityPatch.getOriginal().yBodyRot + 180.0F),
                new Vec3f(0.0F, 1.0F, 0.0F)
        );
        OpenMatrix4f.mul(rotation, transformMatrix, transformMatrix);

        // 提取旋转后的 Y 轴方向作为基础速度
        Vec3f baseVelocity = new Vec3f(transformMatrix.m10 * 0.1F, transformMatrix.m11 * 0.1F, transformMatrix.m12 * 0.1F);

        // 获取玩家当前速度
        Vec3 playerVelocity = vatanseverEntity.getOwner().getDeltaMovement();
        Vec3f relativeVelocity = new Vec3f(
                (float) playerVelocity.x + baseVelocity.x,
                (float) playerVelocity.y + baseVelocity.y,
                (float) playerVelocity.z + baseVelocity.z
        );

        // 生成粒子
        for (int i = 0; i < 5 * particleCount; i++) {
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
        for (int i = 0; i < 3 * particleCount; i++) {
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
    }
}