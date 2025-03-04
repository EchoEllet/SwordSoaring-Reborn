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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;
import net.p1nero.ss.network.PacketHandler;
import net.p1nero.ss.network.PacketRelay;
import net.p1nero.ss.network.packet.client.SyncEnderChestValidBabylonPacket;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.main.EpicFightMod;

import java.util.*;

public class BabylonEntity extends AbstractSwordEntity {
    private final ArrayList<Item> validBabylonItems = new ArrayList<>();
    private float startYRot;
    private final Map<Integer, OpenMatrix4f> jointTransformMap = new HashMap<>();
    private static final EntityDataAccessor<String> ANIMATION_TO_PLAY = SynchedEntityData.defineId(BabylonEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> SEED = SynchedEntityData.defineId(BabylonEntity.class, EntityDataSerializers.INT);//双端打乱顺序需要同步
    public BabylonEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BabylonEntity(Player owner){
        super(SwordSoaringEntities.BABYLON.get(), owner.getMainHandItem().copy(), owner);
        setPos(owner.position());
        setYBodyRot(owner.getYRot());
        setYRot(owner.getYRot());
        setYHeadRot(owner.getYRot());
        if(!level.isClientSide){
            startYRot = owner.getYRot();
            getEntityData().set(SEED, random.nextInt());
        }
        setNoGravity(true);
        noPhysics = true;
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

    public void setAnimationToPlay(StaticAnimation staticAnimation){
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
    public void calculateValidBabylonItems(Player player){
        validBabylonItems.clear();
        player.getInventory().items.forEach(itemStack -> {
            if(itemStack.isEmpty()){
                return;
            }
            if(!validBabylonItems.contains(itemStack.getItem())){
                validBabylonItems.add(itemStack.getItem());
            }
            //包括背包，潜影贝等
            itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                for(int i = 0; i < iItemHandler.getSlots(); i ++){
                    Item inSideItem = iItemHandler.getStackInSlot(i).getItem();
                    if(!validBabylonItems.contains(inSideItem)){
                        validBabylonItems.add(inSideItem);
                    }
                }
            });
        });
        if(!level.isClientSide){
            List<ItemStack> enderChestStacks = new ArrayList<>();
            PlayerEnderChestContainer enderChestContainer = player.getEnderChestInventory();
            for(int i = 0; i < enderChestContainer.getContainerSize(); i++){
                ItemStack itemStack = enderChestContainer.getItem(i);
                if(itemStack.isEmpty()){
                    continue;
                }
                Item inSideItem = itemStack.getItem();
                if(!validBabylonItems.contains(inSideItem)){
                    validBabylonItems.add(inSideItem);
                    enderChestStacks.add(itemStack);
                }
            }
            PacketRelay.sendToAll(PacketHandler.INSTANCE, new SyncEnderChestValidBabylonPacket(getId(), enderChestStacks.size(), enderChestStacks));
        }
        Collections.shuffle(validBabylonItems, new Random(getSeed()));//打乱但客户端服务端打乱顺序要一致
    }

    /**
     * 接收来自服务端的
     */
    public void receiveServerEnderChestItem(List<ItemStack> items){
        items.forEach(itemStack -> {
            Item item = itemStack.getItem();
            if(!validBabylonItems.contains(item)){
                validBabylonItems.add(item);
            }
        });
    }

    public long getSeed() {
        return getEntityData().get(SEED);
    }

    public ArrayList<Item> getValidBabylonItems() {
        return validBabylonItems;
    }

    @Override
    public void tick() {
        super.tick();
        if(validBabylonItems.isEmpty() && getSeed() != 0){
            if(getOwner() instanceof Player player){
                calculateValidBabylonItems(player);
                if(!level.isClientSide){
                    setYRot(player.getYRot());
                    setYBodyRot(player.getYRot());
                    setYHeadRot(player.getYRot());
                }
            }
        }
    }

    @Override
    protected void moveToOwner(LivingEntity owner) {
        if(!level.isClientSide){
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
