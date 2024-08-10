package com.example.homemod;

import com.example.homemod.commands.*;
import com.example.homemod.events.TeleportEventHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod(HomeMod.MODID)
public class HomeMod {
    public static final String MODID = "homemod";
    
    private final Map<UUID, Map<String, Home>> playerHomes = new HashMap<>();
    private final Map<UUID, String> playerLanguages = new HashMap<>();
    
    public HomeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Enregistrement des commandes
        CommandDispatcher<CommandSourceStack> dispatcher = event.getServer().getCommands().getDispatcher();
        dispatcher.register(SetHomeCommand.register(this));
        dispatcher.register(HomeCommand.register(this));
        dispatcher.register(DelHomeCommand.register(this));
        dispatcher.register(HomesCommand.register(this));
        dispatcher.register(NearestHomeCommand.register(this));
        dispatcher.register(ChangeLanguageCommand.register(this));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Initialisation lors du démarrage du serveur
    }

    public Map<String, Home> getHomes(UUID playerId) {
        return playerHomes.computeIfAbsent(playerId, k -> new HashMap<>());
    }

    public void setPlayerLanguage(UUID playerId, String language) {
        playerLanguages.put(playerId, language);
    }

    public String getPlayerLanguage(UUID playerId) {
        return playerLanguages.getOrDefault(playerId, "fr_fr");
    }

    public String translate(String key, UUID playerId) {
        String language = getPlayerLanguage(playerId);
        return LanguageRegistry.getTranslation(key, language);
    }

    public static class Home {
        public final ServerPlayer level;
        public final double x, y, z;

        public Home(ServerPlayer level, double x, double y, double z) {
            this.level = level;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
