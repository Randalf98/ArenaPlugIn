package io.github.randalf.project.commands;


import io.github.randalf.project.ArenaPlugIn;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class ArenaStopCommand implements CommandExecutor {

    /**
     * Command implementing CommandExecutor.Command implementing CommandExecutor.
     * Stops an arena if it exists.
     * Get's the AreaManager and starts the arena if it exists.
     *
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String mapName = args.<String>getOne("arenaName").get();
        src.sendMessage(Text.of("Arena " + mapName +  " will stop now"));
        if(ArenaPlugIn.getInstance().getArenaManager().mapContains(mapName)){
            ArenaPlugIn.getInstance().getArenaManager().stopArena(mapName);
        } else {
            src.sendMessage(Text.of("Couldn't stop, arena doesn't exist."));
        }
        return CommandResult.success();
    }
}
