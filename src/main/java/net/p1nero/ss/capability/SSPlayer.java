package net.p1nero.ss.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p1nero.ss.SwordSoaringConfig;
import net.p1nero.ss.entity.sword.fly_sword.FlySwordEntity;
import net.p1nero.ss.network.packet.client.SyncBabylonPacket;
import net.p1nero.ss.utils.ItemUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.registry.EpicFightRegistries;
import yesman.epicfight.skill.Skill;

import java.util.*;

/**
 * 记录飞行和技能使用的状态
 */
public class SSPlayer implements INBTSerializable<CompoundTag> {
    @Nullable
    private Skill lastDodgeSkill;

    public void setLastDodgeSkill(@Nullable Skill lastDodgeSkill) {
        this.lastDodgeSkill = lastDodgeSkill;
    }

    public @Nullable Skill getLastDodgeSkill() {
        return lastDodgeSkill;
    }

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

    private ArrayList<ItemStack> validBabylonItems = new ArrayList<>();

    /**
     * 初始化王财列表，并返回物品数
     */
    public int initBabylonItems(ServerPlayer player){
        validBabylonItems = ItemUtils.calculateValidBabylonItems(player, SwordSoaringConfig.REMOVE_ITEM.get());
        PacketDistributor.sendToPlayer(player, new SyncBabylonPacket(player.getId(), validBabylonItems));
        return validBabylonItems.size();
    }

    public void updateBabylonItems(ArrayList<ItemStack> newItems){
        validBabylonItems = newItems;
    }

    public ArrayList<ItemStack> getValidBabylonItems() {
        return validBabylonItems;
    }

    private ArrayList<ItemStack> wanSwordList;

    public void setWanSwordList(ArrayList<ItemStack> wanSwordList) {
        this.wanSwordList = wanSwordList;
    }

    public ArrayList<ItemStack> getWanSwordList() {
        return wanSwordList;
    }

    public void saveNBTData(CompoundTag tag){
        if(lastDodgeSkill != null){
            tag.putString("last_dodge_skill", lastDodgeSkill.toString());
        }
    }

    public void loadNBTData(CompoundTag tag){
        lastDodgeSkill = EpicFightRegistries.SKILL.get(ResourceLocation.parse(tag.getString("last_dodge_skill")));
    }

    public void copyFrom(SSPlayer old){
        this.lastDodgeSkill = old.lastDodgeSkill;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag compoundTag) {
        loadNBTData(compoundTag);
    }
}
