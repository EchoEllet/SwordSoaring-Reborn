package net.p1nero.ss.util.vfx;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.util.AnimationUtils;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.swing.text.html.parser.Entity;
import java.util.Random;

public class ParticleVFX {

    public static void createRandomLine(Level level, Vec3 center, ParticleOptions particleOptions, double minSpeed, double maxSpeed, int particleCount) {
        Random random1 = level.random;

        for (int i = 0; i < particleCount; i++) {
            double t = (double) i / (particleCount - 1);
            double distance = t * 5.0;

            double angle = random1.nextDouble() * 2 * Math.PI;
            double pitch = random1.nextDouble() * Math.PI - Math.PI / 2;

            double offsetX = Math.cos(angle) * Math.cos(pitch);
            double offsetY = Math.sin(pitch);
            double offsetZ = Math.sin(angle) * Math.cos(pitch);

            double x = center.x() + offsetX * distance;
            double y = center.y() + offsetY * distance;
            double z = center.z() + offsetZ * distance;
            double speed = minSpeed + random1.nextDouble() * (maxSpeed - minSpeed);
            level.addParticle(particleOptions,
                    x, y, z,
                    offsetX * speed, offsetY * speed, offsetZ * speed
            );
        }
    }

    public static void createJointRandomLine(LivingEntityPatch<?> entityPatch, Joint joint, ParticleOptions particleOptions, double minSpeed, double maxSpeed, int particleCount) {
        LivingEntity entity = entityPatch.getOriginal();
        Level level = entity.level;
        Vec3 vec3 = AnimationUtils.getJointWorldPos(entityPatch,joint);
        Random random = level.random;
        for (int i = 0; i < particleCount; i++) {
            double t = (double) i / (particleCount - 1);
            double distance = t * 5.0;

            double angle = random.nextDouble() * 2 * Math.PI;
            double pitch = random.nextDouble() * Math.PI - Math.PI / 2;

            double offsetX = Math.cos(angle) * Math.cos(pitch);
            double offsetY = Math.sin(pitch);
            double offsetZ = Math.sin(angle) * Math.cos(pitch);

            double x = vec3.x() + offsetX * distance;
            double y = vec3.y() + offsetY * distance;
            double z = vec3.z() + offsetZ * distance;
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
            if (level.isClientSide) {
                level.addParticle(particleOptions,
                        x+entity.getX(), y+entity.getY(), z+entity.getZ(),
                        offsetX * speed, offsetY * speed, offsetZ * speed);
            }
        }
    }

