package net.p1nero.ss.entity;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public interface IReplaceableArmature {
    List<Joint> getJoints(LivingEntityPatch<?> livingEntityPatch);
}
