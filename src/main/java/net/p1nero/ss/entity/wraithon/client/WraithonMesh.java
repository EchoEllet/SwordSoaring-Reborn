package net.p1nero.ss.entity.wraithon.client;

import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.client.model.Mesh;
import yesman.epicfight.api.client.model.MeshPartDefinition;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.client.model.VertexBuilder;

import java.util.List;
import java.util.Map;

public class WraithonMesh extends SkinnedMesh {
    public WraithonMesh(@Nullable Map<String, Number[]> arrayMap, @Nullable Map<MeshPartDefinition, List<VertexBuilder>> partBuilders, @Nullable SkinnedMesh parent, Mesh.RenderProperties properties) {
        super(arrayMap, partBuilders, parent, properties);
    }
}