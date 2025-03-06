package net.p1nero.ss.entity.sword.gate_of_babylon;

import com.google.common.collect.ImmutableList;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.IReplaceableArmature;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.skill.sword_controller.ScreenSwordSkill;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BabylonArmature extends Armature implements IReplaceableArmature {
    public final Joint W001, W002, W003, W004, W005, W006, W007, W008, W009, W010,
            W011, W012, W013, W014, W015, W016, W017, W018, W019, W020,
            W021, W022, W023, W024, W025, W026, W027, W028, W029, W030,
            W031, W032, W033, W034, W035, W036, W037, W038, W039, W040,
            W041, W042, W043, W044;

    public final ImmutableList<Joint> joints;
    public BabylonArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
        W001 = getOrLogException(jointMap, "W.001");
        W002 = getOrLogException(jointMap, "W.002");
        W003 = getOrLogException(jointMap, "W.003");
        W004 = getOrLogException(jointMap, "W.004");
        W005 = getOrLogException(jointMap, "W.005");
        W006 = getOrLogException(jointMap, "W.006");
        W007 = getOrLogException(jointMap, "W.007");
        W008 = getOrLogException(jointMap, "W.008");
        W009 = getOrLogException(jointMap, "W.009");
        W010 = getOrLogException(jointMap, "W.010");
        W011 = getOrLogException(jointMap, "W.011");
        W012 = getOrLogException(jointMap, "W.012");
        W013 = getOrLogException(jointMap, "W.013");
        W014 = getOrLogException(jointMap, "W.014");
        W015 = getOrLogException(jointMap, "W.015");
        W016 = getOrLogException(jointMap, "W.016");
        W017 = getOrLogException(jointMap, "W.017");
        W018 = getOrLogException(jointMap, "W.018");
        W019 = getOrLogException(jointMap, "W.019");
        W020 = getOrLogException(jointMap, "W.020");
        W021 = getOrLogException(jointMap, "W.021");
        W022 = getOrLogException(jointMap, "W.022");
        W023 = getOrLogException(jointMap, "W.023");
        W024 = getOrLogException(jointMap, "W.024");
        W025 = getOrLogException(jointMap, "W.025");
        W026 = getOrLogException(jointMap, "W.026");
        W027 = getOrLogException(jointMap, "W.027");
        W028 = getOrLogException(jointMap, "W.028");
        W029 = getOrLogException(jointMap, "W.029");
        W030 = getOrLogException(jointMap, "W.030");
        W031 = getOrLogException(jointMap, "W.031");
        W032 = getOrLogException(jointMap, "W.032");
        W033 = getOrLogException(jointMap, "W.033");
        W034 = getOrLogException(jointMap, "W.034");
        W035 = getOrLogException(jointMap, "W.035");
        W036 = getOrLogException(jointMap, "W.036");
        W037 = getOrLogException(jointMap, "W.037");
        W038 = getOrLogException(jointMap, "W.038");
        W039 = getOrLogException(jointMap, "W.039");
        W040 = getOrLogException(jointMap, "W.040");
        W041 = getOrLogException(jointMap, "W.041");
        W042 = getOrLogException(jointMap, "W.042");
        W043 = getOrLogException(jointMap, "W.043");
        W044 = getOrLogException(jointMap, "W.044");
        //你问为什么没有009？因为猪鼻merlin把它掰弯了，含泪抛弃
        joints = ImmutableList.of(W001, W002, W003, W004, W005, W006, W007, W008, W010,
                W011, W012, W013, W014, W015, W016, W017, W018, W019, W020,
                W021, W022, W023, W024, W025, W026, W027, W028, W029, W030,
                W031, W032, W033, W034, W035, W036, W037, W038, W039, W040,
                W041, W042, W043, W044);
    }

    @Override
    public List<Joint> getJoints(LivingEntityPatch<?> livingEntityPatch) {
        return joints;
    }
}