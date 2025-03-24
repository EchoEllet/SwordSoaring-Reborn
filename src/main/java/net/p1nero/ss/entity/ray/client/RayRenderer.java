package net.p1nero.ss.entity.ray.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.ray.RayEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class RayRenderer extends EntityRenderer<RayEntity> {

    public static final ResourceLocation RAY = new ResourceLocation(SwordSoaringMod.MOD_ID,"textures/entity/ray.png");

    public RayRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
    @Override
    public ResourceLocation getTextureLocation(RayEntity pEntity) {
        return RAY;
    }




    @Override
    public void render(RayEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Vec3 start = entity.getStartPos();
        Vec3 end = entity.getEndPos();
        renderRay(entity, start, end, poseStack, buffer, partialTicks);
    }

    public static void renderRay(Entity entity, Vec3 begin, Vec3 end, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
        poseStack.pushPose();
        var pose = poseStack.last();
        Vec3 start = Vec3.ZERO;

        Vec3 direction = end.subtract(begin);
        float distance = (float) direction.length();
        if (distance < 1e-6) { // 避免零向量浪费算力
            poseStack.popPose();
            return;
        }
        direction = direction.normalize();

        float yaw = (float) Math.toDegrees(Math.atan2(direction.z, direction.x)) - 90.0F;
        float pitch = (float) -Math.toDegrees(Math.asin(direction.y));

        Vec3 entityPos = entity.getPosition(partialTicks);
        Vec3 offset = begin.subtract(entityPos);
        poseStack.translate(offset.x, offset.y, offset.z);

        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        float radius = 0.5F;
        int r = 255, g = 255, b = 255, a = 255;
        float deltaTicks = entity.tickCount + partialTicks;
        float deltaUV = -deltaTicks % 10;
        float max = Mth.frac(deltaUV * 0.2F - Mth.floor(deltaUV * 0.1F));
        float min = -1.0F + max;

        for (float j = 0.5F; j <= distance; j += 0.5F) {
            Vec3 currentEnd = new Vec3(0, 0, Math.min(j, distance));
            VertexConsumer inner = bufferSource.getBuffer(RenderType.entityTranslucent(RAY, true));
            drawHull(start, currentEnd, radius, radius, pose, inner, r, g, b, a, min, max);
            start = currentEnd;
        }

        poseStack.popPose();
    }
    private static void drawHull(Vec3 from, Vec3 to, float width, float height, PoseStack.Pose pose, VertexConsumer consumer, int r,int g,int b,int a,float uvMin,float uvMax){
        drawQuad(from.subtract(0,height *0.5F,0), to.subtract(0,height*0.5F,0),width,0,pose,consumer,r,g,b,a,uvMin,uvMax);

        drawQuad(from.add(0,height *0.5F,0), to.add(0,height*0.5F,0),width,0,pose,consumer,r,g,b,a,uvMin,uvMax);

        drawQuad(from.subtract(width*0.5F,0,0), to.subtract(width*0.5F,0,0),0,height,pose,consumer,r,g,b,a,uvMin,uvMax);

        drawQuad(from.add(width*0.5F,0,0), to.add(width*0.5F,0,0),0,height,pose,consumer,r,g,b,a,uvMin,uvMax);
    }

    private static void drawQuad(Vec3 from, Vec3 to, float width, float height, PoseStack.Pose pose, VertexConsumer consumer, int r,int g,int b,int a,float uvMin,float uvMax){
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();
        float halfWidth = width *0.5F;
        float halfHeight = height *0.5F;
        consumer.vertex(poseMatrix,(float) from.x - halfWidth,(float)from.y -halfHeight,(float)from.z).color(r,g,b,a).uv(0F,uvMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(0f,1f,0f).endVertex();
        consumer.vertex(poseMatrix,(float) from.x + halfWidth,(float)from.y +halfHeight,(float)from.z).color(r,g,b,a).uv(1F,uvMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(0f,1f,0f).endVertex();
        consumer.vertex(poseMatrix,(float) to.x + halfWidth,(float)to.y +halfHeight,(float)to.z).color(r,g,b,a).uv(1F,uvMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(0f,1f,0f).endVertex();
        consumer.vertex(poseMatrix,(float) to.x - halfWidth,(float)to.y -halfHeight,(float)to.z).color(r,g,b,a).uv(0F,uvMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(0f,1f,0f).endVertex();
    }

}