    public static void createSphereParticles(Level level, Vec3 center, ParticleOptions particleOptions, double radius, double minSpeed, double maxSpeed, int particleCount) {
        Random random = level.random;
        for (int i = 0; i < particleCount; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double pitch = Math.acos(2 * random.nextDouble() - 1) - Math.PI/2;

            double offsetX = Math.cos(angle) * Math.cos(pitch);
            double offsetY = Math.sin(pitch);
            double offsetZ = Math.sin(angle) * Math.cos(pitch);

            double x = center.x() + offsetX * radius;
            double y = center.y() + offsetY * radius;
            double z = center.z() + offsetZ * radius;
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);

            level.addParticle(particleOptions, x, y, z, offsetX * speed, offsetY * speed, offsetZ * speed);
        }
    }
    public static void createJointSphereParticles(LivingEntityPatch<?> entityPatch, Joint joint, ParticleOptions particleOptions, double radius, double minSpeed, double maxSpeed, int particleCount) {
        LivingEntity entity = entityPatch.getOriginal();
        Level level = entity.level;
        Vec3 vec3 = AnimationUtils.getJointWorldPos(entityPatch,joint);
        Random random = level.random;
        for (int i = 0; i < particleCount; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double pitch = Math.acos(2 * random.nextDouble() - 1) - Math.PI/2;

            double offsetX = Math.cos(angle) * Math.cos(pitch);
            double offsetY = Math.sin(pitch);
            double offsetZ = Math.sin(angle) * Math.cos(pitch);

            double x = vec3.x() + offsetX * radius;
            double y = vec3.y() + offsetY * radius;
            double z = vec3.z() + offsetZ * radius;
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
            if (level.isClientSide) {
                level.addParticle(particleOptions,x+entity.getX(), y+entity.getY(), z+entity.getZ(), offsetX * speed, offsetY * speed, offsetZ * speed);
            }
        }
    }

    public static void createRingParticles(Level level, Vec3 center, ParticleOptions particleOptions, double radius, double minSpeed, double maxSpeed, int particleCount) {
        Random random = level.random;
        for (int i = 0; i < particleCount; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;

            double x = center.x() + Math.cos(angle) * radius;
            double y = center.y();
            double z = center.z() + Math.sin(angle) * radius;


            double speedX = -Math.sin(angle);
            double speedZ = Math.cos(angle);
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);

            level.addParticle(particleOptions, x, y, z, speedX * speed, 0, speedZ * speed);
        }
    }

    public static void createJointRingParticles(LivingEntityPatch<?> entityPatch, Joint joint, ParticleOptions particleOptions, double radius, double minSpeed, double maxSpeed, int particleCount) {
        LivingEntity entity = entityPatch.getOriginal();
        Level level = entity.level;
        Vec3 vec3 = AnimationUtils.getJointWorldPos(entityPatch,joint);
        Random random = level.random;
        for (int i = 0; i < particleCount; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;

            double x = vec3.x() + Math.cos(angle) * radius;
            double y = vec3.y();
            double z = vec3.z() + Math.sin(angle) * radius;


            double speedX = -Math.sin(angle);
            double speedZ = Math.cos(angle);
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);

            level.addParticle(particleOptions,x+entity.getX(), y+entity.getY(), z+entity.getZ(), speedX * speed, 0, speedZ * speed);
        }
    }
    public static void createRandomInSphereParticles(Level level, Vec3 center, ParticleOptions particleOptions, double radius, double minSpeed, double maxSpeed, int particleCount) {
        Random random = level.random;
        for (int i = 0; i < particleCount; i++) {

            double r = radius * Math.cbrt(random.nextDouble());
            double angle = random.nextDouble() * 2 * Math.PI;
            double pitch = Math.acos(2 * random.nextDouble() - 1) - Math.PI/2;

            double x = center.x() + r * Math.cos(angle) * Math.cos(pitch);
            double y = center.y() + r * Math.sin(pitch);
            double z = center.z() + r * Math.sin(angle) * Math.cos(pitch);
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);

            double dirAngle = random.nextDouble() * 2 * Math.PI;
            double dirPitch = random.nextDouble() * Math.PI - Math.PI/2;
            double dirX = Math.cos(dirAngle) * Math.cos(dirPitch);
            double dirY = Math.sin(dirPitch);
            double dirZ = Math.sin(dirAngle) * Math.cos(dirPitch);

            level.addParticle(particleOptions, x, y, z, dirX * speed, dirY * speed, dirZ * speed);
        }
    }
    public static void createJointRandomInSphereParticles(LivingEntityPatch<?> entityPatch, Joint joint, ParticleOptions particleOptions, double radius, double minSpeed, double maxSpeed, int particleCount) {
        LivingEntity entity = entityPatch.getOriginal();
        Level level = entity.level;
        Vec3 vec3 = AnimationUtils.getJointWorldPos(entityPatch,joint);
        Random random = level.random;
        for (int i = 0; i < particleCount; i++) {

            double r = radius * Math.cbrt(random.nextDouble());
            double angle = random.nextDouble() * 2 * Math.PI;
            double pitch = Math.acos(2 * random.nextDouble() - 1) - Math.PI/2;

            double x = vec3.x() + r * Math.cos(angle) * Math.cos(pitch);
            double y = vec3.y() + r * Math.sin(pitch);
            double z = vec3.z() + r * Math.sin(angle) * Math.cos(pitch);
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);

            double dirAngle = random.nextDouble() * 2 * Math.PI;
            double dirPitch = random.nextDouble() * Math.PI - Math.PI/2;
            double dirX = Math.cos(dirAngle) * Math.cos(dirPitch);
            double dirY = Math.sin(dirPitch);
            double dirZ = Math.sin(dirAngle) * Math.cos(dirPitch);
            if (level.isClientSide) {
                level.addParticle(particleOptions, x+entity.getX(), y+entity.getY(), z+entity.getZ(), dirX * speed, dirY * speed, dirZ * speed);
            }
        }
    }


    public static void createLineSegmentParticles(Level level, Vec3 start, Vec3 end, ParticleOptions particleOptions, double minSpeed, double maxSpeed, int particleCount) {
        Random random = level.random;
        Vec3 direction = end.subtract(start);
        for (int i = 0; i < particleCount; i++) {
            double t = (double)i / (particleCount - 1);
            Vec3 pos = start.add(direction.scale(t));

            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
            double dirX = direction.x / particleCount;
            double dirY = direction.y / particleCount;
            double dirZ = direction.z / particleCount;

            level.addParticle(particleOptions, pos.x, pos.y, pos.z, dirX * speed, dirY * speed, dirZ * speed);
        }
    }
    public static void createMovingParticles(Level level, Vec3 start, Vec3 end, ParticleOptions particleOptions, double minSpeed, double maxSpeed, int particleCount) {
        Random random = level.random;
        Vec3 direction = end.subtract(start).normalize();
        for (int i = 0; i < particleCount; i++) {
            double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
            level.addParticle(particleOptions,
                    start.x, start.y, start.z,
                    direction.x * speed,
                    direction.y * speed,
                    direction.z * speed
            );
        }
    }







}
