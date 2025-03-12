package net.p1nero.ss.entity.sword.fly_sword;

import net.p1nero.ss.entity.ReplaceableArmature;
import yesman.epicfight.api.animation.Joint;

import java.util.Map;

public class FlySwordArmature extends ReplaceableArmature {
    public final Joint body;

    public FlySwordArmature(String name, int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(name, jointNumber, rootJoint, jointMap);
        body = getOrLogException(jointMap, "root");
        joints.add(body);
    }
}