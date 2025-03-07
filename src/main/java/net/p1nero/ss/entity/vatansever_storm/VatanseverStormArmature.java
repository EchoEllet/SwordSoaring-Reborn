package net.p1nero.ss.entity.vatansever_storm;

import net.p1nero.ss.entity.ReplaceableArmature;
import yesman.epicfight.api.animation.Joint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VatanseverStormArmature extends ReplaceableArmature {
    public final List<Joint> rootJoints = new ArrayList<>();

    public VatanseverStormArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
        rootJoints.add(getOrLogException(jointMap, "root_1"));
        for(int i = 1; i <= 255; i++){
            if(i <= 15){
                rootJoints.add(getOrLogException(jointMap, "root_1." + String.format("%03d", i)));
            }
            joints.add(getOrLogException(jointMap, "s." + String.format("%03d", i)));
        }
    }

}