package net.p1nero.ss.entity.screen_sword;

import com.google.common.collect.ImmutableList;
import net.p1nero.ss.entity.IReplaceableArmature;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.List;
import java.util.Map;

public class ScreenSwordArmature extends Armature implements IReplaceableArmature {
    public final Joint W1, W2, W3, W4, W5, W6;
    public final ImmutableList<Joint> joints;
    public ScreenSwordArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
        W1 = getOrLogException(jointMap, "W_1");
        W2 = getOrLogException(jointMap, "W_2");
        W3 = getOrLogException(jointMap, "W_3");
        W4 = getOrLogException(jointMap, "W_4");
        W5 = getOrLogException(jointMap, "W_5");
        W6 = getOrLogException(jointMap, "W_6");
        joints = ImmutableList.of(W1, W2, W3, W4, W5, W6);
    }

    @Override
    public List<Joint> getJoints() {
        return joints;
    }
}