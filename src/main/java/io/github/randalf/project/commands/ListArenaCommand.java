package io.github.randalf.project.commands;

import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import java.util.Set;

/**
 * CommandExecutor for listing all arenas
 */
public class ListArenaCommand implements CommandExecutor {

    /**
     * Get's the ArenaManager and returns a list of the existing arenas
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Set<String> arenaNames = ArenaManager.getInstance().getArenaNames();
        String listOfArenas = "Existing Arenas: \n";
        for(String arena: arenaNames){
            listOfArenas += arena + "\n";
        }
        listOfArenas += "------------------------------";
        src.sendMessage(Text.of(listOfArenas));
        return CommandResult.success();
    }
}
