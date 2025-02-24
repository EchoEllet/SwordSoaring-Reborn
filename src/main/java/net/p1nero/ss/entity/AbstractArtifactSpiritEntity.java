package net.p1nero.ss.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public abstract class AbstractArtifactSpiritEntity extends TamableAnimal {

    public AbstractArtifactSpiritEntity(EntityType<? extends AbstractArtifactSpiritEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        fallDistance = 0;
        LivingEntity owner = getOwner();
        if (owner != null) {
            moveToOwner(owner);
            if (!owner.getMainHandItem().is(getOriginalItem()) && shouldRemoveWhenOwnerLost()) {
                discard();
            }
        } else if(shouldRemoveWhenOwnerLost()){
            discard();
        }
    }

    protected void moveToOwner(LivingEntity owner){
        setYRot(owner.yBodyRot);
        setYBodyRot(owner.yBodyRot);
        setYHeadRot(owner.yBodyRot);
        setPos(owner.position());
    }

    protected boolean shouldRemoveWhenOwnerLost(){
        return true;
    }

    protected abstract Item getOriginalItem();

    public static AttributeSupplier getDefaultAttribute() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 19.9F)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 10.0F)
                .build();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_21017_) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pPos, @NotNull BlockState pState) {
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {
        return false;
    }

    @Override
    public boolean canSpawnSprintParticle() {
        return false;
    }

}