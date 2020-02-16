package io.github.randalf.project;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Zone;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.commands.*;
import io.github.randalf.project.manager.ArenaManager;
import io.github.randalf.project.serializer.ZoneSerializer;
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
import org.spongepowered.api.event.game.state.*;
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

    @Listener
    public void onGameStoppingServerEvent(GameStoppingServerEvent event){
        ArenaPlugIn.getInstance().getArenaManager().stopAllArenas();
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

        //Command Spec for creating a Zone
        CommandSpec arenaCreateCommandSpec = CommandSpec.builder()
                .description(Text.of("Arena Create Command"))
                .permission("io.github.randalf.arena.create.arena")
                .arguments(GenericArguments.string(Text.of("arenaName")),GenericArguments.string(Text.of("zoneName")))
                .executor(new ArenaCreateCommand())
                .build();

        //Command Spec for creating a Zone
        CommandSpec zoneCreateCommandSpec = CommandSpec.builder()
                .description(Text.of("Zone Create Command"))
                .permission("io.github.randalf.arena.create.zone")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("zoneName")))
                .executor(new ZoneCreateCommand())
                .build();

        //Command Spec for creating commands
        CommandSpec createCommandSpec = CommandSpec.builder()
                .description(Text.of("Create Arena/Zone"))
                .permission("io.github.randalf.arena.create")
                .child(arenaCreateCommandSpec, "arena")
                .child(zoneCreateCommandSpec, "zone")
                .build();

        //Command Spec for adding a chunk to zone
        CommandSpec zoneAddChunkCommandSpec = CommandSpec.builder()
                .description(Text.of("Add Chunk Command"))
                .permission("io.github.randalf.arena.chunk.add")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("zoneName")))
                .executor(new ZoneAddChunkCommand())
                .build();

        //Command Spec for removing a chunk from the zone
        CommandSpec zoneRemoveChunkCommandSpec = CommandSpec.builder()
                .description(Text.of("Remove Chunk Command"))
                .permission("io.github.randalf.arena.chunk.remove")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("zoneName")))
                .executor(new ZoneRemoveChunkCommand())
                .build();

        //Command Spec for chunk commands
        CommandSpec chunkCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Zone Chunks"))
                .permission("io.github.randalf.arena.chunk")
                .child(zoneAddChunkCommandSpec, "add")
                .child(zoneRemoveChunkCommandSpec, "remove")
                .build();

        //Command Spec for adding a spawnpoint to zone
        CommandSpec zoneAddSpawnPointCommandSpec = CommandSpec.builder()
                .description(Text.of("Add Spawnpoint Command"))
                .permission("io.github.randalf.arena.spawnpoint.add")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("zoneName")))
                .executor(new ZoneAddSpawnPointCommand())
                .build();

        //Command Spec for removing a spawnpoint from the zone
        CommandSpec zoneRemoveSpawnPointCommandSpec = CommandSpec.builder()
                .description(Text.of("Remove Spawnpoint Command"))
                .permission("io.github.randalf.arena.spawnpoint.remove")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("zoneName")))
                .executor(new ZoneRemoveSpawnPointCommand())
                .build();

        //Command Spec for spawnpoint commands
        CommandSpec spawnpointCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Zone Chunks"))
                .permission("io.github.randalf.arena.spawnpoint")
                .child(zoneAddSpawnPointCommandSpec, "add")
                .child(zoneRemoveSpawnPointCommandSpec, "remove")
                .build();

        //Command Spec for listing all zones
        CommandSpec listZonesCommandSpec = CommandSpec.builder()
                .description(Text.of("List Zones Command"))
                .permission("io.github.randalf.arena.list.zone")
                .executor(new ListZoneCommand())
                .build();

        //Command Spec for listing all arenas
        CommandSpec listArenasCommandSpec = CommandSpec.builder()
                .description(Text.of("List Arenas Command"))
                .permission("io.github.randalf.arena.list.arena")
                .executor(new ListArenaCommand())
                .build();

        //Command Spec for list commands
        CommandSpec listCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Zone Chunks"))
                .permission("io.github.randalf.arena.list")
                .child(listZonesCommandSpec, "zone")
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
        CommandSpec zoneInformationCommandSpec = CommandSpec.builder()
                .description(Text.of("Zone Information Command"))
                .permission("io.github.randalf.arena.information.zone")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("zoneName")))
                .executor(new ZoneInformationCommand())
                .build();

        //Command Spec for list commands
        CommandSpec informationCommandSpec = CommandSpec.builder()
                .description(Text.of("Get Information about Zone/Arena"))
                .permission("io.github.randalf.arena.information")
                .child(zoneInformationCommandSpec, "zone")
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
                .description(Text.of("Manage Zone Chunks"))
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
                .description(Text.of("Manage Zone Chunks"))
                .permission("io.github.randalf.arena.option")
                .child(arenaAddOptionCommandSpec, "add")
                .child(arenaRemoveOptionCommandSpec, "remove")
                .build();

        //Command Spec for removing an option from an arena
        CommandSpec arenaCommandSpec = CommandSpec.builder()
                .description(Text.of("Manage Arena/Zone"))
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
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Zone.class), new ZoneSerializer());
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
