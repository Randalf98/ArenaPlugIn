package io.github.randalf.project.commands;


import io.github.randalf.project.ArenaPlugIn;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

/**
 * CommandExecutor for stopping an arena
 */
public class ArenaStopCommand implements CommandExecutor {

    /**
     * Get's the ArenaManager and stops the arena if it exists.
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String mapName = args.<String>getOne("arenaName").get();
        if(ArenaPlugIn.getInstance().getArenaManager().mapContains(mapName)){
            ArenaPlugIn.getInstance().getArenaManager().stopArena(mapName);
        } else {
            src.sendMessage(Text.of("Couldn't stop, because there is no arena with that name \n For a list of all arenas type /arena list arena"));
        }
        return CommandResult.success();
    }
}
