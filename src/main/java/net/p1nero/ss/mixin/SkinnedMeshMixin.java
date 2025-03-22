package net.p1nero.ss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import yesman.epicfight.api.client.model.SkinnedMesh;

@Mixin(SkinnedMesh.class)
public class SkinnedMeshMixin {

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lyesman/epicfight/api/utils/math/OpenMatrix4f;allocateMatrixArray(I)[Lyesman/epicfight/api/utils/math/OpenMatrix4f;"), index = 0, remap = false)
    private static int sword_soaring$modifyMatrixArraySize(int size){
        return 60;
    }

}
