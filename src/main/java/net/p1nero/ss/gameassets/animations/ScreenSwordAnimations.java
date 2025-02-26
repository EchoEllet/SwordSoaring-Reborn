package net.p1nero.ss.gameassets.animations;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordArmature;
import net.p1nero.ss.entity.sword.screen_sword.ScreenSwordPatch;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ScreenSwordAnimations {
    public static StaticAnimation SCREEN_SWORD_IDLE;
    public static StaticAnimation SCREEN_SWORD_1;
    public static StaticAnimation SCREEN_SWORD_2;
    public static StaticAnimation SCREEN_SWORD_3;
    public static StaticAnimation SCREEN_SWORD_PLAYER_SUMMON;

    public static AnimationEvent.TimeStampedEvent RESET_ANIM = AnimationEvent.TimeStampedEvent.create(0.49F, ((livingEntityPatch, staticAnimation, objects) -> {
        livingEntityPatch.reserveAnimation(staticAnimation);
    }), AnimationEvent.Side.SERVER);

    public static void buildScreenSwordAnim() {
        ScreenSwordArmature screenSwordArmature = SwordSoaringArmatures.screenSwordArmature;
        ArrayList<Pair<Joint, Collider>> list = new ArrayList<>();
        for (Joint joint : screenSwordArmature.joints) {
            list.add(new Pair<>(joint, null));
        }
        AttackAnimation.Phase[] phases = new AttackAnimation.Phase[]{
                new ScreenSwordAttackPhase(0.01F, 0.01F, 0.01F, 0.1F, 0.1F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list),
                new ScreenSwordAttackPhase(0.1F, 0.1F, 0.1F, 0.2F, 0.2F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list),
                new ScreenSwordAttackPhase(0.2F, 0.2F, 0.2F, 0.3F, 0.3F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list),
                new ScreenSwordAttackPhase(0.3F, 0.3F, 0.3F, 0.4F, 0.4F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list),
                new ScreenSwordAttackPhase(0.4F, 0.4F, 0.4F, 0.5F, 0.5F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, list)};

        SCREEN_SWORD_IDLE = new StaticAnimation(true, "screen_sword/screen_sword_idle", screenSwordArmature)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 2.0F));
        SCREEN_SWORD_1 = new AttackAnimation(0.001F, "screen_sword/screen_sword_1", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        SCREEN_SWORD_2 = new AttackAnimation(0.001F, "screen_sword/screen_sword_2", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);
        SCREEN_SWORD_3 = new AttackAnimation(0.001F, "screen_sword/screen_sword_3", screenSwordArmature, phases)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 0.2F))
                .addEvents(RESET_ANIM);

        HumanoidArmature biped = Armatures.BIPED;
        SCREEN_SWORD_PLAYER_SUMMON = new ActionAnimation(0.15F, "screen_sword/screen_sword_start_player", biped);
    }

    /**
     * 获取武器对应碰撞箱
     */
    public static class ScreenSwordAttackPhase extends AttackAnimation.Phase{

        public ScreenSwordAttackPhase(float start, float antic, float contact, float recovery, float end, Joint joint, Collider collider) {
            super(start, antic, contact, recovery, end, joint, collider);
        }

        public ScreenSwordAttackPhase(float start, float antic, float contact, float recovery, float end, InteractionHand hand, Joint joint, Collider collider) {
            super(start, antic, contact, recovery, end, hand, joint, collider);
        }

        public ScreenSwordAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, Joint joint, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, joint, collider);
        }

        public ScreenSwordAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, InteractionHand hand, Joint joint, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, hand, joint, collider);
        }

        public ScreenSwordAttackPhase(InteractionHand hand, Joint joint, Collider collider) {
            super(hand, joint, collider);
        }

        public ScreenSwordAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, boolean noStateBind, InteractionHand hand, Joint joint, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, noStateBind, hand, joint, collider);
        }

        public ScreenSwordAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, boolean noStateBind, InteractionHand hand, List<Pair<Joint, Collider>> colliders) {
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
