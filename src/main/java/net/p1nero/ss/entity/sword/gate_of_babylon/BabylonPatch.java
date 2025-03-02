package net.p1nero.ss.entity.sword.gate_of_babylon;

import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.SwordSoaringColliders;
import net.p1nero.ss.gameassets.animations.BabylonAnimations;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.network.PacketRelay;
import net.p1nero.ss.network.packet.server.RequestEntityPlayAnimationPacket;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.collider.Collider;

public class BabylonPatch extends AbstractArtifactSpiritPatch<BabylonEntity> {
    private boolean played;

    /**
     * Join World的时候主人还没初始化，只能换这里操作
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientTick(LivingEvent.LivingUpdateEvent event) {
        super.clientTick(event);
        if(!played){
            if(this.isLogicalClient() && this.getOwnerPatch() != null){
                StaticAnimation toPlay = BabylonAnimations.BABYLON_SHOOT;
                this.animator.playAnimation(toPlay, 0.0001F);
                PacketRelay.sendToServer(PacketHandler.INSTANCE, new RequestEntityPlayAnimationPacket(this.getOriginal().getId(), toPlay.getNamespaceId(), toPlay.getId(), 0.0001F));
                played = true;
            }
        }
    }

    @Override
    public void initAnimator(ClientAnimator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, BabylonAnimations.BABYLON_IDLE);
        animator.setCurrentMotionsAsDefault();
    }

    /**
     * 懒得根据Joint去查碰撞箱了，太麻烦
     */
    @Override
    public Collider getColliderMatching(InteractionHand hand) {
        return SwordSoaringColliders.FLY_SWORD_COMMON;
    }


    @Override
    public void updateMotion(boolean considerInaction) {
        keepIdleMotion(considerInaction);
    }

}
