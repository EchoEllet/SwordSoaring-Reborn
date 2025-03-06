package net.p1nero.ss.entity.sword.gate_of_babylon;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.Config;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.gameassets.animations.BabylonAnimations;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.network.PacketRelay;
import net.p1nero.ss.network.packet.server.RequestBabylonSyncPacket;
import net.p1nero.ss.util.AnimationUtils;
import net.p1nero.ss.util.ItemUtils;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.main.EpicFightMod;

import java.util.*;

public class BabylonEntity extends AbstractSwordEntity {
    private ArrayList<ItemStack> validBabylonItems = new ArrayList<>();
    private float startYRot;
    private final Map<Integer, OpenMatrix4f> startJointTransformMap = new HashMap<>();
    private final Map<Integer, Double> jointDamageMap = new HashMap<>();
    private final Map<Integer, Boolean> jointsHittenGroundMap = new HashMap<>();
    private static final EntityDataAccessor<String> ANIMATION_TO_PLAY = SynchedEntityData.defineId(BabylonEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> CLIENT_INIT = SynchedEntityData.defineId(BabylonEntity.class, EntityDataSerializers.BOOLEAN);

    public BabylonEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BabylonEntity(Player owner, Vec3 startPos, float yRot) {
        super(SwordSoaringEntities.BABYLON.get(), owner.getMainHandItem().copy(), owner);
        setPos(startPos);
        setYBodyRot(yRot);
        setYRot(yRot);
        setYHeadRot(yRot);
        if (!level.isClientSide) {
            startYRot = yRot;
        }
        setNoGravity(true);
        noPhysics = true;

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(ANIMATION_TO_PLAY, "sword_soaring:babylon/babylon_shoot");
        getEntityData().define(CLIENT_INIT, false);
    }

    public StaticAnimation getAnimationToPlay() {
        return EpicFightMod.getInstance().animationManager.findAnimationByPath(this.getEntityData().get(ANIMATION_TO_PLAY));
    }

    public void setAnimationToPlay(StaticAnimation staticAnimation) {
        getEntityData().set(ANIMATION_TO_PLAY, staticAnimation.getRegistryName().toString());
    }

    public void bindStartTransform(int jointId, OpenMatrix4f startTransform) {
        this.startJointTransformMap.put(jointId, startTransform);
    }

    public OpenMatrix4f getStartTransform(int jointId) {
        return startJointTransformMap.get(jointId);
    }

    /**
     * 金闪闪！
     */
    @Override
    public int getTeamColor() {
        return 0xf9fb74;
    }

    @Override
    public boolean isCurrentlyGlowing() {
        return Config.ITEMS_BLOOM.get();
    }

    /**
     * 应在服务端调用
     */
    public void initBabylonItems(ArrayList<ItemStack> babylonItems){
        validBabylonItems = babylonItems;
        Collections.shuffle(validBabylonItems);
        //记录Joint和伤害的关系
        if (!level.isClientSide) {
            List<Joint> joints = SwordSoaringArmatures.babylonArmature.getJoints(getPatch());
            for (int i = 0; i < joints.size() && i < validBabylonItems.size(); i++) {
                Joint joint = joints.get(i);
                ItemStack itemStack = validBabylonItems.get(i);
                jointDamageMap.put(joint.getId(), ItemUtils.getItemAttackDamage(this.getOwner(), itemStack));
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(getOwner() != null && level.isClientSide && !getEntityData().get(CLIENT_INIT)){
            getEntityData().set(CLIENT_INIT, true);
            PacketRelay.sendToServer(PacketHandler.INSTANCE, new RequestBabylonSyncPacket(getId()));
        }
        if(!level.isClientSide && !jointDamageMap.isEmpty() && getPatch() != null){
            if(getPatch().getAnimator().getPlayerFor(null).getElapsedTime() > 1.33F){
                for(int id : jointDamageMap.keySet()){
                    if(jointsHittenGroundMap.getOrDefault(id, false)) {
                        continue;
                    }
                    Joint joint = SwordSoaringArmatures.babylonArmature.searchJointById(id);
                    Vec3 jointPos = AnimationUtils.getJointWorldPos(getPatch(), joint);
                    if(jointPos.y() <= getY() + 0.5F){
                        LevelUtil.circleSlamFracture(getOwner(), level, jointPos.add(0, -1, 0), 2.5, false);
                        jointsHittenGroundMap.put(id, true);
                        if(Config.REMOVE_ITEM.get()){
                            ItemEntity itemEntity = new ItemEntity(level, jointPos.x, jointPos.y, jointPos.z, validBabylonItems.get(SwordSoaringArmatures.babylonArmature.joints.indexOf(joint)));
                            level.addFreshEntity(itemEntity);
                        }
                    }
                }
            }
        }
    }

    /**
     * 接收来自服务端的
     */
    public void updateBabylonItems(ArrayList<ItemStack> items) {
        validBabylonItems = items;
    }

    public ArrayList<ItemStack> getValidBabylonItems() {
        return validBabylonItems;
    }

    public Map<Integer, Double> getJointDamageMap() {
        return jointDamageMap;
    }

    public double getJointDamage(Joint joint) {
        return Objects.requireNonNullElse(jointDamageMap.get(joint.getId()), 0.0);
    }

    @Override
    protected void moveToOwner(LivingEntity owner) {
        if (!level.isClientSide) {
            setYRot(startYRot);
            setYBodyRot(startYRot);
            setYHeadRot(startYRot);
        }
    }

    @Override
    protected Item getOriginalItem() {
        return null;
    }


}
