package net.p1nero.ss.entity.wraithon.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import yesman.epicfight.api.utils.math.MathUtils;

public class WraithonChaseGoal extends Goal {

    private final WraithonEntityPatch wraithonEntityPatch;
    private final float dis;

    public WraithonChaseGoal(WraithonEntityPatch wraithonEntityPatch, float dis){
        this.wraithonEntityPatch = wraithonEntityPatch;
        this.dis = dis;
    }

    @Override
    public boolean canUse() {
        return !wraithonEntityPatch.getEntityState().inaction() && wraithonEntityPatch.getTarget() != null && wraithonEntityPatch.getTarget().distanceTo(wraithonEntityPatch.getOriginal()) >= dis;
    }

    @Override
    public void tick() {
        if(wraithonEntityPatch.getEntityState().inaction() || wraithonEntityPatch.isRotating()){
            return;
        }
        Vec3 playerPosition = wraithonEntityPatch.getOriginal().position();
        Vec3 targetPosition = wraithonEntityPatch.getTarget().position();
        double yaw = MathUtils.getYRotOfVector(targetPosition.subtract(playerPosition));
        double delta = yaw - MathUtils.getYRotOfVector(wraithonEntityPatch.getOriginal().getViewVector(1.0F));
        if(Math.abs(delta) < 10) {
            wraithonEntityPatch.getOriginal().setDeltaMovement(wraithonEntityPatch.getTarget().position().subtract(wraithonEntityPatch.getOriginal().position()).normalize().scale(0.4F));
        }
        if(Math.abs(delta) < 30) {
            wraithonEntityPatch.rotateTo(wraithonEntityPatch.getTarget(), 10, true);
        } else {
            if(delta > 0 && delta < 180){
                wraithonEntityPatch.turnRight();
            } else {
                wraithonEntityPatch.turnLeft();
            }
        }
    }
}
