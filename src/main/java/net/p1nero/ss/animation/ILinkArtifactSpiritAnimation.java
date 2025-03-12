package net.p1nero.ss.animation;

import net.minecraft.world.entity.Entity;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public interface ILinkArtifactSpiritAnimation {
    default void callArtifactSpiritAnimation(LivingEntityPatch<?> ownerPatch){
        if(ownerPatch instanceof ServerPlayerPatch serverPlayerPatch){
            SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
            if(manager.hasData(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get())){
                Entity entity = serverPlayerPatch.getOriginal().level().getEntity(manager.getDataValue(SwordSoaringDatakeys.ARTIFACT_SPIRIT_ENTITY_ID.get()));
                if(entity != null){
                    AbstractArtifactSpiritPatch<?> spiritPatch = EpicFightCapabilities.getEntityPatch(entity, AbstractArtifactSpiritPatch.class);
                    if(spiritPatch != null && getArtifactSpiritAnimation() != null){
                        spiritPatch.playAnimationSynchronized(getArtifactSpiritAnimation(), getConvertTime());
                    }
                }
            }
        }
    }
    StaticAnimation setArtifactSpiritAnimation(AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation);
    AnimationManager.AnimationAccessor<? extends StaticAnimation> getArtifactSpiritAnimation();

    float getConvertTime();

}
