package net.p1nero.ss.entity.wraithon;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.wraithon.ai.WraithonTargetSelector;
import net.p1nero.ss.entity.wraithon.container.DamageContainer;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPSetAttackTarget;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.List;

public class WraithonEntity extends PathfinderMob {
    public static final int PHASE0 = 0;//阶段0（用血量表示什么的）
    public static final int PHASE1 = 1;//阶段1（用血量表示什么的）
    public static final int PHASE2 = 2;//阶段2（用血量表示什么的）
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
    protected static final EntityDataAccessor<Boolean> ROTATING = SynchedEntityData.defineId(WraithonEntity.class, EntityDataSerializers.BOOLEAN);
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
    public final List<DamageContainer> damageContainers;
    public final DamageContainer fireContainer;
    public final DamageContainer explosionContainer;
    public final DamageContainer magicContainer;
    public final DamageContainer outsideContainer;
    public final DamageContainer projectileContainer;

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

        fireContainer = new DamageContainer(this, List.of(DamageTypes.FIREBALL, DamageTypes.IN_FIRE, DamageTypes.ON_FIRE), FIRE_CONTAINER, 100, FIRE_STATE);
        explosionContainer = new DamageContainer(this, List.of(DamageTypes.EXPLOSION), EXPLOSION_CONTAINER, 100, EXPLOSION_STATE);
        magicContainer = new DamageContainer(this, List.of(DamageTypes.MAGIC, DamageTypes.INDIRECT_MAGIC), MAGIC_CONTAINER, 100, MAGIC_STATE);
        outsideContainer = new DamageContainer(this, List.of(DamageTypes.OUTSIDE_BORDER, DamageTypes.FELL_OUT_OF_WORLD), OUTSIDE_BORDER_CONTAINER, 100, OUTSIDE_STATE);
        projectileContainer = new DamageContainer(this, List.of(DamageTypes.ARROW, DamageTypes.MOB_PROJECTILE), PROJECTILE_CONTAINER, 100, PROJECTILE_STATE);

        damageContainers = List.of(fireContainer, explosionContainer, magicContainer, outsideContainer, projectileContainer);
    }

    public static AttributeSupplier getDefaultAttribute() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000F)
                .add(Attributes.ATTACK_DAMAGE, 99999)
                .add(Attributes.FOLLOW_RANGE, 72)
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

        this.entityData.define(ROTATING, false);

        this.entityData.define(PHASE, 0);
        this.entityData.define(STATE, 0);
        this.entityData.define(FIRE_CONTAINER, 0.0F);
        this.entityData.define(EXPLOSION_CONTAINER, 0.0F);
        this.entityData.define(MAGIC_CONTAINER, 0.0F);
        this.entityData.define(OUTSIDE_BORDER_CONTAINER, 0.0F);
        this.entityData.define(PROJECTILE_CONTAINER, 0.0F);
        this.entityData.define(LEG_DAMAGE_VALUE, 0.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new WraithonTargetSelector(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
    }

    @Override
    public void setTarget(@Nullable LivingEntity pTarget) {
        if (!this.level().isClientSide()) {
            super.setTarget(pTarget);
            EpicFightNetworkManager.sendToAllPlayerTrackingThisEntity(new SPSetAttackTarget((this).getId(), pTarget != null ? pTarget.getId() : -1), this);
        } else {
            super.setTarget(pTarget);
        }
    }

    public boolean isRotating(){
        return getEntityData().get(ROTATING);
    }

    public void setRotating(boolean rotating){
        this.getEntityData().set(ROTATING, rotating);
    }

    public void updateYRotBeforeRotation() {
        if(!level().isClientSide){
            this.getEntityData().set(Y_ROT_BEFORE_ROTATION, this.getYRot());
        }
    }

    public float getCorrectYRot(float partialTick) {
        WraithonEntityPatch wraithonEntityPatch = EpicFightCapabilities.getEntityPatch(this, WraithonEntityPatch.class);
        if(wraithonEntityPatch.getEntityState().inaction() && !wraithonEntityPatch.getAnimator().getPlayerFor(null).getAnimation().get().isLinkAnimation()){
//            System.out.println(this.getEntityData().get(Y_ROT_BEFORE_ROTATION) + " " + wraithonEntityPatch.getAnimator().getPlayerFor(null).getAnimation().get().isLinkAnimation() + wraithonEntityPatch.getAnimator().getPlayerFor(null).getAnimation());
            return this.getEntityData().get(Y_ROT_BEFORE_ROTATION);
        } else {
//            System.out.println(this.getViewYRot(partialTick) + " " + wraithonEntityPatch.getAnimator().getPlayerFor(null).getAnimation().get().isLinkAnimation() + wraithonEntityPatch.getAnimator().getPlayerFor(null).getAnimation());
            return this.getViewYRot(partialTick);
        }
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

    public boolean hurtFromPartEntity(WraithonPartEntity wraithonPartEntity, DamageSource pSource, float pAmount) {
        if(pSource.getEntity() != null && pSource.getEntity().distanceTo(this) >= this.getAttributeBaseValue(Attributes.FOLLOW_RANGE)){
            return false;
        }

        if (isAnyDamageTypeInRange(pSource, pAmount)) {
            return true;
        } else {
            pAmount *= wraithonPartEntity.getDamageReduce();
            if (wraithonPartEntity.isLeg()) {
                this.damageLegs(pAmount);
            }
            //防高频
            MobEffectInstance currentDamageResistance = this.getEffect(MobEffects.DAMAGE_RESISTANCE);
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, currentDamageResistance == null ? 0 : currentDamageResistance.getAmplifier() + 1));
            return this.hurt(pSource, pAmount);
        }
    }

    /**
     * 判断是否属于火焰，虚空，魔法，爆炸，弹射物伤害
     * 出生设计= =
     */
    public boolean isAnyDamageTypeInRange(DamageSource damageSource, float pAmount) {
        boolean flag = false;
        for(DamageContainer container : damageContainers) {
            if(container.containDamage(damageSource)) {
                container.onHurt(damageSource, pAmount);
                if(getPhase() == PHASE0){
                    return true;
                } else {
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public void tick() {
        super.tick();
        for(DamageContainer container : damageContainers){
            container.onTick();
        }
        if(this.getPhase() == PHASE2){
            this.hurt(this.damageSources().fellOutOfWorld(), this.getMaxHealth() * 0.01F * 0.05F);
        }
    }
}
