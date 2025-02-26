package net.p1nero.ss.entity.sword.screen_sword;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.network.PacketRelay;
import net.p1nero.ss.network.packet.server.RequestEntityPlayAnimationPacket;
import net.p1nero.ss.skill.sword_controller.ScreenSwordSkill;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;

public class ScreenSwordPatch extends AbstractArtifactSpiritPatch<ScreenSword> {
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
                if(this.getOwnerPatch().getSkill(SwordSoaringSkillSlots.SWORD_CONTROLLER).getSkill() instanceof ScreenSwordSkill skill){
                    StaticAnimation toPlay = skill.getAnim().get();
                    this.animator.playAnimation(toPlay, 0.0F);
                    PacketRelay.sendToServer(PacketHandler.INSTANCE, new RequestEntityPlayAnimationPacket(this.getOriginal().getId(), toPlay.getNamespaceId(), toPlay.getId(), 0.0F));
                    played = true;
                }
            }
        }
    }

    @Override
    public void initAnimator(ClientAnimator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, ScreenSwordAnimations.SCREEN_SWORD_IDLE);
        animator.setCurrentMotionsAsDefault();
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        this.currentLivingMotion = LivingMotions.IDLE;
        this.currentCompositeMotion = LivingMotions.IDLE;
    }
}
