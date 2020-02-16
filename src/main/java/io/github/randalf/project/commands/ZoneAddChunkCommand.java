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
 * CommandExecutor for adding a chunk to an arena
 */
public class ZoneAddChunkCommand implements CommandExecutor {

    /**
     * Get's the zoneName and the player who executed the command and adds the chunk to the zone
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String zoneName = args.<String>getOne("zoneName").get();
        src.sendMessage(Text.of("Chunk will be added to zone " + zoneName));
        Player player = (Player) src;
        ZoneManager.getInstance().addChunkToArena(zoneName, player);
        return CommandResult.success();
    }
}