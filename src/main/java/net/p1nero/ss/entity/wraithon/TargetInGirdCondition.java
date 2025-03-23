package net.p1nero.ss.entity.wraithon;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.data.conditions.Condition;

import java.util.List;

public class TargetInGirdCondition implements Condition<WraithonEntityPatch> {

    private final int x, y;

    public TargetInGirdCondition(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public Condition<WraithonEntityPatch> read(CompoundTag compoundTag) throws IllegalArgumentException {
        return null;
    }

    @Override
    public CompoundTag serializePredicate() {
        return null;
    }

    @Override
    public boolean predicate(WraithonEntityPatch wraithonEntityPatch) {
        LivingEntity target = wraithonEntityPatch.getTarget();
        // 获取BOSS和实体的位置
        Vec3 bossPos = wraithonEntityPatch.getOriginal().position();
        Vec3 targetPos = target.position();

        // 计算相对位移
        double dx = targetPos.x - bossPos.x;
        double dz = targetPos.z - bossPos.z;

        // 转换到BOSS的局部坐标系
        double theta = Math.toRadians(wraithonEntityPatch.getOriginal().getYRot());
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);
        double localX = dx * cosTheta + dz * sinTheta;
        double localZ = -dx * sinTheta + dz * cosTheta;

        // 检查是否在网格范围内
        if (localX < -7.5 || localX >= 7.5 || localZ < -7.5 || localZ >= 7.5) {
            return false;
        }

        // 计算格子编号
        int gridX = (int) Math.floor(localX + 7.5) + 1;
        int gridY = (int) Math.floor(7.5 - localZ) + 1;

        // 判断是否匹配目标格子
        return gridX == x && gridY == y;
    }

    @Override
    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}
