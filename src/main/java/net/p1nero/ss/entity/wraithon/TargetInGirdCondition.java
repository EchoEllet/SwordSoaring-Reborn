package net.p1nero.ss.entity.wraithon;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.gameasset.EpicFightSounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TargetInGirdCondition implements Condition<WraithonEntityPatch> {

    public static class Rectangle {
        public final int xMin, xMax;
        public final int zMin, zMax;

        public Rectangle(int x1, int z1, int x2, int z2) {
            this.xMin = Math.min(x1, x2);
            this.xMax = Math.max(x1, x2);
            this.zMin = Math.min(z1, z2);
            this.zMax = Math.max(z1, z2);
        }
    }

    private final List<Rectangle> rectangles;

    public TargetInGirdCondition(int x, int z) {
        this.rectangles = Collections.singletonList(
                new Rectangle(x, z, x, z)
        );
    }

    public TargetInGirdCondition(Rectangle... rects) {
        this.rectangles = Arrays.asList(rects);
    }

    @Override
    public boolean predicate(WraithonEntityPatch wraithon) {
        Vec3 bossPos = wraithon.getOriginal().position();
        Level world = wraithon.getOriginal().level();

        AABB searchArea = new AABB(
                bossPos.x - 30, bossPos.y - 2, bossPos.z - 30,
                bossPos.x + 30, bossPos.y + 2, bossPos.z + 30
        );

        List<LivingEntity> entities = world.getEntitiesOfClass(
                LivingEntity.class,
                searchArea,
                e -> e.isAlive() && e != wraithon.getOriginal()
        );

//        drawAllRectangles(wraithon);

        float yRot = wraithon.getOriginal().getYRot();
        double theta = Math.toRadians(yRot);
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        List<Rectangle> rects = new ArrayList<>(rectangles);

        for (LivingEntity entity : entities) {
            Vec3 entityPos = entity.position();
            double dx = entityPos.x - bossPos.x;
            double dz = entityPos.z - bossPos.z;
            double localX = dx * cos + dz * sin;
            double localZ = -dx * sin + dz * cos;

            if (Math.abs(localX) >= 24 || Math.abs(localZ) >= 24) continue;

            int gridX = (int) Math.floor((localX + 1) / 2);
            int gridZ = (int) Math.floor((localZ + 1) / 2);

            for (Rectangle rect : rects) {
                if (gridX >= rect.xMin &&
                        gridX <= rect.xMax &&
                        gridZ >= rect.zMin &&
                        gridZ <= rect.zMax) {
                    return true;
                }
            }
        }
        return false;
    }


    private void drawAllRectangles(WraithonEntityPatch wraithon) {
        Vec3 bossPos = wraithon.getOriginal().position();
        float yRot = wraithon.getOriginal().getYRot();
        LivingEntity livingEntity = wraithon.getOriginal();

        for (Rectangle rect : rectangles) {
            double minX = rect.xMin * 2 - 1;
            double maxX = rect.xMax * 2 + 1;
            double minZ = rect.zMin * 2 - 1;
            double maxZ = rect.zMax * 2 + 1;

            drawLine(bossPos, yRot, minX, minZ, maxX, minZ,livingEntity);
            drawLine(bossPos, yRot, maxX, minZ, maxX, maxZ,livingEntity);
            drawLine(bossPos, yRot, maxX, maxZ, minX, maxZ,livingEntity);
            drawLine(bossPos, yRot, minX, maxZ, minX, minZ,livingEntity);
        }
    }

    private void drawLine(Vec3 bossPos, float yRot,
                          double x1, double z1, double x2, double z2, LivingEntity entity) {
        final double step = 0.1;
        double dx = x2 - x1;
        double dz = z2 - z1;
        double length = Math.sqrt(dx*dx + dz*dz);
        int steps = (int) (length / step);

        for (int i = 0; i <= steps; i++) {
            double progress = i / (double) steps;
            double localX = x1 + dx * progress;
            double localZ = z1 + dz * progress;

            Vec3 worldPos = localToWorld(
                    new Vec3(localX, 0, localZ),
                    bossPos,
                    yRot
            );
            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.SMOKE,
                        worldPos.x,
                        worldPos.y + 0.5,
                        worldPos.z,
                        5,
                        0,
                        0,
                        0,
                        0);

            }

        }
    }

    private Vec3 localToWorld(Vec3 local, Vec3 origin, float yRot) {
        double radian = Math.toRadians(yRot);
        double cos = Math.cos(radian);
        double sin = Math.sin(radian);

        return new Vec3(
                origin.x + local.x * cos - local.z * sin,
                origin.y,
                origin.z + local.x * sin + local.z * cos
        );
    }


    @Override
    public Condition<WraithonEntityPatch> read(CompoundTag compoundTag) throws IllegalArgumentException {
        return null;
    }

    @Override
    public CompoundTag serializePredicate() {
        return null;
    }

    @Override
    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}
