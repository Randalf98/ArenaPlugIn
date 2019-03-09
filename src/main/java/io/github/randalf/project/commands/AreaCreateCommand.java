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
 * CommandExecutor for creating an arena
 */
public class AreaCreateCommand implements CommandExecutor {

    /**
     * Get's the AreaManager and create and saves the new area.
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String areaName = args.<String>getOne("areaName").get();
        src.sendMessage(Text.of("Area " + areaName + " will be created"));
        Player player = (Player) src;
        AreaManager.getInstance().createArea(areaName, player);
        return CommandResult.success();
    }
}
