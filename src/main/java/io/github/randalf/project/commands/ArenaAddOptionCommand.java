package io.github.randalf.project.commands;

import io.github.randalf.project.arenaparts.ArenaOptions;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;


public class ArenaAddOptionCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String arenaName = args.<String>getOne("areaName").get();
        String option = args.<String>getOne("option").get();
        ArenaManager.getInstance().getArena(arenaName).setOption(ArenaOptions.valueOf(option), true);
        return CommandResult.success();
    }
}
