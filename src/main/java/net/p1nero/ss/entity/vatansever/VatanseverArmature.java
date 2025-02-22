package net.p1nero.ss.entity.vatansever;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.Map;

public class VatanseverArmature extends Armature {
    public final Joint L1, L2, L3, R1, R2, R3;
    public VatanseverArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
        L1 = getOrLogException(jointMap, "S_1_L");
        L2 = getOrLogException(jointMap, "S_2_L");
        L3 = getOrLogException(jointMap, "S_3_L");
        R1 = getOrLogException(jointMap, "S_1_R");
        R2 = getOrLogException(jointMap, "S_2_R");
        R3 = getOrLogException(jointMap, "S_3_R");
    }

}