package net.p1nero.ss.entity.ray;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.SwordSoaringEntities;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class RayEntity extends Entity {

    private static final EntityDataAccessor<Vector3f> START_POS =
            SynchedEntityData.defineId(RayEntity.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> END_POS =
            SynchedEntityData.defineId(RayEntity.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID =
            SynchedEntityData.defineId(RayEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public RayEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity owner = getOwner();
        if (owner != null) {






        }
        if (this.tickCount > 1) this.discard();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(START_POS, new Vector3f(0.0F, 0.0F, 0.0F));
        this.entityData.define(END_POS, new Vector3f(0.0F, 0.0F, 0.0F));
        this.entityData.define(OWNER_UUID, Optional.empty());
    }

    public void setOwner(@Nullable LivingEntity owner) {
        if (owner != null) {
            this.entityData.set(OWNER_UUID, Optional.of(owner.getUUID()));
        } else {
            this.entityData.set(OWNER_UUID, Optional.empty());
        }
    }

    @Nullable
    public LivingEntity getOwner() {
        Optional<UUID> uuid = this.entityData.get(OWNER_UUID);
        if (uuid.isPresent() && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(uuid.get());
            return entity instanceof LivingEntity ? (LivingEntity) entity : null;
        }
        return null;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        // 读取保存的主人 UUID
        if (pCompound.hasUUID("Owner")) {
            this.entityData.set(OWNER_UUID, Optional.of(pCompound.getUUID("Owner")));
        } else {
            this.entityData.set(OWNER_UUID, Optional.empty());
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        // 保存主人 UUID
        Optional<UUID> uuid = this.entityData.get(OWNER_UUID);
        uuid.ifPresent(value -> pCompound.putUUID("Owner", value));
    }

    public static void spawnRay(Level level, LivingEntity owner, Vec3 start, Vec3 end) {
        RayEntity ray = new RayEntity(SwordSoaringEntities.RAY_ENTITY.get(), level);
        ray.setOwner(owner);
        ray.updatePositions(start, end);
        level.addFreshEntity(ray);
    }

    public void updatePositions(Vec3 newStart, Vec3 newEnd) {
        this.entityData.set(START_POS, new Vector3f(
                (float) newStart.x,
                (float) newStart.y,
                (float) newStart.z
        ));
        this.entityData.set(END_POS, new Vector3f(
                (float) newEnd.x,
                (float) newEnd.y,
                (float) newEnd.z
        ));
        this.setPos(newStart.x, newStart.y, newStart.z);
    }
    public Vec3 getStartPos() {
        Vector3f vec = this.entityData.get(START_POS);
        return new Vec3(vec.x(), vec.y(), vec.z());
    }

    public Vec3 getEndPos() {
        Vector3f vec = this.entityData.get(END_POS);
        return new Vec3(vec.x(), vec.y(), vec.z());
    }


    public static void spawnRay(Level level, Vec3 start, Vec3 end) {
        RayEntity ray = new RayEntity(SwordSoaringEntities.RAY_ENTITY.get(), level);
        ray.updatePositions(start, end);
        level.addFreshEntity(ray);
    }

    public static void updateRay(RayEntity entity, Vec3 newEnd) {
        Vec3 currentStart = entity.getStartPos();
        entity.updatePositions(currentStart, newEnd);
    }
}
