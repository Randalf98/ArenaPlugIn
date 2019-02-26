package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.DropItemEvent;

/**
 * Listener used to detect and prevent items from dropping in the arena
 */
public class PreventDroppingListener extends ArenaListener {

    private ArenaSpawner spawner;
    private Area area;

    /**
     * Constructor for achieving the functionality of the listener
     * @param spawner spawner object of the arena
     * @param area arena object of the arena
     */
    public PreventDroppingListener(ArenaSpawner spawner, Area area) {
        super();
        this.spawner = spawner;
        this.area = area;
    }

    /**
     * Listener to react when an event of an item dropping occurs
     * @param event the DropItemEvent containing all necessary information to evaluate if the object gets dropped by a entity which is part of the arena
     */
    @Listener
    public void onItemDrop(DropItemEvent event){
        event.setCancelled(true);
    }
}
