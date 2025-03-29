package net.p1nero.ss.animation;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class LinkArtifactSpiritDodgeAnimation extends DodgeAnimation implements ILinkArtifactSpiritAnimation{
    private AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation;

    public LinkArtifactSpiritDodgeAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends DodgeAnimation> accessor, float width, float height, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, width, height, armature);
    }

    public LinkArtifactSpiritDodgeAnimation(float transitionTime, float delayTime, AnimationManager.AnimationAccessor<? extends DodgeAnimation> accessor, float width, float height, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, delayTime, accessor, width, height, armature);
    }

    @Override
    public void begin(LivingEntityPatch<?> entityPatch) {
        super.begin(entityPatch);
        this.callArtifactSpiritAnimation(entityPatch);
    }

    @Override
    public LinkArtifactSpiritDodgeAnimation setArtifactSpiritAnimation(AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation) {
        this.artifactSpiritAnimation = artifactSpiritAnimation;
        return this;
    }

    @Override
    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getArtifactSpiritAnimation() {
        return artifactSpiritAnimation;
    }

    @Override
    public float getConvertTime() {
        return this.transitionTime;
    }

}
