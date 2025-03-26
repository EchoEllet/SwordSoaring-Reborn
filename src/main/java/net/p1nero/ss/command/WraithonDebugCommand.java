package net.p1nero.ss.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.phys.Vec3;
import net.p1nero.ss.entity.SwordSoaringEntities;
import net.p1nero.ss.entity.wraithon.WraithonEntity;
import net.p1nero.ss.entity.wraithon.WraithonEntityPatch;
import net.p1nero.ss.gameassets.animations.WraithonAnimations;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Objects;

public class WraithonDebugCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sword_soaring")
                .then(Commands.literal("playWraithonAnimation").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .then(Commands.argument("anim_id", StringArgumentType.string())
                                .suggests(((commandContext, suggestionsBuilder) -> {
                                    WraithonAnimations.DEBUG_ANIM_LIST.forEach(
                                            animationAccessor -> suggestionsBuilder.suggest("\"" + animationAccessor.get().getRegistryName().toString() + "\"")
                                    );
                                    return suggestionsBuilder.buildFuture();
                                }))
                                .executes(commandContext -> {
                                    List<WraithonEntity> wraithonList = commandContext.getSource().getLevel().getEntitiesOfClass(WraithonEntity.class, Objects.requireNonNull(commandContext.getSource().getPlayer()).getBoundingBox().inflate(50.0D));
                                    for (WraithonEntity wraithon : wraithonList) {
                                        LivingEntityPatch<WraithonEntity> entityPatch = EpicFightCapabilities.getEntityPatch(wraithon, WraithonEntityPatch.class);
                                        if (entityPatch != null) {
                                            entityPatch.playAnimationSynchronized(AnimationManager.byKey(StringArgumentType.getString(commandContext, "anim_id")), 0.0001F);
                                        }
                                    }
                                    return 0;
                                })
                        )
                )
        );
    }

}
