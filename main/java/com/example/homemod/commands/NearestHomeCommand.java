package com.example.homemod.commands;

import com.example.homemod.HomeMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class NearestHomeCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> register(HomeMod mod) {
        return Commands.literal("nearesthome")
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    var homes = mod.getHomes(player.getUUID());

                    if (homes.isEmpty()) {
                        context.getSource().sendFailure(Component.literal("Vous n'avez défini aucune maison."));
                        return Command.SINGLE_SUCCESS;
                    }

                    HomeMod.Home closestHome = null;
                    double closestDistance = Double.MAX_VALUE;

                    for (HomeMod.Home home : homes.values()) {
                        double distance = player.position().distanceTo(home.level.getSharedSpawnPos());
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestHome = home;
                        }
                    }

                    if (closestHome != null) {
                        player.teleportTo(closestHome.level, closestHome.x, closestHome.y, closestHome.z, player.getYRot(), player.getXRot());
                        context.getSource().sendSuccess(Component.literal("Téléporté à la maison la plus proche !"), false);
                    }

                    return Command.SINGLE_SUCCESS;
                });
    }
}
