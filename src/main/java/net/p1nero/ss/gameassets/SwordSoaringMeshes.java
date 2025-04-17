package net.p1nero.ss.gameassets;

import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.entity.sword.fly_sword.client.FlySwordMesh;
import net.p1nero.ss.entity.sword.gate_of_babylon.client.BabylonMesh;
import net.p1nero.ss.entity.sword.screen_sword.client.ScreenSwordMesh;
import net.p1nero.ss.entity.sword.wan.client.WanMesh;
import net.p1nero.ss.entity.vatansever.client.VatanseverMesh;
import net.p1nero.ss.entity.vatansever_storm.client.VatanseverStormMesh;
import net.p1nero.ss.entity.wraithon.client.WraithonMesh;
import yesman.epicfight.api.client.model.Meshes;

public class SwordSoaringMeshes {
    public static final Meshes.MeshAccessor<WraithonMesh> WRAITHON_MESH = Meshes.MeshAccessor.create(SwordSoaringMod.MOD_ID, "entity/wraithon", (jsonModelLoader) -> jsonModelLoader.loadSkinnedMesh(WraithonMesh::new));
    public static final Meshes.MeshAccessor<WanMesh> WAN_MESH = Meshes.MeshAccessor.create(SwordSoaringMod.MOD_ID, "entity/wan", (jsonModelLoader) -> jsonModelLoader.loadSkinnedMesh(WanMesh::new));
    public static final Meshes.MeshAccessor<BabylonMesh> BABYLON_MESH = Meshes.MeshAccessor.create(SwordSoaringMod.MOD_ID, "entity/babylon", (jsonModelLoader) -> jsonModelLoader.loadSkinnedMesh(BabylonMesh::new));
    public static final Meshes.MeshAccessor<FlySwordMesh> FLY_SWORD_MESH = Meshes.MeshAccessor.create(SwordSoaringMod.MOD_ID, "entity/fly_sword", (jsonModelLoader) -> jsonModelLoader.loadSkinnedMesh(FlySwordMesh::new));
    public static final Meshes.MeshAccessor<ScreenSwordMesh> screenSwordMesh = Meshes.MeshAccessor.create(SwordSoaringMod.MOD_ID, "entity/screen_sword", (jsonModelLoader) -> jsonModelLoader.loadSkinnedMesh(ScreenSwordMesh::new));

    public static final Meshes.MeshAccessor<VatanseverMesh> vatanseverMesh = Meshes.MeshAccessor.create(SwordSoaringMod.MOD_ID, "entity/vatansever", (jsonModelLoader) -> jsonModelLoader.loadSkinnedMesh(VatanseverMesh::new));
    public static final Meshes.MeshAccessor<VatanseverStormMesh> vatanseverStormMesh = Meshes.MeshAccessor.create(SwordSoaringMod.MOD_ID, "entity/vatansever_swordgroup", (jsonModelLoader) -> jsonModelLoader.loadSkinnedMesh(VatanseverStormMesh::new));

}