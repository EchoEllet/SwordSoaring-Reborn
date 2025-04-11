package net.p1nero.ss.entity.wraithon.ai;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.p1nero.ss.entity.wraithon.WraithonEntity;

public class WraithonTargetSelector extends NearestAttackableTargetGoal<Player> {

    public WraithonTargetSelector(WraithonEntity wraithonEntity) {
        super(wraithonEntity, Player.class, false);
    }

    @Override
    public boolean canUse() {
        this.findTarget();
        return this.target != null;
    }

    @Override
    protected void findTarget() {
        this.target = this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

}
