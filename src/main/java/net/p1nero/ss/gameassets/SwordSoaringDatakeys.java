package net.p1nero.ss.gameassets;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.skill.sword_controller.*;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkill;
import net.p1nero.ss.skill.sword_soaring.SwordSoaringSkillElytra;
import net.p1nero.ss.skill.weapon_passive.ArtifactSpiritPassiveSkill;
import net.p1nero.ss.skill.weapon_passive.VatanseverPassive;
import yesman.epicfight.api.utils.PacketBufferCodec;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.SkillDataKey;

public class SwordSoaringDatakeys {
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(EpicFightMod.MODID, "skill_data_keys"), SwordSoaringMod.MOD_ID);

    //Artifact
    //器灵id
    public static final RegistryObject<SkillDataKey<Integer>> ARTIFACT_SPIRIT_ENTITY_ID = DATA_KEYS.register("artifact_spirit_entity_id", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, false, ArtifactSpiritPassiveSkill.class));
    //剩余卫国者剑数
    public static final RegistryObject<SkillDataKey<Integer>> SWORD_COUNT = DATA_KEYS.register("sword_count", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, true, ArtifactSpiritPassiveSkill.class));

    //Fly Skill
    //是否处于飞行状态
    public static final RegistryObject<SkillDataKey<Boolean>> FLYING = DATA_KEYS.register("flying", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, false, SwordSoaringSkill.class));
    //是否加速中
    public static final RegistryObject<SkillDataKey<Boolean>> ACCELERATING = DATA_KEYS.register("accelerating", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, false, SwordSoaringSkill.class));
    //冷却计时器
    public static final RegistryObject<SkillDataKey<Integer>> COOLDOWN_TIMER = DATA_KEYS.register("cooldown_timer", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, true, SwordSoaringSkill.class, SwordSoaringSkillElytra.class, WanJianGuiZongSkill.class, KillAuraSkill.class, ScreenSwordSkill.class, RainSwordSkill.class, GateOfBabylonSkill.class));
    //万剑归宗
    public static final RegistryObject<SkillDataKey<Boolean>> IS_PRESSING = DATA_KEYS.register("is_pressing", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, false, WanJianGuiZongSkill.class));
    public static final RegistryObject<SkillDataKey<Boolean>> IS_CHARGING = DATA_KEYS.register("is_charging", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, true, WanJianGuiZongSkill.class));
    //帘剑
    public static final RegistryObject<SkillDataKey<Integer>> PROTECT_COUNT = DATA_KEYS.register("protect_count", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, true, ScreenSwordSkill.class));
    //七星剑
    public static final RegistryObject<SkillDataKey<Integer>> DELAY_TIMER = DATA_KEYS.register("delay_timer", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, false, RainSwordSkill.class));

    public static final RegistryObject<SkillDataKey<Boolean>> PLAY_BIG_DIPPER = DATA_KEYS.register("play_big_dipper", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, false, RainSwordSkill.class));
    //剑阵
    public static final RegistryObject<SkillDataKey<Integer>> SWORD_ENTITY_ID = DATA_KEYS.register("sword_entity_id", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, false, KillAuraSkill.class));
    //王财
    public static final RegistryObject<SkillDataKey<Integer>> CAMERA_TIMER = DATA_KEYS.register("camera_timer", () ->
            SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, false, GateOfBabylonSkill.class));



}
