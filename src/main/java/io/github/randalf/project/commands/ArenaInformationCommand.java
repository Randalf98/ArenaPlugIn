package io.github.randalf.project.commands;

import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

/**
 * CommandExecutor for getting the information of an arena
 */
public class ArenaInformationCommand implements CommandExecutor {

    /**
     * Lists information of a given arena
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
            StringBuilder arenaInfo = new StringBuilder().append("Arena ").append(arenaName).append(" contains:\n");
            arenaInfo.append("Area Name: ").append(arena.getArea().getAreaName()).append("\n");
            arenaInfo.append("------------------------------");
            src.sendMessage(Text.of(arenaInfo));
        } else {
            src.sendMessage(Text.of("Arena doesn't exists"));
        }
        return CommandResult.success();
    }
}
