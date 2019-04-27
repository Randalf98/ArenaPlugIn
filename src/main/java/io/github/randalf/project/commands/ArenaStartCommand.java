package io.github.randalf.project.commands;

import io.github.randalf.project.ArenaPlugIn;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

/**
 * CommandExecutor for starting an arena
 */
public class ArenaStartCommand implements CommandExecutor {

    /**
     * Get's the ArenaManager and starts the arena if it exists.
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String arenaName = "";
        if (args.<String>getOne("arenaName").isPresent()) {
            arenaName = args.<String>getOne("arenaName").get();
        }
        if(ArenaPlugIn.getInstance().getArenaManager().mapContains(arenaName)){
            src.sendMessage(Text.of("Arena " + arenaName + " starts"));
            ArenaPlugIn.getInstance().getArenaManager().startArena(arenaName);
            src.sendMessage(Text.of("Arena started"));
        } else {
            src.sendMessage(Text.of("Arena couldn't start because it doesn't exist"));
        }
        return CommandResult.success();
    }
}
