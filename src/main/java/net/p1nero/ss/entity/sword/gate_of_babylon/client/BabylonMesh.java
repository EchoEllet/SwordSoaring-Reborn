package net.p1nero.ss.entity.sword.gate_of_babylon.client;


import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.client.model.MeshPartDefinition;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.client.model.SkinnedMeshVertexBuilder;

import java.util.List;
import java.util.Map;

public class BabylonMesh extends SkinnedMesh {
    public BabylonMesh(@Nullable Map<String, Number[]> arrayMap, @Nullable Map<MeshPartDefinition, List<SkinnedMeshVertexBuilder>> partBuilders, @Nullable SkinnedMesh parent, RenderProperties properties) {
        super(arrayMap, partBuilders, parent, properties);
    }
}