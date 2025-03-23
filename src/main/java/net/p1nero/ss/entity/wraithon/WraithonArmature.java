package net.p1nero.ss.entity.wraithon;

import com.google.common.collect.ImmutableList;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.List;
import java.util.Map;

public class WraithonArmature extends Armature {
    public final Joint WEAPON;




    public WraithonArmature(String name, int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(name, jointNumber, rootJoint, jointMap);
        WEAPON = getOrLogException(jointMap, "weapon");

    }
}
