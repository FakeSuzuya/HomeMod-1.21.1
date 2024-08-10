package com.example.homemod.commands;

import com.example.homemod.HomeMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ChangeLanguageCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> register(HomeMod mod) {
        return Commands.literal("changelang")
                .then(Commands.argument("language", StringArgumentType.string())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            String langCode = StringArgumentType.getString(context, "language");

                            if (!LanguageRegistry.isLanguageSupported(langCode)) {
                                context.getSource().sendFailure(Component.literal("Langue non supportée: " + langCode));
                                return Command.SINGLE_SUCCESS;
                            }

                            mod.setPlayerLanguage(player.getUUID(), langCode);
                            context.getSource().sendSuccess(Component.literal("Langue changée en: " + langCode), false);
                            return Command.SINGLE_SUCCESS;
                        }));
    }
}
