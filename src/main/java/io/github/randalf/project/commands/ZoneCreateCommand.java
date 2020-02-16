package io.github.randalf.project.commands;

import io.github.randalf.project.manager.ZoneManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * CommandExecutor for creating an arena
 */
public class ZoneCreateCommand implements CommandExecutor {

    /**
     * Get's the ZoneManager and create and saves the new zone.
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String zoneName = args.<String>getOne("zoneName").get();
        src.sendMessage(Text.of("Zone " + zoneName + " will be created"));
        Player player = (Player) src;
        ZoneManager.getInstance().createZone(zoneName, player);
        return CommandResult.success();
    }
}
