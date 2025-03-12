package net.p1nero.ss.animation;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class LinkArtifactSpiritAnimation extends ActionAnimation implements ILinkArtifactSpiritAnimation{
    private AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation;

    public LinkArtifactSpiritAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature, AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation) {
        super(transitionTime, accessor, armature);
        this.artifactSpiritAnimation = artifactSpiritAnimation;
    }

    public LinkArtifactSpiritAnimation(float transitionTime, float postDelay, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature, AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation) {
        super(transitionTime, postDelay, accessor, armature);
        this.artifactSpiritAnimation = artifactSpiritAnimation;
    }

    public LinkArtifactSpiritAnimation(float transitionTime, float postDelay, String path, AssetAccessor<? extends Armature> armature, AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation) {
        super(transitionTime, postDelay, path, armature);
        this.artifactSpiritAnimation = artifactSpiritAnimation;
    }


    @Override
    public void begin(LivingEntityPatch<?> entityPatch) {
        super.begin(entityPatch);
        this.callArtifactSpiritAnimation(entityPatch);
    }

    @Override
    public LinkArtifactSpiritAnimation setArtifactSpiritAnimation(AnimationManager.AnimationAccessor<? extends StaticAnimation> artifactSpiritAnimation) {
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
