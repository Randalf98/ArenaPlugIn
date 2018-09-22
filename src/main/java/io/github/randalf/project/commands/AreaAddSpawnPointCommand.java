package io.github.randalf.project.commands;

import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class AreaAddSpawnPointCommand implements CommandExecutor {

    /**
     * Command implementing CommandExecutor.
     * Adds a spawnpoint to a given area.
     * Get's the AreaManager and adds a spawnpoint at the players location.
     *
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("Chunk will be added soon - if implemented"));
        String areaName = args.<String>getOne("areaName").get();
        Player player = (Player) src;
        AreaManager.getInstance().addSpawnPointToArea(areaName, player);
        return CommandResult.success();
    }
}
