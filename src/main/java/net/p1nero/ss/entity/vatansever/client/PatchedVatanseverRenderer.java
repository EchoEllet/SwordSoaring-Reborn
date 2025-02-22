package net.p1nero.ss.entity.vatansever.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.client.EmptyEntityModel;
import net.p1nero.ss.entity.vatansever.VatanseverEntity;
import net.p1nero.ss.entity.vatansever.VatanseverEntityPatch;
import net.p1nero.ss.gameassets.SwordSoaringMeshes;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;

@OnlyIn(Dist.CLIENT)
public class PatchedVatanseverRenderer extends PatchedLivingEntityRenderer<VatanseverEntity, VatanseverEntityPatch, EmptyEntityModel<VatanseverEntity>, VatanseverMesh> {

    @Override
    public VatanseverMesh getMesh(VatanseverEntityPatch vatanseverEntityPatch) {
        return SwordSoaringMeshes.vatanseverMesh;
    }

    @Override
    protected void prepareModel(VatanseverMesh mesh, VatanseverEntity entity, VatanseverEntityPatch entityPatch) {
        super.prepareModel(mesh, entity, entityPatch);
        mesh.swordLists.forEach((part -> part.hidden = false));
        if (entityPatch.getOwnerPatch() != null) {
            SkillDataManager manager = entityPatch.getOwnerPatch().getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
            if (manager.hasData(VatanseverPassive.SWORD_COUNT)) {
                for (int i = manager.getDataValue(VatanseverPassive.SWORD_COUNT); i < 6; i++) {
//                    mesh.swordLists.get(i).hidden = true;
                }
            }
        }
    }
}