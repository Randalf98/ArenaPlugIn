package io.github.randalf.project.commands;

import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.manager.AreaManager;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by randalf on 28.11.2017.
 */
public class ArenaInformationCommand implements CommandExecutor {

    /**
     * Command implementing CommandExecutor.
     * Lists information of a given arena
     * Get's the ArenaManager and lists the existing arenas
     *
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String arenaName = "";
        if (args.<String>getOne("arenaName").isPresent()) {
            arenaName = args.<String>getOne("arenaName").get();
        }
        if(ArenaPlugIn.getInstance().getArenaManager().mapContains(arenaName)){
            Arena arena = ArenaPlugIn.getInstance().getArenaManager().getArena(arenaName);
            String arenaInfo = "Arena " + arenaName + " contains:\n";
            arenaInfo += "Area Name: " + arena.getArea().getAreaName() + "\n";
            arenaInfo += "------------------------------";
            src.sendMessage(Text.of(arenaInfo));
        } else {
            src.sendMessage(Text.of("Arena doesn't exists"));
        }
        return CommandResult.success();
    }
}
