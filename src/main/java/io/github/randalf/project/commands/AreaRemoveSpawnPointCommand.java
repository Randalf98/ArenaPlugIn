package io.github.randalf.project.commands;

import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * CommandExecutor for removing a spawnpoint from the area
 */
public class AreaRemoveSpawnPointCommand implements CommandExecutor {

    /**
     * Get's the AreaManager and removes a spawnpoint from the players location.
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String areaName = args.<String>getOne("areaName").get();
        Player player = (Player) src;
        AreaManager.getInstance().removeSpawnPointFromArea(areaName, player);
        return CommandResult.success();
    }
}
