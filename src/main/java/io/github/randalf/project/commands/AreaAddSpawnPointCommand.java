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
 * CommandExecutor for adding a spawnpoint to an area
 */
public class AreaAddSpawnPointCommand implements CommandExecutor {

    /**
     * Get's the AreaManager and adds a spawnpoint at the players location.
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String areaName = args.<String>getOne("areaName").get();
        Player player = (Player) src;
        if(AreaManager.getInstance().mapContains(areaName)){
            AreaManager.getInstance().addSpawnPointToArea(areaName, player);
            src.sendMessage(Text.of("Spawnpoint was added to area " + areaName));
        }  else {
            src.sendMessage(Text.of("Area "  + areaName+  " doesn't exists. \n For a list of all areas type /arena list area"));
        }
        return CommandResult.success();
    }
}
