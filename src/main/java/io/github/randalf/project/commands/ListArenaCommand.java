package io.github.randalf.project.commands;

import io.github.randalf.project.manager.AreaManager;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Set;

/**
 * Created by randalf on 28.11.2017.
 */
public class ListArenaCommand implements CommandExecutor {

    /**
     * Command implementing CommandExecutor.
     * List all existing Arenas
     * Get's the ArenaManager and lists the existing arenas
     *
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
