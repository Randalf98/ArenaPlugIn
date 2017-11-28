package io.github.randalf.project.commands;

import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * Created by b7hunnn on 28.11.2017.
 */
public class AreaCreateCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("Area will be created soon - if implemented"));
        String areaName = args.<String>getOne("areaName").get();
        Player player = (Player) src;
        AreaManager.getInstance().createArea(areaName, player);
        return CommandResult.success();
    }
}
