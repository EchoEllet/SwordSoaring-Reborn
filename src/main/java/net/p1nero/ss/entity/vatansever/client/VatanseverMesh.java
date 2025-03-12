package net.p1nero.ss.entity.vatansever.client;

import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.client.model.MeshPartDefinition;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.client.model.SkinnedMeshVertexBuilder;

import java.util.List;
import java.util.Map;

public class VatanseverMesh extends SkinnedMesh {

    public final SkinnedMesh.SkinnedMeshPart L1;
    public final SkinnedMesh.SkinnedMeshPart L2;
    public final SkinnedMesh.SkinnedMeshPart L3;
    public final SkinnedMesh.SkinnedMeshPart R1;
    public final SkinnedMesh.SkinnedMeshPart R2;
    public final SkinnedMesh.SkinnedMeshPart R3;
    public final List<SkinnedMesh.SkinnedMeshPart> swordLists;

    public VatanseverMesh(@Nullable Map<String, Number[]> arrayMap, @Nullable Map<MeshPartDefinition, List<SkinnedMeshVertexBuilder>> partBuilders, @Nullable SkinnedMesh parent, RenderProperties properties) {
        super(arrayMap, partBuilders, parent, properties);
        this.L1 = this.getOrLogException(parts, "sss_1_l");
        this.L2 = this.getOrLogException(parts, "sss_2_l");
        this.L3 = this.getOrLogException(parts, "sss_3_l");
        this.R1 = this.getOrLogException(parts, "sss_1_r");
        this.R2 = this.getOrLogException(parts, "sss_2_r");
        this.R3 = this.getOrLogException(parts, "sss_3_r");
        swordLists = List.of(R3, L3, R2, L2, R1, L1);
    }
}