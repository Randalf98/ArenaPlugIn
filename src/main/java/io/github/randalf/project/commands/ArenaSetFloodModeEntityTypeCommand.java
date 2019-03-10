package io.github.randalf.project.commands;

import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.arenaparts.spawner.FloodMode;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

/**
 * CommandExecutor for setting the entity tyoe in an arena
 */
public class ArenaSetFloodModeEntityTypeCommand implements CommandExecutor {

    /**
     * Sets EntityType of FloodMode
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String arenaName = args.<String>getOne("arenaName").get();
        String entityType = args.<String>getOne("entityType").get();
        ((FloodMode)ArenaManager.getInstance().getArena(arenaName).getMode()).setEntityType(entityType);
        Arena arena = ArenaManager.getInstance().getArena(arenaName);
        ArenaManager.getInstance().saveArena(arenaName, arena);
        return CommandResult.success();
    }
}
