package net.p1nero.ss.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.CapabilityItemHandler;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.network.PacketRelay;
import net.p1nero.ss.network.packet.client.RequestValidBabylonSyncPacket;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
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
        Iterator<FlySwordEntity> iterator = vatanseverShootEntities.iterator();
        while (iterator.hasNext()){
            FlySwordEntity flySwordEntity = iterator.next();
            if(flySwordEntity != null && flySwordEntity.isAlive()){
                flySwordEntity.discard();
            }
            iterator.remove();
        }
    }

    public void saveNBTData(CompoundTag tag){

    }

    public void loadNBTData(CompoundTag tag){

    }

    public void copyFrom(SSPlayer old){

    }

}
