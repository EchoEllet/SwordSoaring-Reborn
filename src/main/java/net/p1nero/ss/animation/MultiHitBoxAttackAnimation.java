package net.p1nero.ss.animation;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordPatch;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiHitBoxAttackAnimation extends AttackAnimation {
    public MultiHitBoxAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
    }

    public MultiHitBoxAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
    }

    public MultiHitBoxAttackAnimation(float convertTime, String path, Armature armature, Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    /**
     * 全部进行判断
     */
    @Override
    protected void attackTick(LivingEntityPatch<?> entityPatch) {
        AnimationPlayer player = entityPatch.getAnimator().getPlayerFor(this);
        float elapsedTime = player.getElapsedTime();
        float prevElapsedTime = player.getPrevElapsedTime();
        EntityState state = this.getState(entityPatch, elapsedTime);
        EntityState prevState = this.getState(entityPatch, prevElapsedTime);
        for(Phase phase : phases){
            if (state.getLevel() == 1 && !state.turningLocked() && entityPatch instanceof MobPatch<?> mobpatch) {
                mobpatch.getOriginal().getNavigation().stop();
                entityPatch.getOriginal().attackAnim = 2.0F;
                LivingEntity target = entityPatch.getTarget();
                if (target != null) {
                    entityPatch.rotateTo(target, entityPatch.getYRotLimit(), false);
                }
            }

            if (prevState.attacking() || state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2) {
                if (!prevState.attacking() || phase != this.getPhaseByTime(prevElapsedTime) && (state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2)) {
                    entityPatch.playSound(this.getSwingSound(entityPatch, phase), 0.0F, 0.0F);
                    entityPatch.removeHurtEntities();
                }

                this.hurtCollidingEntities(entityPatch, prevElapsedTime, elapsedTime, prevState, state, phase);
            }
        }
    }

    /**
     * 全部渲染
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderDebugging(PoseStack poseStack, MultiBufferSource buffer, LivingEntityPatch<?> entityPatch, float playbackTime, float partialTicks) {
        AnimationPlayer animPlayer = entityPatch.getAnimator().getPlayerFor(this);
        float prevElapsedTime = animPlayer.getPrevElapsedTime();
        float elapsedTime = animPlayer.getElapsedTime();
        for(Phase phase : phases){
            Pair<Joint, Collider> colliderInfo;
            Collider collider;
            for(Iterator<Pair<Joint, Collider>> iterator = phase.colliders.iterator(); iterator.hasNext(); collider.draw(poseStack, buffer, entityPatch, this, colliderInfo.getFirst(), prevElapsedTime, elapsedTime, partialTicks, this.getPlaySpeed(entityPatch))) {
                colliderInfo = iterator.next();
                collider = colliderInfo.getSecond();
                if(colliderInfo.getSecond() == null && entityPatch instanceof ScreenSwordPatch screenSwordPatch){
                    ItemStack stack = screenSwordPatch.getOriginal().getItemStack(entityPatch);
                    collider = EpicFightCapabilities.getItemStackCapability(stack).getWeaponCollider();
                }
            }
        }
    }

    /**
     * 获取武器对应碰撞箱
     */
    public static class KillAuraAttackPhase extends AttackAnimation.Phase{

        public KillAuraAttackPhase(float start, float antic, float contact, float recovery, float end, Joint joint, Collider collider) {
            super(start, antic, contact, recovery, end, joint, collider);
        }

        public KillAuraAttackPhase(float start, float antic, float contact, float recovery, float end, InteractionHand hand, Joint joint, Collider collider) {
            super(start, antic, contact, recovery, end, hand, joint, collider);
        }

        public KillAuraAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, Joint joint, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, joint, collider);
        }

        public KillAuraAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, InteractionHand hand, Joint joint, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, hand, joint, collider);
        }

        public KillAuraAttackPhase(InteractionHand hand, Joint joint, Collider collider) {
            super(hand, joint, collider);
        }

        public KillAuraAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, boolean noStateBind, InteractionHand hand, Joint joint, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, noStateBind, hand, joint, collider);
        }

        public KillAuraAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, boolean noStateBind, InteractionHand hand, List<Pair<Joint, Collider>> colliders) {
            super(start, antic, preDelay, contact, recovery, end, noStateBind, hand, colliders);
        }

        @Override
        public List<Entity> getCollidingEntities(LivingEntityPatch<?> entityPatch, AttackAnimation animation, float prevElapsedTime, float elapsedTime, float attackSpeed) {
            if(entityPatch instanceof ScreenSwordPatch screenSwordPatch){
                List<Entity> entities = Lists.newArrayList();
                Pair<Joint, Collider> colliderInfo;
                Collider collider;
                boolean flag = false;
                ArrayList<Pair<Joint, Collider>> newColliders = new ArrayList<>();
                for(Iterator<Pair<Joint, Collider>> iterator = this.colliders.iterator(); iterator.hasNext(); entities.addAll(collider.updateAndSelectCollideEntity(entityPatch, animation, prevElapsedTime, elapsedTime, colliderInfo.getFirst(), attackSpeed))) {
                    colliderInfo = iterator.next();
                    collider = colliderInfo.getSecond();
                    if(colliderInfo.getSecond() == null){
                        flag = true;
                        ItemStack stack = screenSwordPatch.getOriginal().getItemStack(entityPatch);
                        collider = EpicFightCapabilities.getItemStackCapability(stack).getWeaponCollider();
                        newColliders.add(new Pair<>(colliderInfo.getFirst(), collider));
                    }
                }
                if(flag){
                    colliders = newColliders;
                }
                return entities;
            }
            return super.getCollidingEntities(entityPatch, animation, prevElapsedTime, elapsedTime, attackSpeed);
        }

    }

}
