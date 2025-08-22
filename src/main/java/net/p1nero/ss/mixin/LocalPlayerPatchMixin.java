package net.p1nero.ss.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.item.VatanseverItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

@Mixin(value = LocalPlayerPatch.class, remap = false)
public abstract class LocalPlayerPatchMixin extends AbstractClientPlayerPatch<LocalPlayer> {

    @Shadow
    private LivingEntity rayTarget;

    public LocalPlayerPatchMixin(LocalPlayer entity) {
        super(entity);
    }

    @Inject(method = "preTickClient",at = @At(value = "INVOKE", target = "Lyesman/epicfight/network/EpicFightNetworkManager;sendToServer(Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;[Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"), cancellable = true)
    public void tick(EntityTickEvent.Pre event, CallbackInfo ci) {
        if(this.getOriginal().getMainHandItem().getItem() instanceof VatanseverItem){
            if(rayTarget instanceof AbstractArtifactSpiritEntity){
                rayTarget = null;
                ci.cancel();
            }
        }
    }

}
