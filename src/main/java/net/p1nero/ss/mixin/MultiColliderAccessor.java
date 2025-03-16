package net.p1nero.ss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiCollider;

import java.util.List;

@Mixin(value = MultiCollider.class, remap = false)
public interface MultiColliderAccessor<T extends Collider> {
    @Accessor("numberOfColliders")
    int getNumberOfColliders();
    @Accessor("colliders")
    List<T> getColliders();
}
