package net.p1nero.ss.mixin;

import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.OpenMatrix4f;

@Mixin(value = Collider.class, remap = false)
public interface ColliderInvoker {
    @Invoker("transform")
    void sword_soaring$transform(OpenMatrix4f mat);


    @Invoker("getHitboxAABB")
    AABB sword_soaring$getHitboxAABB();
}
