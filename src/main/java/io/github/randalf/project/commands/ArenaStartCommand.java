package io.github.randalf.project.commands;

import io.github.randalf.project.arenaparts.ArenaController;
import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.arenaparts.ArenaMode;
import io.github.randalf.project.arenaparts.ArenaSecurity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class ArenaStartCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("Arena will start soon - if implemented"));
        String arenaName = "";
        if (args.<String>getOne("arenaName").isPresent()) {
            arenaName = args.<String>getOne("arenaName").get();
        }
        ArenaPlugIn.getInstance().getArenaManager().addArena(arenaName, new ArenaController(arenaName, ArenaMode.DEFAULT, ArenaSecurity.DEFAULT));
        ArenaPlugIn.getInstance().getArenaManager().startArena(arenaName);
        return CommandResult.success();
    }
}
