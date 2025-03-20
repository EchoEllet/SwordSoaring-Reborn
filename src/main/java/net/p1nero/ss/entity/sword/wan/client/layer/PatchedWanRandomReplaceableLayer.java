package net.p1nero.ss.entity.sword.wan.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.entity.AbstractArtifactSpiritPatch;
import net.p1nero.ss.entity.ReplaceableArmature;
import net.p1nero.ss.entity.sword.wan.WanEntity;
import net.p1nero.ss.gameassets.SwordSoaringDatakeys;
import net.p1nero.ss.gameassets.SwordSoaringSkillSlots;
import net.p1nero.ss.skill.sword_controller.WanJianGuiZongSkill;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class PatchedWanRandomReplaceableLayer<E extends WanEntity, T extends AbstractArtifactSpiritPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, RenderLayer<E, M>> {

    @Override
    protected void renderLayer(T entityPatch, E entity, RenderLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
        if (entityPatch.getArmature() instanceof ReplaceableArmature armature) {
            renderItemInJoint(entity, entityPatch, armature, poses, buffer, postStack, LightTexture.FULL_BRIGHT);
        }
    }

    /**
     * 根据玩家物品栏物品随机替换Joint的位置渲染
     */
    public static void renderItemInJoint(WanEntity entity, AbstractArtifactSpiritPatch<?> artifactSpiritPatch, ReplaceableArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        if (entity.getOwner() != null) {
            List<ItemStack> babylons = entity.getValidBabylonItems();
            if (babylons.isEmpty()) {
                return;
            }

            int lifeTime = 0;
            if(entity.getOwnerPatch() instanceof PlayerPatch<?> playerPatch){
                SkillDataManager manager = playerPatch.getSkill(SwordSoaringSkillSlots.SWORD_CONTROLLER).getDataManager();
                if(manager.hasData(SwordSoaringDatakeys.COOLDOWN_TIMER.get())){
                    int cooldown = manager.getDataValue(SwordSoaringDatakeys.COOLDOWN_TIMER.get());
                    lifeTime = WanJianGuiZongSkill.getMaxCooldown() - cooldown;
                }
            }
            List<Joint> jointList = armature.getJoints(artifactSpiritPatch);
            Random random = new Random(entity.getSeed());
            //慢慢出现，1tick解放2根
            for (int i = 0; i < jointList.size() && i < lifeTime / 2; i++) {
                Joint joint = jointList.get(i);
                ItemStack itemStack;
                if (i < babylons.size()) {
                    itemStack = babylons.get(i);//尽可能都用上
                } else {
                    itemStack = babylons.get(random.nextInt(babylons.size()));
                }
                if (itemStack != null) {
                    OpenMatrix4f jointTransform = poses[joint.getId()];
                    if (entity.getStartTransform(joint.getId()) == null && jointTransform.toScaleVector().length() > 0) {
                        entity.bindStartTransform(joint.getId(), jointTransform.removeScale());
                    }

                    //画在Joint上
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, jointTransform);
                    ItemDisplayContext transformType = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                    Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(artifactSpiritPatch.getOriginal(), itemStack, transformType, false, poseStack, buffer, packedLight);
                    poseStack.popPose();
                }
            }
        }
    }

}