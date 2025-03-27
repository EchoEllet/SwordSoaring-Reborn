package net.p1nero.ss.entity.wraithon;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.List;

public class WraithonEntity extends PathfinderMob {
    protected static final EntityDataAccessor<Float> FIRE_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> EXPLOSION_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> MAGIC_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> OUTSIDE_BORDER_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> PROJECTILE_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    private final WraithonPartEntity[] subEntities;
    private final WraithonPartEntity head;
    private final WraithonPartEntity chest;
    private final WraithonPartEntity tail;
    private final WraithonPartEntity leg_F_3_R;
    private final WraithonPartEntity leg_M_3_R;
    private final WraithonPartEntity leg_B_3_R;
    private final WraithonPartEntity leg_F_3_L;
    private final WraithonPartEntity leg_M_3_L;
    private final WraithonPartEntity leg_B_3_L;
    private final List<ResourceKey<DamageType>> damageTypes = ImmutableList.of(
            DamageTypes.EXPLOSION,
            DamageTypes.ARROW, DamageTypes.MOB_PROJECTILE,
            DamageTypes.FIREBALL, DamageTypes.IN_FIRE, DamageTypes.ON_FIRE,
            DamageTypes.MAGIC, DamageTypes.INDIRECT_MAGIC,
            DamageTypes.OUTSIDE_BORDER, DamageTypes.FELL_OUT_OF_WORLD);

    public WraithonEntity(EntityType<? extends WraithonEntity> pEntityType, Level pLevel) {
        super(SwordSoaringEntities.WRAITHON.get(), pLevel);

        WraithonArmature armature = SwordSoaringArmatures.WRAITHON_ARMATURE.get();
        this.head = new WraithonPartEntity(this, armature.head, 1.5F, 1.5F, new Vec3(0, -0.5, 1), 0);
        this.chest = new WraithonPartEntity(this, armature.chest, 3.0F, 7.0F, new Vec3(0, -4, 0), 0.2F);
        this.tail = new WraithonPartEntity(this, armature.tail, 6.0F, 5.0F, new Vec3(0, -1.5, 0), 0.7F);
        this.leg_F_3_R = new WraithonPartEntity(this, armature.leg_F_3_R, 2.0F, 5.0F, new Vec3(0, -3, 0), 0.9F);
        this.leg_M_3_R = new WraithonPartEntity(this, armature.leg_M_3_R, 2.0F, 6.0F, new Vec3(0, -5, 0),0.9F);
        this.leg_B_3_R = new WraithonPartEntity(this, armature.leg_B_3_R, 2.0F, 5.0F, new Vec3(0, -3, 0),0.9F);
        this.leg_F_3_L = new WraithonPartEntity(this, armature.leg_F_3_L, 2.0F, 5.0F, new Vec3(0, -3, 0),0.9F);
        this.leg_M_3_L = new WraithonPartEntity(this, armature.leg_M_3_L, 2.0F, 6.0F, new Vec3(0, -5, 0),0.9F);
        this.leg_B_3_L = new WraithonPartEntity(this, armature.leg_B_3_L, 2.0F, 5.0F, new Vec3(0, -3, 0),0.9F);
        subEntities = new WraithonPartEntity[]{head, chest, tail, leg_F_3_R, leg_M_3_R, leg_B_3_R, leg_F_3_L, leg_M_3_L, leg_B_3_L};
    }

    public static AttributeSupplier getDefaultAttribute() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000F)
                .add(Attributes.ATTACK_DAMAGE, 99999)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 50.0F)
                .build();
    }

    /**
     * 防止被推
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        setYHeadRot(getYRot());
    }

    @Override
    protected void pushEntities() {
        for(WraithonPartEntity part : getWraithonParts()) {
            if(part == null){
                continue;
            }
            if (this.level().isClientSide()) {
                this.level().getEntities(EntityTypeTest.forClass(Player.class), part.getSize().makeBoundingBox(part.position()), EntitySelector.pushableBy(this)).forEach(this::doPush);
            } else {
                List<Entity> list = this.level().getEntities(this, part.getSize().makeBoundingBox(part.position()), EntitySelector.pushableBy(this));
                if (!list.isEmpty()) {
                    for (Entity entity : list) {
                        this.doPush(entity);
                    }
                }

            }
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public @Nullable PartEntity<?>[] getParts() {
        return subEntities;
    }

    public @Nullable WraithonPartEntity[] getWraithonParts() {
        return subEntities;
    }

    protected float getSoundVolume() {
        return 7.0F;
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        hurtTime = 0;//取消受击变色
        return super.hurt(pSource, pAmount);
    }

    public boolean hurt(WraithonPartEntity wraithonPartEntity, DamageSource pSource, float pAmount) {
        if (isDamageTypeInRange(pSource)) {

        } else {
            pAmount *= wraithonPartEntity.getDamageReduce();
        }
        return this.hurt(pSource, pAmount);
    }

    /**
     * 判断是否属于火焰，虚空，魔法，爆炸，弹射物伤害
     * 出生设计= =
     */
    public boolean isDamageTypeInRange(DamageSource damageSource){
        for(ResourceKey<DamageType> damageTypeResourceKey : damageTypes){
            if(damageSource.is(damageTypeResourceKey)){
                return true;
            }
        }
        return false;
    }

}
