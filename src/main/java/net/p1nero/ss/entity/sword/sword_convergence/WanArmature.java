package net.p1nero.ss.entity.sword.sword_convergence;

import net.p1nero.ss.entity.ReplaceableArmature;
import yesman.epicfight.api.animation.Joint;

import java.util.Map;

public class WanArmature extends ReplaceableArmature {
    public WanArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
        for(int i = 1; i <= 255; i++){
            String name = "s." + String.format("%03d", i);
            if(jointMap.containsKey(name)){
                joints.add(jointMap.get(name));
            }
        }
    }
}
