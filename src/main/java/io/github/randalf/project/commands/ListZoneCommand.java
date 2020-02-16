package io.github.randalf.project.commands;

import io.github.randalf.project.manager.ZoneManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import java.util.Set;

/**
 * CommandExecutor for listing all zones
 */
public class ListZoneCommand implements CommandExecutor {

    /**
     * List all existing Zones
     * Get's the ZoneManager and lists the existing zones
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Set<String> zoneNames = ZoneManager.getInstance().getZoneNames();
        String listOfZones = "Existing Zones: \n";
        for(String zone: zoneNames){
            listOfZones += zone + "\n";
        }
        listOfZones += "------------------------------";
        src.sendMessage(Text.of(listOfZones));
        return CommandResult.success();
    }
}
