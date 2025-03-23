package net.p1nero.ss.entity.wraithon;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.HashMap;
import java.util.Map;

public class WraithonArmature extends Armature {
    public final Joint weapon;
    public final Joint head;
    public final Joint chest;
    public final Joint tail;
    public final Joint leg_F_3_R;
    public final Joint leg_M_3_R;
    public final Joint leg_B_3_R;
    public final Joint leg_F_3_L;
    public final Joint leg_M_3_L;
    public final Joint leg_B_3_L;
    public final Map<String, Joint> partJointMap;
    public WraithonArmature(String name, int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(name, jointNumber, rootJoint, jointMap);
        partJointMap = new HashMap<>();
        weapon = getOrLogException(jointMap, "weapon");
        head = getAndAddToPartJointMap(jointMap, "head");
        chest = getAndAddToPartJointMap(jointMap, "chest");
        tail = getAndAddToPartJointMap(jointMap, "tail_2");
        leg_F_3_R = getAndAddToPartJointMap(jointMap, "leg_F_3_R");
        leg_M_3_R = getAndAddToPartJointMap(jointMap, "leg_M_3_R");
        leg_B_3_R = getAndAddToPartJointMap(jointMap, "leg_B_3_R");
        leg_F_3_L = getAndAddToPartJointMap(jointMap, "leg_F_3_L");
        leg_M_3_L = getAndAddToPartJointMap(jointMap, "leg_M_3_L");
        leg_B_3_L = getAndAddToPartJointMap(jointMap, "leg_B_3_L");
    }

    protected Joint getAndAddToPartJointMap(Map<String, Joint> jointMap, String name) {
        Joint joint = super.getOrLogException(jointMap, name);
        partJointMap.put(name, joint);
        return joint;
    }
}
