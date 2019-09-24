package io.github.randalf.project.commands;

import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.arenaparts.spawner.Entitys;
import io.github.randalf.project.arenaparts.spawner.FloodMode;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

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
        if(ArenaManager.getInstance().mapContains(arenaName)){
            if(Entitys.contains(entityType)){
                ((FloodMode)ArenaManager.getInstance().getArena(arenaName).getMode()).setEntityType(entityType);
                Arena arena = ArenaManager.getInstance().getArena(arenaName);
                ArenaManager.getInstance().saveArena(arenaName, arena);
            } else {
                src.sendMessage(Text.of("The entity " + entityType+ " is not existent."));
            }
        } else {
            src.sendMessage(Text.of("The arena " + arenaName+ " is not existent. \n For a list of all arenas type /arena list arena"));
        }


        return CommandResult.success();
    }
}
