package net.p1nero.ss.entity.sword.gate_of_babylon;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.p1nero.ss.entity.AbstractArtifactSpiritEntity;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.sword.AbstractSwordEntity;

import java.util.ArrayList;
import java.util.Collections;

public class BabylonEntity extends AbstractSwordEntity {
    private final ArrayList<Item> validBabylonItems = new ArrayList<>();
    private long seed;
    private float startYRot;
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
        }
        setNoGravity(true);
        noPhysics = true;
    }

    /**
     * 金闪闪！
     */
    @Override
    public int getTeamColor() {
        return 0xFFD700;
    }

    @Override
    public boolean isCurrentlyGlowing() {
        return true;
    }

    /**
     * 仅客户端渲染需要计算，服务端无需存着
     */
    @OnlyIn(Dist.CLIENT)
    public void calculateValidBabylonItems(Player player){
        validBabylonItems.clear();
        for(ItemStack itemStack : player.getInventory().items){
            if(!validBabylonItems.contains(itemStack.getItem())){
                validBabylonItems.add(itemStack.getItem());
            }
            //包括背包
            itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                for(int i = 0; i < iItemHandler.getSlots(); i ++){
                    Item inSideItem = iItemHandler.getStackInSlot(i).getItem();
                    if(!validBabylonItems.contains(inSideItem)){
                        validBabylonItems.add(inSideItem);
                    }
                }
            });
        }
        Collections.shuffle(validBabylonItems);//打乱
        seed = random.nextLong();
    }

    public long getSeed() {
        return seed;
    }

    public ArrayList<Item> getValidBabylonItems() {
        return validBabylonItems;
    }

    @Override
    public void tick() {
        super.tick();
        if(validBabylonItems.isEmpty()){
            if(getOwner() instanceof Player player && level.isClientSide){
                calculateValidBabylonItems(player);
                setYRot(player.getYRot());
                setYBodyRot(player.getYRot());
                setYHeadRot(player.getYRot());
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
