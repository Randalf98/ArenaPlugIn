package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Area;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.type.Include;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Listener used to detect and prevent manipulation of the area
 */
public class AreaManipulationListener extends ArenaListener {

    private Area area;

    /**
     * Constructor for achieving the functionality of the listener
     * @param area spawner object of the arena
     */
    public AreaManipulationListener(Area area) {
        super();
        this.area = area;
    }

    /**
     * istener to react when an event of a ChangeBlockEvent occurs
     * @param event the SpawnEntityEvent containing all necessary information to evaluate if the block change should be ignored
     */
    @Listener
    @Include({
            ChangeBlockEvent.Break.class,
            ChangeBlockEvent.Place.class
    })
    public void onBlockBreakingAndPlacing(ChangeBlockEvent event) {
        for (Transaction<BlockSnapshot> transaction : event.getTransactions()){
            Location<World> location = transaction.getOriginal().getLocation().orElse(null);
            if (location != null) {
                if(area.contains(location.getChunkPosition())){
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }
}
