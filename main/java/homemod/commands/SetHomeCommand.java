package com.example.homemod.commands;

import com.example.homemod.HomeMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetHomeCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> register(HomeMod mod) {
        return Commands.literal("sethome")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            String homeName = StringArgumentType.getString(context, "name");

                            var homes = mod.getHomes(player.getUUID());
                            if (homes.size() >= 5) {
                                context.getSource().sendFailure(Component.literal("Vous avez atteint le nombre maximum de maisons (5)."));
                                return Command.SINGLE_SUCCESS;
                            }

                            if (homes.containsKey(homeName)) {
                                context.getSource().sendFailure(Component.literal("Une maison avec ce nom existe déjà."));
                                return Command.SINGLE_SUCCESS;
                            }

                            HomeMod.Home newHome = new HomeMod.Home(player.getLevel(), player.getX(), player.getY(), player.getZ());
                            homes.put(homeName, newHome);
                            context.getSource().sendSuccess(Component.literal("Maison " + homeName + " définie avec succès !"), false);

                            return Command.SINGLE_SUCCESS;
                        }));
    }
}
