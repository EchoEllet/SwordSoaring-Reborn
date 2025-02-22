package net.p1nero.ss.entity.vatansever_storm.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.EmptyEntityModel;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntity;
import net.p1nero.ss.entity.vatansever_storm.VatanseverStormEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;

@OnlyIn(Dist.CLIENT)
public class PatchedVatanseverStormRenderer extends PatchedLivingEntityRenderer<VatanseverStormEntity, VatanseverStormEntityPatch, EmptyEntityModel<VatanseverStormEntity>, VatanseverStormMesh> {

    @Override
    public VatanseverStormMesh getMesh(VatanseverStormEntityPatch vatanseverStormEntityPatch) {
        return SwordSoaringMeshes.vatanseverStormMesh;
    }

}