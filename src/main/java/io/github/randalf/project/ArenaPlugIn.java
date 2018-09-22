package io.github.randalf.project;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.commands.*;
import io.github.randalf.project.manager.ArenaManager;
import io.github.randalf.project.serializer.AreaSerializer;
import io.github.randalf.project.serializer.Vector3dSerializer;
import io.github.randalf.project.serializer.Vector3iSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import javax.inject.Inject;
import javax.inject.Singleton;

@Plugin(id = ArenaPlugIn.PLUGIN_ID, name = "Area", version = "1.0", description = "A little arena Plug-In")
@Singleton
public class ArenaPlugIn {

    public static final String PLUGIN_ID = "arena";
    private static ArenaPlugIn instance;
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
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arenaName")))
                .executor(new ArenaStartCommand())
                .build();

        //Command Spec for stopping a Round
        CommandSpec arenaStopCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Stop Command"))
                .permission("io.github.randalf.stopArena")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arenaName")))
                .executor(new ArenaStopCommand())
                .build();

        //Command Spec for creating a Area
        CommandSpec arenaCreateCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Create Command"))
                .permission("io.github.randalf.createArena")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("areaName")))
                .executor(new ArenaCreateCommand())
                .build();

        //Command Spec for creating a Area
        CommandSpec areaCreateCommandSpec = CommandSpec.builder()
                .description(Text.of("Area Create Command"))
                .permission("io.github.randalf.createArea")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaCreateCommand())
                .build();

        //Command Spec for adding a Chunk to a Area
        CommandSpec areaAddChunkCommandSpec = CommandSpec.builder()
                .description(Text.of("Add Chunk Command"))
                .permission("io.github.randalf.addChunkToArea")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaAddChunkCommand())
                .build();

        //Command Spec for adding a Chunk to a Area
        CommandSpec areaAddSpawnPointCommandSpec = CommandSpec.builder()
                .description(Text.of("Add Spawnpoint Command"))
                .permission("io.github.randalf.addSpawnPointToArea")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaAddSpawnPointCommand())
                .build();

        Sponge.getCommandManager().register(this, arenaStartCommandSpec, "startArena");
        Sponge.getCommandManager().register(this, arenaStopCommandSpec, "stopArena");
        Sponge.getCommandManager().register(this, arenaCreateCommandSpec, "createArena");
        Sponge.getCommandManager().register(this, areaCreateCommandSpec, "createArea");
        Sponge.getCommandManager().register(this, areaAddChunkCommandSpec, "addChunkToArea");
        Sponge.getCommandManager().register(this, areaAddSpawnPointCommandSpec, "addSpawnPointToArea");
    }

    private void setupSerializer() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Area.class), new AreaSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Vector3d.class), new Vector3dSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Vector3i.class), new Vector3iSerializer());
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}
