package io.github.randalf.project.commands;

import io.github.randalf.project.arenaparts.ArenaOptions;
import io.github.randalf.project.manager.ArenaConfigurationManager;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

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
        ArenaOptions arenaOption = null;
        try{
            arenaOption = ArenaOptions.valueOf(option);
        } catch (IllegalArgumentException ex){
            src.sendMessage(Text.of("This option " + option + ". Is not legit, legit options are:"));
            for(ArenaOptions aOptions :ArenaOptions.values()){
                src.sendMessage(Text.of(aOptions));
            }
        }
        if(ArenaManager.getInstance().mapContains(arenaName)){
            if (arenaOption!=null){
                ArenaManager.getInstance().getArena(arenaName).getALM().addOption(arenaOption);
                new ArenaConfigurationManager(arenaName, ArenaManager.getInstance().getArena(arenaName)).save();
            }
        } else {
            src.sendMessage(Text.of("The arena " + arenaName+ " is not existent. \n For a list of all arenas type /arena list arena"));
        }
        return CommandResult.success();
    }
}
