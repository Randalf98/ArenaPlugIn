package io.github.randalf.project.commands;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Zone;
import io.github.randalf.project.manager.ZoneManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * CommandExecutor for getting the information of an arena
 */
public class ZoneInformationCommand implements CommandExecutor {

    /**
     * Lists information of a given arena
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String zoneName = "";
        if (args.<String>getOne("zoneName").isPresent()) {
            zoneName = args.<String>getOne("zoneName").get();
        }
        if(ZoneManager.getInstance().mapContains(zoneName)){
            Zone zone = ZoneManager.getInstance().getZone(zoneName);
            String zoneInfo = "Arena " + zoneName + " contains:\n";
            zoneInfo += "World UUID: " + zone.getWorldUUID() + "\n";
            zoneInfo += "Zone Chunks:\n";
            for(Vector3i chunk : zone.getZoneChunks()) {
                zoneInfo += chunk.toString() + "\n";
            }
            zoneInfo += "Zone Spawn Locations:\n";
            for(Vector3d spawnlocations : zone.getSpawnLocations()) {
                zoneInfo += spawnlocations.toString() + "\n";
            }
            zoneInfo += "Player in Zone:\n";
            for(Player player : zone.getPlayerInZone()) {
                zoneInfo += player.getName() + "\n";
            }
            zoneInfo += "------------------------------";
            src.sendMessage(Text.of(zoneInfo));
        } else {
            src.sendMessage(Text.of("Zone doesn't exists"));
        }
        return CommandResult.success();
    }
}
