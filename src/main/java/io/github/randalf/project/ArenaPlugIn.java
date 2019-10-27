package io.github.randalf.project;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.commands.*;
import io.github.randalf.project.manager.ArenaManager;
import io.github.randalf.project.serializer.AreaSerializer;
import io.github.randalf.project.serializer.ArenaSerializer;
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
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * An arena plugin for Sponge server to let the player fight monsters.
 *
 * @author Randalf
 * @version 1.0
 */
@Plugin(id = ArenaPlugIn.PLUGIN_ID, name = "Arena", version = "1.0", description = "A little arena Plug-In")
@Singleton
public class ArenaPlugIn {

    public static final String PLUGIN_ID = "arena";
    private static ArenaPlugIn instance;

    @Inject
    Game game;

    @Inject
    Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {}

    /**
     * When the Plugin gets initialized the function gets executed.
     * @param event Default GameInitializationEvent from the server
     */
    @Listener
    public void onInitialization(GameInitializationEvent event) {
        instance = this;
        setupCommands();
        setupSerializer();
    }

    /**
     * When the Plugin gets initialized the function gets executed.
     * @param event Default GameInitializationEvent from the server
     */
    @Listener
    public void onTermination(GameStoppingEvent event) {
        this.getArenaManager().stopAllArenas();
    }

    /**
     * Functionality to get the singleton instance
     * @return The instance of this Plugin
     */
    public static ArenaPlugIn getInstance(){
        return instance;
    }

