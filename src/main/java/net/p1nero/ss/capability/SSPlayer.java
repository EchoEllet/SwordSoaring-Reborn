package net.p1nero.ss.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.p1nero.ss.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 记录飞行和技能使用的状态，被坑了，这玩意儿也分服务端和客户端...
 */
public class SSPlayer {
    private final ArrayList<Integer> screenSwordIds = new ArrayList<>();

    public void addScreenSword(int id){
        screenSwordIds.add(id);
    }

    public ArrayList<Integer> getScreenSwordIds() {
        return screenSwordIds;
    }

    public void saveNBTData(CompoundTag tag){

    }

    public void loadNBTData(CompoundTag tag){

    }

    public void copyFrom(SSPlayer old){

    }

}
