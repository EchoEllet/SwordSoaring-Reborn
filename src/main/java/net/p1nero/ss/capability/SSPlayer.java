package net.p1nero.ss.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.AttackAnimation;

import java.util.*;

/**
 * 记录飞行和技能使用的状态，被坑了，这玩意儿也分服务端和客户端...
 */
public class SSPlayer {
    private final Map<AttackAnimation.Phase, List<Entity>> phaseListMap = new HashMap<>();

    public Map<AttackAnimation.Phase, List<Entity>> getPhaseListMap() {
        return phaseListMap;
    }

    public List<Entity> getCurrentlyHurtEntities(AttackAnimation.Phase phase){
        List<Entity> toReturn = phaseListMap.get(phase);
        if(toReturn == null){
            List<Entity> newList = new ArrayList<>();
            phaseListMap.put(phase, newList);
            return newList;
        }
        return toReturn;
    }

    public void clearMap(){
        phaseListMap.clear();
    }

    private final ArrayList<FlySwordEntity> vatanseverShootEntities = new ArrayList<>();

    public ArrayList<FlySwordEntity> getVatanseverShootEntities() {
        return vatanseverShootEntities;
    }

    public void addVatanseverShootEntity(@NotNull FlySwordEntity flySwordEntity){
        vatanseverShootEntities.add(flySwordEntity);
    }

    public void clearVatanseverShootEntities(){
        vatanseverShootEntities.clear();
    }

    public void saveNBTData(CompoundTag tag){

    }

    public void loadNBTData(CompoundTag tag){

    }

    public void copyFrom(SSPlayer old){

    }

}
