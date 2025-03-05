package net.p1nero.ss.entity.sword.gate_of_babylon;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.p1nero.ss.SwordSoaring;
import net.p1nero.ss.compat.ArmourersWorkshopCompat;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;
import net.p1nero.ss.gameassets.SwordSoaringArmatures;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.network.PacketRelay;
import net.p1nero.ss.network.packet.client.SyncEnderChestValidBabylonPacket;
import net.p1nero.ss.util.ItemUtils;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.main.EpicFightMod;

import java.util.*;
import java.util.function.Supplier;

public class BabylonEntity extends AbstractSwordEntity {
    private final ArrayList<ItemStack> validBabylonItems = new ArrayList<>();
    private float startYRot;
    private final Map<Integer, OpenMatrix4f> jointTransformMap = new HashMap<>();
    private final Map<Integer, Double> jointDamageMap = new HashMap<>();
    private static final EntityDataAccessor<String> ANIMATION_TO_PLAY = SynchedEntityData.defineId(BabylonEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> SEED = SynchedEntityData.defineId(BabylonEntity.class, EntityDataSerializers.INT);//双端打乱顺序需要同步

    public BabylonEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BabylonEntity(Player owner) {
        super(SwordSoaringEntities.BABYLON.get(), owner.getMainHandItem().copy(), owner);
        setPos(owner.position());
        setYBodyRot(owner.getYRot());
        setYRot(owner.getYRot());
        setYHeadRot(owner.getYRot());
        if (!level.isClientSide) {
            startYRot = owner.getYRot();
            getEntityData().set(SEED, random.nextInt());
        }
        setNoGravity(true);
        noPhysics = true;

        //时装工坊联动，拷贝时装栏
        SwordSoaring.runInArmourersWorkshopLoaded(() -> () -> ArmourersWorkshopCompat.copyArmourers(owner, this));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(ANIMATION_TO_PLAY, "sword_soaring:babylon/babylon_shoot");
        getEntityData().define(SEED, 0);
    }

    public StaticAnimation getAnimationToPlay() {
        return EpicFightMod.getInstance().animationManager.findAnimationByPath(this.getEntityData().get(ANIMATION_TO_PLAY));
    }

    public void setAnimationToPlay(StaticAnimation staticAnimation) {
        getEntityData().set(ANIMATION_TO_PLAY, staticAnimation.getRegistryName().toString());
    }

    public void bindStartTransform(int jointId, OpenMatrix4f startTransform) {
        this.jointTransformMap.put(jointId, startTransform);
    }

    public OpenMatrix4f getStartTransform(int jointId) {
        return jointTransformMap.get(jointId);
    }

    /**
     * 金闪闪！
     */
    @Override
    public int getTeamColor() {
        return 0xf9fb74;
    }

//    @Override
//    public boolean isCurrentlyGlowing() {
//        return true;
//    }

    /**
     * 双端分别计算，并共用seed
     */
    public void calculateValidBabylonItems(Player player) {
        validBabylonItems.clear();
        player.getInventory().items.forEach(itemStack -> {

            //包括背包，潜影贝等
            boolean isItemHandler = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent();
            if (isItemHandler) {
                itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                    for (int i = 0; i < iItemHandler.getSlots(); i++) {
                        ItemStack inSideItem = iItemHandler.getStackInSlot(i);
                        if (!inSideItem.isEmpty()) {
                            validBabylonItems.add(inSideItem.copy());
                        }
                    }
                });
            } else {
                if (!itemStack.isEmpty()) {
                    validBabylonItems.add(itemStack.copy());
                }
            }
        });
        if (!level.isClientSide) {
            List<ItemStack> enderChestStacks = new ArrayList<>();
            PlayerEnderChestContainer enderChestContainer = player.getEnderChestInventory();
            for (int i = 0; i < enderChestContainer.getContainerSize(); i++) {
                ItemStack itemStack = enderChestContainer.getItem(i);
                if (!itemStack.isEmpty()) {
                    enderChestStacks.add(itemStack.copy());
                }
            }
            PacketRelay.sendToAll(PacketHandler.INSTANCE, new SyncEnderChestValidBabylonPacket(getId(), enderChestStacks.size(), enderChestStacks));
            validBabylonItems.addAll(enderChestStacks);
        }
        Collections.shuffle(validBabylonItems, new Random(getSeed()));//打乱但客户端服务端打乱顺序要一致
        if (!level.isClientSide) {
            List<Joint> joints = SwordSoaringArmatures.babylonArmature.getJoints(getPatch());
            for (int i = 0; i < joints.size(); i++) {
                Joint joint = joints.get(i);
                ItemStack itemStack;
                if (i < validBabylonItems.size()) {
                    itemStack = validBabylonItems.get(i);//尽可能都用上
                } else {
                    itemStack = validBabylonItems.get(new Random(getSeed()).nextInt(validBabylonItems.size()));
                }
                jointDamageMap.put(joint.getId(), ItemUtils.getItemAttackDamage(this.getOwner(), itemStack));
            }
        }
    }

    /**
     * 接收来自服务端的
     */
    public void receiveServerEnderChestItem(List<ItemStack> items) {
        validBabylonItems.addAll(items);
    }

    public long getSeed() {
        return getEntityData().get(SEED);
    }

    public ArrayList<ItemStack> getValidBabylonItems() {
        return validBabylonItems;
    }

    public Map<Integer, Double> getJointDamageMap() {
        return jointDamageMap;
    }

    public double getJointDamage(Joint joint) {
        return jointDamageMap.get(joint.getId());
    }

    @Override
    public void tick() {
        super.tick();
        if (validBabylonItems.isEmpty() && getSeed() != 0) {
            if (getOwner() instanceof Player player) {
                calculateValidBabylonItems(player);
                if (!level.isClientSide) {
                    setYRot(player.getYRot());
                    setYBodyRot(player.getYRot());
                    setYHeadRot(player.getYRot());
                }
            }
        }
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
