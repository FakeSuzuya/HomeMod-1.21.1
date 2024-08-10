package com.example.homemod.commands;

import com.example.homemod.HomeMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class HomesCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> register(HomeMod mod) {
        return Commands.literal("homes")
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    var homes = mod.getHomes(player.getUUID());

                    if (homes.isEmpty()) {
                        context.getSource().sendFailure(Component.literal("Vous n'avez défini aucune maison."));
                        return Command.SINGLE_SUCCESS;
                    }

                    StringBuilder homeList = new StringBuilder("Vos maisons: ");
                    for (String name : homes.keySet()) {
                        homeList.append(name).append(", ");
                    }

                    context.getSource().sendSuccess(Component.literal(homeList.toString()), false);
                    return Command.SINGLE_SUCCESS;
                });
    }
}
