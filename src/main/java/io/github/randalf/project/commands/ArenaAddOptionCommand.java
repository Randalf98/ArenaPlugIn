package io.github.randalf.project.commands;

import io.github.randalf.project.arenaparts.ArenaOptions;
import io.github.randalf.project.manager.ArenaConfigurationManager;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

/**
 * CommandExecutor for adding an option to a given arena
 */
public class ArenaAddOptionCommand implements CommandExecutor {

    /**
     * Adds an option from a given arena
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String arenaName = args.<String>getOne("arenaName").get();
        String option = args.<String>getOne("arenaOption").get();
        ArenaManager.getInstance().getArena(arenaName).getALM().addOption(ArenaOptions.valueOf(option));
        new ArenaConfigurationManager(arenaName, ArenaManager.getInstance().getArena(arenaName)).save();
        return CommandResult.success();
    }
}
