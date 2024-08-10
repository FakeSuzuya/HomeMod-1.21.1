package com.example.homemod.commands;

import com.example.homemod.HomeMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class DelHomeCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> register(HomeMod mod) {
        return Commands.literal("delhome")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            String homeName = StringArgumentType.getString(context, "name");

                            var homes = mod.getHomes(player.getUUID());
                            if (!homes.containsKey(homeName)) {
                                context.getSource().sendFailure(Component.literal("Aucune maison trouvée avec le nom " + homeName + "."));
                                return Command.SINGLE_SUCCESS;
                            }

                            homes.remove(homeName);
                            context.getSource().sendSuccess(Component.literal("Maison " + homeName + " supprimée avec succès !"), false);
                            return Command.SINGLE_SUCCESS;
                        }));
    }
}
