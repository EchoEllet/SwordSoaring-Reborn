package net.p1nero.ss.entity.sword.screen_sword;

import com.google.common.collect.ImmutableList;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.IReplaceableArmature;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.skill.sword_controller.ScreenSwordSkill;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.ArrayList;
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
    public List<Joint> getJoints(LivingEntityPatch<?> livingEntityPatch) {
        if(livingEntityPatch instanceof AbstractArtifactSpiritPatch<?> artifactSpiritPatch && artifactSpiritPatch.getOwnerPatch() != null){
            if(artifactSpiritPatch.getOwnerPatch().getSkill(SwordSoaringSkillSlots.SWORD_CONTROLLER).getSkill() instanceof ScreenSwordSkill skill){
                SkillDataManager manager = artifactSpiritPatch.getOwnerPatch().getSkill(SwordSoaringSkillSlots.SWORD_CONTROLLER).getDataManager();
                if(manager.hasData(ScreenSwordSkill.PROTECT_COUNT)){
                    float maxJoint = (manager.getDataValue(ScreenSwordSkill.PROTECT_COUNT) * 1.0F / skill.getMaxProtectCount()) * 6;
                    ArrayList<Joint> toReturn = new ArrayList<>();
                    for(int i = 0; i < maxJoint; i++){
                        if(i < joints.size()){
                            toReturn.add(joints.get(i));
                        }
                    }
                    return toReturn;
                }
            }
        }

        return joints;
    }
}