    /**
     * Routine for setting up all commands
     */
    private void setupCommands() {
        //Command Spec for starting a Round
        CommandSpec arenaStartCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Start Command"))
                .permission("io.github.randalf.arena.start")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arenaName")))
                .executor(new ArenaStartCommand())
                .build();

        //Command Spec for stopping a Round
        CommandSpec arenaStopCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Stop Command"))
                .permission("io.github.randalf.arena.stop")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arenaName")))
                .executor(new ArenaStopCommand())
                .build();

        //Command Spec for creating a Area
        CommandSpec arenaCreateCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Create Command"))
                .permission("io.github.randalf.arena.create.arena")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("areaName")))
                .executor(new ArenaCreateCommand())
                .build();

        //Command Spec for creating a Area
        CommandSpec areaCreateCommandSpec = CommandSpec.builder()
                .description(Text.of("Area Create Command"))
                .permission("io.github.randalf.arena.create.area")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaCreateCommand())
                .build();

        //Command Spec for creating commands
        CommandSpec createCommandSpec = CommandSpec.builder()
                .description(Text.of("Create Arena/Area"))
                .permission("io.github.randalf.arena.create")
                .child(arenaCreateCommandSpec, "arena")
                .child(areaCreateCommandSpec, "area")
                .build();

        //Command Spec for adding a chunk to area
        CommandSpec areaAddChunkCommandSpec = CommandSpec.builder()
                .description(Text.of("Add Chunk Command"))
                .permission("io.github.randalf.arena.chunk.add")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaAddChunkCommand())
                .build();

        //Command Spec for removing a chunk from the area
        CommandSpec areaRemoveChunkCommandSpec = CommandSpec.builder()
                .description(Text.of("Remove Chunk Command"))
                .permission("io.github.randalf.arena.chunk.remove")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaRemoveChunkCommand())
                .build();

        //Command Spec for chunk commands
        CommandSpec chunkCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Area Chunks"))
                .permission("io.github.randalf.arena.chunk")
                .child(areaAddChunkCommandSpec, "add")
                .child(areaRemoveChunkCommandSpec, "remove")
                .build();

        //Command Spec for adding a spawnpoint to area
        CommandSpec areaAddSpawnPointCommandSpec = CommandSpec.builder()
                .description(Text.of("Add Spawnpoint Command"))
                .permission("io.github.randalf.arena.spawnpoint.add")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaAddSpawnPointCommand())
                .build();

        //Command Spec for removing a spawnpoint from the area
        CommandSpec areaRemoveSpawnPointCommandSpec = CommandSpec.builder()
                .description(Text.of("Remove Spawnpoint Command"))
                .permission("io.github.randalf.arena.spawnpoint.remove")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaRemoveSpawnPointCommand())
                .build();

        //Command Spec for spawnpoint commands
        CommandSpec spawnpointCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Area Chunks"))
                .permission("io.github.randalf.arena.spawnpoint")
                .child(areaAddSpawnPointCommandSpec, "add")
                .child(areaRemoveSpawnPointCommandSpec, "remove")
                .build();

        //Command Spec for listing all areas
        CommandSpec listAreasCommandSpec = CommandSpec.builder()
                .description(Text.of("List Areas Command"))
                .permission("io.github.randalf.arena.list.area")
                .executor(new ListAreaCommand())
                .build();

        //Command Spec for listing all arenas
        CommandSpec listArenasCommandSpec = CommandSpec.builder()
                .description(Text.of("List Arenas Command"))
                .permission("io.github.randalf.arena.list.arena")
                .executor(new ListArenaCommand())
                .build();

        //Command Spec for list commands
        CommandSpec listCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Area Chunks"))
                .permission("io.github.randalf.arena.list")
                .child(listAreasCommandSpec, "area")
                .child(listArenasCommandSpec, "arena")
                .build();

        //Command Spec for giving the information of an arena
        CommandSpec arenaInformationCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Information Command"))
                .permission("io.github.randalf.arena.information.arena")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arenaName")))
                .executor(new ArenaInformationCommand())
                .build();

        //Command Spec for giving the information of an arena
        CommandSpec areaInformationCommandSpec = CommandSpec.builder()
                .description(Text.of("Area Information Command"))
                .permission("io.github.randalf.arena.information.area")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("areaName")))
                .executor(new AreaInformationCommand())
                .build();

        //Command Spec for list commands
        CommandSpec informationCommandSpec = CommandSpec.builder()
                .description(Text.of("Get Information about Area/Arena"))
                .permission("io.github.randalf.arena.information")
                .child(areaInformationCommandSpec, "area")
                .child(arenaInformationCommandSpec, "arena")
                .build();

        //Command Spec for setting the entity type for a flood arena
        CommandSpec arenaSetFloodModeEntityTypeCommandSpec = CommandSpec.builder()
                .description(Text.of("Sets Flood Mode Entity Type"))
                .permission("io.github.randalf.arena.set.entitytype")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("entityType")))
                .executor(new ArenaSetFloodModeEntityTypeCommand())
                .build();

        //Command Spec for setting the entity amount for a flood arena
        CommandSpec arenaSetFloodModeEntityAmountCommandSpec = CommandSpec.builder()
                .description(Text.of("Sets Flood Mode Entity Amount"))
                .permission("io.github.randalf.arena.set.entityamount")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("entityAmount")))
                .executor(new ArenaSetFloodModeEntityAmountCommand())
                .build();

        //Command Spec for set commands
        CommandSpec setCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Area Chunks"))
                .permission("io.github.randalf.arena.set")
                .child(arenaSetFloodModeEntityTypeCommandSpec, "entitytype")
                .child(arenaSetFloodModeEntityAmountCommandSpec, "entityamount")
                .build();

        //Command Spec for adding an option to an arena
        CommandSpec arenaAddOptionCommandSpec = CommandSpec.builder()
                .description(Text.of("Adds an option to the arena"))
                .permission("io.github.randalf.arena.options.add")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("arenaOption")))
                .executor(new ArenaAddOptionCommand())
                .build();

        //Command Spec for removing an option from an arena
        CommandSpec arenaRemoveOptionCommandSpec = CommandSpec.builder()
                .description(Text.of("Removes an option to the arena"))
                .permission("io.github.randalf.arena.options.remove")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("arenaOption")))
                .executor(new ArenaRemoveOptionCommand())
                .build();

        //Command Spec for options commands
        CommandSpec optionCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Area Chunks"))
                .permission("io.github.randalf.arena.option")
                .child(arenaAddOptionCommandSpec, "add")
                .child(arenaRemoveOptionCommandSpec, "remove")
                .build();

        //Command Spec for removing an option from an arena
        CommandSpec arenaCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Arena/Area"))
                .permission("io.github.randalf.arena")
                .child(arenaStartCommandSpec, "start")
                .child(arenaStopCommandSpec, "stop")
                .child(createCommandSpec, "create")
                .child(chunkCommandSpec, "chunk")
                .child(spawnpointCommandSpec, "spawnpoint")
                .child(listCommandSpec, "list")
                .child(informationCommandSpec, "information")
                .child(setCommandSpec, "set")
                .child(optionCommandSpec, "option")
                .build();

        //Adding all command Specs to the commang manager
        Sponge.getCommandManager().register(this, arenaCommandSpec, "arena"); }

    /**
     * Routine for registring the Typetoken for Serialization and Deserialization
     */
    private void setupSerializer() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Arena.class), new ArenaSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Area.class), new AreaSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Vector3d.class), new Vector3dSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Vector3i.class), new Vector3iSerializer());
    }

    /**
     * Getter for the ArenaManager instance
     * @return
     */
    public ArenaManager getArenaManager() {
        return ArenaManager.getInstance();
    }
}
