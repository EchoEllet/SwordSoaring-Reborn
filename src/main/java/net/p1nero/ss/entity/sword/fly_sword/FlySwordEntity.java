package net.p1nero.ss.entity.sword.fly_sword;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.animations.FlySwordAnimations;
import net.p1nero.ss.gameassets.animations.VatanseverAnimations;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import net.p1nero.ss.util.AnimationUtils;
import net.p1nero.ss.util.vfx.ParticleVFX;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Random;

public class FlySwordEntity extends AbstractSwordEntity {
    private int maxTickCount = -1;
    private static final EntityDataAccessor<Boolean> ROTATION_LOCK = SynchedEntityData.defineId(FlySwordEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANIMATION_END = SynchedEntityData.defineId(FlySwordEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLYING_BACK = SynchedEntityData.defineId(FlySwordEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> READY_TO_FLY_BACK = SynchedEntityData.defineId(FlySwordEntity.class, EntityDataSerializers.BOOLEAN);
    private LivingEntity target;
    public FlySwordEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
        noPhysics = true;//穿墙
    }

    public FlySwordEntity(LivingEntity owner, int maxTickCount, LivingEntity target){
        super(SwordSoaringEntities.FLY_SWORD.get(), owner.getMainHandItem().copy(), owner);
        this.maxTickCount = maxTickCount;
        this.target = target;
        if(target != null && target.isAlive()){
            setPos(target.position());
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(ROTATION_LOCK, true);
        getEntityData().define(FLYING_BACK, false);
        getEntityData().define(READY_TO_FLY_BACK, false);
        getEntityData().define(ANIMATION_END, false);
    }

    public boolean isRotationLock(){
        return getEntityData().get(ROTATION_LOCK);
    }

    public void setRotationLock(boolean rotationLock){
        getEntityData().set(ROTATION_LOCK, rotationLock);
    }

    public boolean isAnimationEnd(){
        return getEntityData().get(ANIMATION_END);
    }
    public void setAnimationEnd(boolean flying){
        getEntityData().set(ANIMATION_END, flying);
    }

    public boolean isFlyingBack(){
        return getEntityData().get(FLYING_BACK);
    }

    public void setFlyingBack(boolean flying){
        getEntityData().set(FLYING_BACK, flying);
    }

    public boolean isReadyToFlyBack(){
        return getEntityData().get(READY_TO_FLY_BACK);
    }

    public void setReadyToFlyBack(boolean flying){
        getEntityData().set(READY_TO_FLY_BACK, flying);
    }

    public boolean callFlyingBack(){
        if(getPatch() instanceof FlySwordPatch flySwordPatch){
            if(flySwordPatch.getEntityState().inaction()){
                return false;
            }
            flySwordPatch.playAnimationSynchronized(FlySwordAnimations.FLY_SWORD_ATK_FLY_BACK, 0.001F);
            if(getOwner() != null){
                flySwordPatch.rotateTo(getOwner(), 30, true);
                setReadyToFlyBack(true);
            }
            return true;
        }
        return false;
    }

    @Override
    public LivingEntity getTarget() {
        return target;
    }

    /**
     * 最好只用一次
     */
    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    protected void moveToOwner(LivingEntity owner) {
        if(isFlyingBack()){
            Vec3 vec3 = AnimationUtils.getJointWorldPos(getPatch(), SwordSoaringArmatures.flySwordArmature.body);
            ParticleVFX.createSphereParticles(level,vec3,ParticleTypes.SMOKE,0.2,0.01,0.05,100);
            FlySwordAnimations.flySwordDamage(getPatch(),2,2.5F);
            if(!level.isClientSide){
                if(this.position().distanceTo(owner.getEyePosition()) < 1.5){
                    ((ServerLevel)level).sendParticles( ParticleTypes.SMOKE,getX(),getY(),getZ(),
                            300,
                            0.5,
                            0.5,
                            0.5,
                            0.5);
                    addOwnerSwordCount();
                    this.discard();
                    return;
                }
                Vec3 dir = owner.getEyePosition().subtract(this.getEyePosition()).normalize().scale(0.8F);
                setDeltaMovement(dir);//旋转在Patch里操作
            }
        } else {
            if(isReadyToFlyBack()){
                getPatch().rotateTo(owner, 30, true);
            } else if(isRotationLock()){
                setYRot(0);
                setYBodyRot(0);
                setYHeadRot(0);
            }
            if(!level.isClientSide){
                if(target != null && target.isAlive() && !isAnimationEnd()){
                    this.setPos(target.position());
                }
                if(tickCount == maxTickCount){
                    addOwnerSwordCount();
                    this.discard();
                }
            }
        }
    }

    public void addOwnerSwordCount(){
        if(getOwnerPatch() instanceof ServerPlayerPatch serverPlayerPatch){
            SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
            if(manager.hasData(VatanseverPassive.SWORD_COUNT)){
                int currentCnt = manager.getDataValue(VatanseverPassive.SWORD_COUNT);
                manager.setDataSync(VatanseverPassive.SWORD_COUNT, Math.min(currentCnt + 1, 6), serverPlayerPatch.getOriginal());
            }
        }
    }

    @Override
    protected Item getOriginalItem() {
        return null;
    }

    @Override
    protected boolean shouldRemoveWhenOwnerLost() {
        return false;
    }
}
