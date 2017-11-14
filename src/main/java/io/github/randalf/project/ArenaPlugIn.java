package io.github.randalf.project;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.commands.ArenaStartCommand;
import io.github.randalf.project.serializer.ArenaSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import javax.inject.Inject;
import javax.inject.Singleton;

@Plugin(id = ArenaPlugIn.PLUGIN_ID, name = "Arena", version = "1.0", description = "A little arena Plug-In")
@Singleton
public class ArenaPlugIn {

    public static final String PLUGIN_ID = "arena";

    public static ArenaPlugIn instance;
    private ArenaManager arenaManager;

    @Inject
    Game game;

    @Inject
    Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        // Hey! The server has started!
        // Try instantiating your logger in here.
        // (There's a guide for that)
    }

    @Listener
    public void onInitialization(GameInitializationEvent event) {
        setupCommands();
        setupSerializer();
        arenaManager = ArenaManager.getInstance();
        instance = this;
    }

    public static ArenaPlugIn getInstance(){
        return instance;
    }

    private void setupCommands() {
        //Command Spec for starting a Round
        CommandSpec arenaStartCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Start Command"))
                .permission("io.github.randalf.startArena")
                .executor(new ArenaStartCommand())
                .build();

        //Command Spec for stopping a Round
        CommandSpec arenaStopCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Stop Command"))
                .permission("io.github.randalf.stopArena")
                .executor(new ArenaStartCommand())
                .build();

        Sponge.getCommandManager().register(this, arenaStartCommandSpec, "startArena");
        Sponge.getCommandManager().register(this, arenaStopCommandSpec, "stopArena");
    }

    private void setupSerializer() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Arena.class), new ArenaSerializer());
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}
