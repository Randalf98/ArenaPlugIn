package io.github.randalf.project.commands;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * CommandExecutor for getting the information of an arena
 */
public class AreaInformationCommand implements CommandExecutor {

    /**
     * Lists information of a given arena
     * @see CommandExecutor#execute(CommandSource, CommandContext);
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String areaName = "";
        if (args.<String>getOne("areaName").isPresent()) {
            areaName = args.<String>getOne("areaName").get();
        }
        if(AreaManager.getInstance().mapContains(areaName)){
            Area area = AreaManager.getInstance().getArea(areaName);
            String areaInfo = "Arena " + areaName + " contains:\n";
            areaInfo += "World UUID: " + area.getWorldUUID() + "\n";
            areaInfo += "Area Chunks:\n";
            for(Vector3i chunk : area.getAreaChunks()) {
                areaInfo += chunk.toString() + "\n";
            }
            areaInfo += "Area Spawn Locations:\n";
            for(Vector3d spawnlocations : area.getSpawnLocations()) {
                areaInfo += spawnlocations.toString() + "\n";
            }
            areaInfo += "Player in Area:\n";
            for(Player player : area.getPlayerInArea()) {
                areaInfo += player.getName() + "\n";
            }
            areaInfo += "------------------------------";
            src.sendMessage(Text.of(areaInfo));
        } else {
            src.sendMessage(Text.of("Area doesn't exists"));
        }
        return CommandResult.success();
    }
}
