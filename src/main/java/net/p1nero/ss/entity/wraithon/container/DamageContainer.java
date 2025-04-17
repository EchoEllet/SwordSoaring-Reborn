package net.p1nero.ss.entity.wraithon.container;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.p1nero.ss.entity.wraithon.WraithonEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DamageContainer {
    private final WraithonEntity wraithon;
    @Nullable
    private TagKey<DamageType> tagKey;
    @Nullable
    private List<ResourceKey<DamageType>> damageTypes;
    private final EntityDataAccessor<Float> containerAccessor;
    public final int maxValue;
    public final int stateCode;
    private boolean fulled;

    public DamageContainer(WraithonEntity wraithon, @Nullable TagKey<DamageType> tagKey, EntityDataAccessor<Float> containerAccessor, int maxValue, int stateCode) {
        this.wraithon = wraithon;
        this.tagKey = tagKey;
        this.containerAccessor = containerAccessor;
        this.maxValue = maxValue;
        this.stateCode = stateCode;
    }

    public DamageContainer(WraithonEntity wraithon, @Nullable List<ResourceKey<DamageType>> damageTypes, EntityDataAccessor<Float> containerAccessor, int maxValue, int stateCode) {
        this.wraithon = wraithon;
        this.damageTypes = damageTypes;
        this.containerAccessor = containerAccessor;
        this.maxValue = maxValue;
        this.stateCode = stateCode;
    }

    public boolean containDamage(DamageSource damageSource) {
        if (tagKey != null && damageSource.is(tagKey)) {
            return true;
        } else {
            if(damageTypes != null){
                for (ResourceKey<DamageType> damageTypeResourceKey : damageTypes) {
                    if(damageSource.is(damageTypeResourceKey)){
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public void setContainerValue(float value) {
        if(value >= maxValue) {
            fulled = true;
            this.wraithon.setState(stateCode);
        }
        wraithon.getEntityData().set(containerAccessor, value);
    }

    public float getContainerValue() {
        return wraithon.getEntityData().get(containerAccessor);
    }

    public boolean onHurt(DamageSource damageSource, float value) {
        System.out.println(wraithon.getState() + " " + stateCode);
        if (wraithon.getState() == stateCode) {
            //如果已处于此状态
            wraithon.heal(value * 0.3F);

        } else if (wraithon.getState() == WraithonEntity.DEFAULT_STATE) {
            //未处于状态则加条
            if (containDamage(damageSource)) {
                float receivedValue = value * 0.7F;
                float newValue = getContainerValue() + receivedValue;
                setContainerValue(newValue);
                float healRate = switch (this.wraithon.getPhase()) {
                    case WraithonEntity.PHASE1 -> 0.2F;
                    case WraithonEntity.PHASE2 -> 0.5F;
                    default -> 0.3F;
                };
                return wraithon.hurt(damageSource, value * healRate);
            }
        }
        return false;
    }

    public void onTick() {
        //1s计算一次
        if (wraithon.tickCount % 20 == 0) {
            if (fulled && getContainerValue() >= 0) {
                setContainerValue(getContainerValue() - 1);
                if (getContainerValue() == 0) {
                    clearContainer();
                }
            }
        }
    }

    /**
     * 时间过久或释放适应技后调用
     */
    public void clearContainer() {
        fulled = false;
        wraithon.updateState();
        switch (this.wraithon.getPhase()) {
            case WraithonEntity.PHASE0 -> wraithon.getEntityData().set(containerAccessor, 0.0F);
            case WraithonEntity.PHASE1 -> wraithon.getEntityData().set(containerAccessor, maxValue / 2.0F);
            default -> {}
        }
    }

    public boolean isFullState() {
        return fulled;
    }

}