package net.p1nero.ss.entity.wraithon;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
    public static final int PHASE0 = 0;//阶段0（用血量表示什么的）
    public static final int PHASE1 = 1;//阶段1（用血量表示什么的）
    public static final int DEFAULT_STATE = 0;//火状态（吸收火伤到一定程度）
    public static final int FIRE_STATE = 1;//火状态（吸收火伤到一定程度）
    public static final int EXPLOSION_STATE = 2;//爆炸状态（吸收爆炸伤害到一定程度）
    public static final int MAGIC_STATE = 3;//魔法状态（吸收魔法伤害到一定程度）
    public static final int OUTSIDE_STATE = 4;//虚空状态（吸收虚空伤害到一定程度）真jb有人打虚空伤害？
    public static final int PROJECTILE_STATE = 5;//投掷物伤害（吸收投掷物伤害到一定程度）
    protected static EntityDataAccessor<Float> Y_ROT_BEFORE_ROTATION = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);

    protected static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> FIRE_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> EXPLOSION_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> MAGIC_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> OUTSIDE_BORDER_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> PROJECTILE_CONTAINER = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
    public static final int MAX_LEG_DAMAGE = 300;
    protected static final EntityDataAccessor<Float> LEG_DAMAGE_VALUE = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.FLOAT);
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
        this.leg_M_3_R = new WraithonPartEntity(this, armature.leg_M_3_R, 2.0F, 6.0F, new Vec3(0, -5, 0), 0.9F);
        this.leg_B_3_R = new WraithonPartEntity(this, armature.leg_B_3_R, 2.0F, 5.0F, new Vec3(0, -3, 0), 0.9F);
        this.leg_F_3_L = new WraithonPartEntity(this, armature.leg_F_3_L, 2.0F, 5.0F, new Vec3(0, -3, 0), 0.9F);
        this.leg_M_3_L = new WraithonPartEntity(this, armature.leg_M_3_L, 2.0F, 6.0F, new Vec3(0, -5, 0), 0.9F);
        this.leg_B_3_L = new WraithonPartEntity(this, armature.leg_B_3_L, 2.0F, 5.0F, new Vec3(0, -3, 0), 0.9F);
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
     * 不做持久化了
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(Y_ROT_BEFORE_ROTATION, 0.0F);

        this.entityData.define(PHASE, 0);
        this.entityData.define(STATE, 0);
        this.entityData.define(FIRE_CONTAINER, 0.0F);
        this.entityData.define(EXPLOSION_CONTAINER, 0.0F);
        this.entityData.define(MAGIC_CONTAINER, 0.0F);
        this.entityData.define(OUTSIDE_BORDER_CONTAINER, 0.0F);
        this.entityData.define(PROJECTILE_CONTAINER, 0.0F);
        this.entityData.define(LEG_DAMAGE_VALUE, 0.0F);
    }

    public void updateYRotBeforeRotation() {
        if(!level().isClientSide){
            this.getEntityData().set(Y_ROT_BEFORE_ROTATION, this.getYRot());
            System.out.println("record Y:" + this.getYRot());
        }
    }

    public float getYRotBeforeRotation() {
        return this.getEntityData().get(Y_ROT_BEFORE_ROTATION);
    }

    /**
     * 获取boss当前阶段
     */
    public int getPhase() {
        return this.entityData.get(PHASE);
    }

    public void setPhase(int newPhase) {
        this.entityData.set(PHASE, newPhase);
    }

    /**
     * 获取boss当前状态
     */
    public int getState() {
        return this.entityData.get(STATE);
    }

    public void setState(int newState) {
        this.entityData.set(STATE, newState);
    }

    public float getLegDamage() {
        return this.entityData.get(LEG_DAMAGE_VALUE);
    }

    public void damageLegs(float damageValue) {
        this.entityData.set(LEG_DAMAGE_VALUE, this.getLegDamage() + damageValue);
        //大于最大值则进入硬直
        if (this.getLegDamage() > MAX_LEG_DAMAGE) {
            //TODO 进硬直
            clearLegDamage();
        }
    }

    public void clearLegDamage() {
        this.entityData.set(LEG_DAMAGE_VALUE, 0.0F);
    }

    /**
     * 防止被推
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    /**
     * 以肢体碰撞为准
     */
    @Override
    protected void pushEntities() {
        for (WraithonPartEntity part : getWraithonParts()) {
            if (part == null) {
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

    /**
     *
     */
    public boolean hurtFromPartEntity(WraithonPartEntity wraithonPartEntity, DamageSource pSource, float pAmount) {
        if (isDamageTypeInRange(pSource)) {
            //TODO 根据伤害判定
            return true;
        } else {
            pAmount *= wraithonPartEntity.getDamageReduce();
            if (wraithonPartEntity.isLeg()) {
                this.damageLegs(pAmount);
            }
            MobEffectInstance currentDamageResistance = this.getEffect(MobEffects.DAMAGE_RESISTANCE);
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, currentDamageResistance == null ? 0 : currentDamageResistance.getAmplifier() + 1));
            return this.hurt(pSource, pAmount);
        }
    }

    /**
     * 判断是否属于火焰，虚空，魔法，爆炸，弹射物伤害
     * 出生设计= =
     */
    public boolean isDamageTypeInRange(DamageSource damageSource) {
        for (ResourceKey<DamageType> damageTypeResourceKey : damageTypes) {
            if (damageSource.is(damageTypeResourceKey)) {
                return true;
            }
        }
        return false;
    }


}
