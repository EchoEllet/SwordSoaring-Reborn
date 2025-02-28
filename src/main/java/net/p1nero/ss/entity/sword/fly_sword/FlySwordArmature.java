package net.p1nero.ss.entity.sword.fly_sword;

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

public class FlySwordArmature extends Armature implements IReplaceableArmature {
    public final Joint body;
    public final ImmutableList<Joint> joints;
    public FlySwordArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
        body = getOrLogException(jointMap, "root");
        joints = ImmutableList.of(body);
    }

    @Override
    public List<Joint> getJoints(LivingEntityPatch<?> livingEntityPatch) {
        return joints;
    }
}