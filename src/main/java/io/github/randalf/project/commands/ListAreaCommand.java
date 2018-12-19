package io.github.randalf.project.commands;

import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Set;

/**
 * Created by randalf on 28.11.2017.
 */
public class ListAreaCommand implements CommandExecutor {

    /**
     * Command implementing CommandExecutor.
     * List all existing Areas
     * Get's the AreaManager and lists the existing areas
     *
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Set<String> areaNames = AreaManager.getInstance().getAreaNames();
        String listOfAreas = "Existing Areas: \n";
        for(String area: areaNames){
            listOfAreas += area + "\n";
        }
        listOfAreas += "------------------------------";
        src.sendMessage(Text.of(listOfAreas));
        return CommandResult.success();
    }
}
