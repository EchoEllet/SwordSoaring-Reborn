package net.p1nero.ss.entity.wraithon.ai;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.p1nero.ss.entity.wraithon.WraithonEntity;

public class WraithonTargetSelector extends NearestAttackableTargetGoal<Player> {

    private int timer = 0;
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
        Player player = this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        timer++;
        if(this.target == null || !this.target.isAlive()){
            this.target = player;
            return;
        }
        if(player != this.target && timer >= 600){
            this.target = player;
            timer = 0;
        }

    }

}
