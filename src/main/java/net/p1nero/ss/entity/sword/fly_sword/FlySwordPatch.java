package net.p1nero.ss.entity.sword.fly_sword;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.animations.FlySwordAnimations;
import net.p1nero.ss.item.VatanseverItem;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.network.PacketRelay;
import net.p1nero.ss.network.packet.server.RequestEntityPlayAnimationPacket;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;

public class FlySwordPatch extends AbstractArtifactSpiritPatch<FlySwordEntity> {
    private boolean played;

    /**
     * 播放初始动画
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientTick(LivingEvent.LivingUpdateEvent event) {
        super.clientTick(event);
        if (!played) {
            if (this.isLogicalClient() && this.getOwnerPatch() != null) {
                if (this.getOriginal().getItemStack(this).getItem() instanceof VatanseverItem) {
                    StaticAnimation toPlay;
                    if (getOriginal().getRandom().nextBoolean()) {
                        toPlay = FlySwordAnimations.FLY_SWORD_ATK_1;
                    } else {
                        toPlay = FlySwordAnimations.FLY_SWORD_ATK_2;
                    }
                    this.animator.playAnimation(toPlay, 0.0001F);
                    PacketRelay.sendToServer(PacketHandler.INSTANCE, new RequestEntityPlayAnimationPacket(this.getOriginal().getId(), toPlay.getNamespaceId(), toPlay.getId(), 0.0001F));
                }
                played = true;
            }
        }
    }

    @Override
    public void initAnimator(ClientAnimator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, FlySwordAnimations.FLY_SWORD_ATK_IDLE);
        animator.setCurrentMotionsAsDefault();
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        keepIdleMotion(considerInaction);
    }
}
