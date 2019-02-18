package io.github.randalf.project.commands;

import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class ArenaCreateCommand implements CommandExecutor {

    /**
     * Command implementing CommandExecutor.
     * Creates an arena.
     * Get's the ArenaManager and create and saves the new arena with a given area.
     *
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("Arena will be created soon - if implemented"));
        String arenaName = args.<String>getOne("arenaName").get();
        String areaName = args.<String>getOne("areaName").get();
        ArenaManager.getInstance().createArena(arenaName,areaName);
        return CommandResult.success();
    }
}
