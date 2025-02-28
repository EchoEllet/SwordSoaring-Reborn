package net.p1nero.ss.entity.vatansever;

import com.google.common.collect.ImmutableList;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VatanseverArmature extends Armature {
    public final Joint L1, L2, L3, R1, R2, R3;
    public final List<Joint> joints;
    public VatanseverArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
        L1 = getOrLogException(jointMap, "S_1_L");
        L2 = getOrLogException(jointMap, "S_2_L");
        L3 = getOrLogException(jointMap, "S_3_L");
        R1 = getOrLogException(jointMap, "S_1_R");
        R2 = getOrLogException(jointMap, "S_2_R");
        R3 = getOrLogException(jointMap, "S_3_R");
        joints = ImmutableList.of(R3, L3, R2, L2, R1, L1);
//        joints = ImmutableList.of(L3, R3, L2, R2, L1, R1);
    }

    public List<Joint> getInvalidJoints(PlayerPatch<?> ownerPatch){
        List<Joint> invalidJoints = new ArrayList<>();
        SkillDataManager manager = ownerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
        if(manager.hasData(VatanseverPassive.SWORD_COUNT)){
            for (int i = manager.getDataValue(VatanseverPassive.SWORD_COUNT); i < 6; i++) {
                invalidJoints.add(joints.get(5 - i));
            }
        }
        return invalidJoints;
    }

}