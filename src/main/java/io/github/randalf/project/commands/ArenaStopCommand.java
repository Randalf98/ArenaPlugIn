package io.github.randalf.project.commands;


import io.github.randalf.project.arenaparts.ArenaSecurity;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class ArenaStopCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("Arena will stop now - if implemented"));
        Sponge.getEventManager().unregisterListeners(ArenaSecurity.getInstance());
        return CommandResult.success();
    }
}
