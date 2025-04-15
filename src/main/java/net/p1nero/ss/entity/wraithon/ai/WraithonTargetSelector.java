package net.p1nero.ss.entity.wraithon.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.p1nero.ss.entity.wraithon.WraithonEntity;

import java.util.List;

public class WraithonTargetSelector extends NearestAttackableTargetGoal<LivingEntity> {

    private int timer = 0;

    public WraithonTargetSelector(WraithonEntity wraithonEntity) {
        super(wraithonEntity, LivingEntity.class, false);
        this.targetConditions = TargetingConditions.forCombat()
                .range(this.getFollowDistance())
                .selector(livingEntity -> {
                    // 这里可以添加额外的筛选条件
                    return livingEntity instanceof LivingEntity;
                });
    }

    @Override
    public boolean canUse() {
        this.findTarget();
        return this.target != null;
    }

    @Override
    protected void findTarget() {

        List<LivingEntity> targets = this.mob.level()
                .getEntitiesOfClass(LivingEntity.class,
                        this.mob.getBoundingBox().inflate(this.getFollowDistance())
                );

        LivingEntity nearest = null;
        double closestDistance = Double.MAX_VALUE;

        for (LivingEntity entity : targets) {
            double distance = this.mob.distanceToSqr(entity);
            if (distance < closestDistance) {
                nearest = entity;
                closestDistance = distance;
            }
        }

        timer++;
        if (this.target == null || !this.target.isAlive()) {
            this.target = nearest;
            return;
        }

        if (nearest != null && nearest != this.target && timer >= 600) {
            this.target = nearest;
            timer = 0;
        }
    }
}
