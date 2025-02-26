package net.p1nero.ss.entity.screen_sword.client;

import net.minecraft.client.model.EntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.layer.PatchedReplaceableLayer;
import net.p1nero.ss.entity.client.layer.ReplaceableRenderLayer;
import net.p1nero.ss.entity.screen_sword.ScreenSword;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class PatchedScreenSwordRenderer<E extends ScreenSword, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLivingEntityRenderer<E, T, M, ScreenSwordMesh> {

    public PatchedScreenSwordRenderer(){
        this.addPatchedLayer(ReplaceableRenderLayer.class, new PatchedReplaceableLayer<>());
    }

    @Override
    public ScreenSwordMesh getMesh(T t) {
        return SwordSoaringMeshes.screenSwordMesh;
    }

}