package net.p1nero.ss.entity.vatansever_storm;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.Map;

public class VatanseverStormArmature extends Armature {
    public final Joint R101, R102, R103, R104, R105, R106, R107, R108,
            R109, R110, R111, R112, R113, R114, R115, R116;

    public VatanseverStormArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
        R101 = getOrLogException(jointMap, "root_1");
        R102 = getOrLogException(jointMap, "root_1.001");
        R103 = getOrLogException(jointMap, "root_1.002");
        R104 = getOrLogException(jointMap, "root_1.003");
        R105 = getOrLogException(jointMap, "root_1.004");
        R106 = getOrLogException(jointMap, "root_1.005");
        R107 = getOrLogException(jointMap, "root_1.006");
        R108 = getOrLogException(jointMap, "root_1.007");
        R109 = getOrLogException(jointMap, "root_1.008");
        R110 = getOrLogException(jointMap, "root_1.009");
        R111 = getOrLogException(jointMap, "root_1.010");
        R112 = getOrLogException(jointMap, "root_1.011");
        R113 = getOrLogException(jointMap, "root_1.012");
        R114 = getOrLogException(jointMap, "root_1.013");
        R115 = getOrLogException(jointMap, "root_1.014");
        R116 = getOrLogException(jointMap, "root_1.015");
    }
}