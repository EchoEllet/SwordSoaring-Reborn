package net.p1nero.ss.entity.wraithon.container;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.wraithon.WraithonEntity;

import java.util.List;

public class DamageContainer {
    private final WraithonEntity wraithon;
    private final List<ResourceKey<DamageType>> damageTypes;
    private final EntityDataAccessor<Float> containerAccessor;
    public final int maxValue;
    public final int stateCode;

    public Vec3 targetPos;
    public float radius;

    public DamageContainer(WraithonEntity wraithon, List<ResourceKey<DamageType>> damageTypes, EntityDataAccessor<Float> containerAccessor, int maxValue, int stateCode, Vec3 targetPos, float radius) {
        this.wraithon = wraithon;
        this.damageTypes = damageTypes;
        this.containerAccessor = containerAccessor;
        this.maxValue = maxValue;
        this.stateCode = stateCode;
        this.targetPos = targetPos;
        this.radius = radius;
    }

    public boolean containDamage(DamageSource damageSource){
        for(ResourceKey<DamageType> damageTypeResourceKey : damageTypes){
            if(damageSource.is(damageTypeResourceKey)){
                return true;
            }
        }
        return false;
    }

    public boolean onHurt(DamageSource damageSource, float value){
        if(wraithon.getState() == stateCode){
            //如果已处于此状态
            wraithon.heal(value * 0.3F);

        } else if (wraithon.getState() == WraithonEntity.DEFAULT_STATE){
            //未处于状态则加条
            if(containDamage(damageSource)){
                float receivedValue = value * 0.7F;
                wraithon.getEntityData().set(containerAccessor, wraithon.getEntityData().get(containerAccessor) + receivedValue);
                wraithon.hurt(damageSource, value * 0.3F);
                return true;
            }
        }
        return false;
    }

    public void onTick(){
        //TODO 每tick减1容量，目标在范围内则释放对应技和清空条
    }

    public boolean isFull(){
        return wraithon.getEntityData().get(containerAccessor) == maxValue;
    }

}