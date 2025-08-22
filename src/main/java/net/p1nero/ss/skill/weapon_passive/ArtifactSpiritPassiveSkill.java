package net.p1nero.ss.skill.weapon_passive;

import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;

public abstract class ArtifactSpiritPassiveSkill extends Skill {

    public ArtifactSpiritPassiveSkill(SkillBuilder<?> builder) {
        super(builder);
    }

    public int getArtifactSpiritId(SkillContainer container){
        if(container.getDataManager().hasData(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID)){
            return container.getDataManager().getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID);
        }
        return 0;
    }

}
