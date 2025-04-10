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
    public final Joint hand_l;
    public final Joint leg_F_3_R;
    public final Joint leg_M_3_R;
    public final Joint leg_B_3_R;
    public final Joint leg_F_3_L;
    public final Joint leg_M_3_L;
    public final Joint leg_B_3_L;
    public final Joint root;
    public final Joint weapon_s;
    public final Joint ROT;
    public final Map<String, Joint> partJointMap;
    public WraithonArmature(String name, int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(name, jointNumber, rootJoint, jointMap);
        partJointMap = new HashMap<>();
        ROT = getOrLogException(jointMap, "ROT");
        weapon = getOrLogException(jointMap, "weapon_r");
        root = getOrLogException(jointMap, "Root");
        weapon_s = getOrLogException(jointMap, "weapon_S");
        head = getAndAddToPartJointMap(jointMap, "head");
        chest = getAndAddToPartJointMap(jointMap, "chest");
        tail = getAndAddToPartJointMap(jointMap, "tail_2");
        hand_l = getAndAddToPartJointMap(jointMap, "hand_L");
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
