package net.p1nero.ss.entity.wraithon.ai;

import net.p1nero.ss.entity.wraithon.TargetInGirdCondition;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.*;

public class WraithonCombatBehaviors {
    public static final Builder<WraithonEntityPatch> PHASE1 = CombatBehaviors.<WraithonEntityPatch>builder()

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(1).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_IDLE)
                                    .predicate(new TargetInGirdCondition(1, 1)))
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(1).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder().behavior(wraithonEntityPatch -> {
                                //Target在右边右转，在左边左转
                                if(wraithonEntityPatch.isTargetInDegree(wraithonEntityPatch.getTarget(), 0, 180)){
                                    wraithonEntityPatch.turnRight();
                                } else {
                                    wraithonEntityPatch.turnLeft();
                                }
                            }))
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(1).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder().animationBehavior(WraithonAnimations.WRAITHON_IDLE).withinDistance(0.0F, 2.5F))
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder().animationBehavior(WraithonAnimations.WRAITHON_IDLE))
            );
}
