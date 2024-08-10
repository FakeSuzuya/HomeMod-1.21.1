package com.example.homemod.commands;

import com.example.homemod.HomeMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

public class HomeCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> register(HomeMod mod) {
        return Commands.literal("home")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            String homeName = StringArgumentType.getString(context, "name");

                            var homes = mod.getHomes(player.getUUID());
                            if (!homes.containsKey(homeName)) {
                                context.getSource().sendFailure(Component.literal("Aucune maison trouvée avec le nom " + homeName + "."));
                                return Command.SINGLE_SUCCESS;
                            }

                            HomeMod.Home home = homes.get(homeName);
                            ServerLevel world = (ServerLevel) home.level;
                            player.teleportTo(world, home.x, home.y, home.z, player.getYRot(), player.getXRot());
                            context.getSource().sendSuccess(Component.literal("Téléporté à la maison " + homeName + "."), false);
                            return Command.SINGLE_SUCCESS;
                        }));
    }
}
