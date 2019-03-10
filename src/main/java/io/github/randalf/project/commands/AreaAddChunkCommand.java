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
 * CommandExecutor for adding a chunk to an arena
 */
public class AreaAddChunkCommand implements CommandExecutor {

    /**
     * Get's the areaName and the player who executed the command and adds the chunk to the area
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("Chunk will be added soon - if implemented"));
        String areaName = args.<String>getOne("areaName").get();
        Player player = (Player) src;
        AreaManager.getInstance().addChunkToArena(areaName, player);
        return CommandResult.success();
    }
}