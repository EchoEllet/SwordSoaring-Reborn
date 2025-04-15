package net.p1nero.ss.entity.wraithon.ai;

import net.p1nero.ss.entity.wraithon.TargetInGirdCondition;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.*;


public class WraithonCombatBehaviors {


    public static final Builder<WraithonEntityPatch> PHASE1 = CombatBehaviors.<WraithonEntityPatch>builder()

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_1)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(5, 3, 7, 7),
                                            new TargetInGirdCondition.Rectangle(-6, 8, 4, 10),
                                            new TargetInGirdCondition.Rectangle(-5, 4, -8, 7)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_2)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(5, 3, 7, 7),
                                            new TargetInGirdCondition.Rectangle(-5, 8, 4, 10),
                                            new TargetInGirdCondition.Rectangle(-5, 3, -7, 6)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(30).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_3)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(-1, -6, 2, -8)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_4)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(-5, -7, -4, 2),
                                            new TargetInGirdCondition.Rectangle(-8, -5, -4, 6)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_5)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(-4, -2, -5, 3),
                                            new TargetInGirdCondition.Rectangle(-1, -3, -4, -4),
                                            new TargetInGirdCondition.Rectangle(5, -5, 0, -6)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_6)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(3, 2, 6, 4),
                                            new TargetInGirdCondition.Rectangle(-4, 4, 3, 7),
                                            new TargetInGirdCondition.Rectangle(-5, 4, -6, 5)
                                    ))
                            )
            )
            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_7)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(5, -7, 4, 3),
                                            new TargetInGirdCondition.Rectangle(8, -5, 4, 1)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_8)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(5, 5, 7, 6),
                                            new TargetInGirdCondition.Rectangle(-5, 8, 4, 11)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_9)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(3, -3, 4, -5)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(50).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_10)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(-5, 3, 4, 3),
                                            new TargetInGirdCondition.Rectangle(-6, 0, -6, 2)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_11)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(-6, -7, 5, -9)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(10).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_12)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(5, 6, 7, 9),
                                            new TargetInGirdCondition.Rectangle(-6, 12, 4, 13)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(1).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_LEG_1)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(3, 1, -3, 4)
                                    ))
                            )
            )
            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(1).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_LEG_2)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(0, 1, -3, 4)
                                    ))
                            )
            )
            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(1).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_LEG_3)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(3, 1, 0, 4)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(30).cooldown(200).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_JUMP_B)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(4, -4, -4, 4)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(30).cooldown(200).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_JUMP_R)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(4, -4, -4, 4)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(30).cooldown(200).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_JUMP_L)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(4, -4, -4, 4)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(30).cooldown(200).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_JUMP_B_ATK)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(3, -3, -3, 3)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(30).cooldown(200).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_JUMP_R_ATK)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(3, -3, -3, 3)
                                    ))
                            )
            )

            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(30).cooldown(200).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_JUMP_L_ATK)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(3, -3, -3, 3)
                                    ))
                            )
            )





            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(30).cooldown(30).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder()
                                    .animationBehavior(WraithonAnimations.WRAITHON_13)
                                    .predicate(new TargetInGirdCondition(
                                            new TargetInGirdCondition.Rectangle(-1, 12, 1, 14)
                                    ))
                            )
            )







            .newBehaviorSeries(
                    BehaviorSeries.<WraithonEntityPatch>builder().weight(1).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder().animationBehavior(WraithonAnimations.WRAITHON_IDLE).withinDistance(0.0F, 2.5F))
                            .nextBehavior(Behavior.<WraithonEntityPatch>builder().animationBehavior(WraithonAnimations.WRAITHON_IDLE))
            );
}
