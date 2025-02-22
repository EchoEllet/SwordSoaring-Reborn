package net.p1nero.ss.skill.weapon_passive;

import net.minecraft.server.level.ServerPlayer;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;

public class VatanseverPassive extends ArtifactSpiritPassiveSkill{
    public static SkillDataManager.SkillDataKey<Integer> SWORD_COUNT = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);

    public VatanseverPassive(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(SWORD_COUNT);
        if(!container.getExecuter().isLogicalClient() && container.getDataManager().getDataValue(ARTIFACT_SPIRIT_ENTITY_ID) == 0){
            VatanseverEntity vatanseverEntity = new VatanseverEntity(container.getExecuter().getOriginal().level, container.getExecuter().getOriginal());
            container.getExecuter().getOriginal().level.addFreshEntity(vatanseverEntity);
            container.getDataManager().setDataSync(ARTIFACT_SPIRIT_ENTITY_ID, vatanseverEntity.getId(), ((ServerPlayer) container.getExecuter().getOriginal()));
            container.getDataManager().setDataSync(SWORD_COUNT, 6, ((ServerPlayer) container.getExecuter().getOriginal()));
        }
    }
}
