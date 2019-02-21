package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.DropItemEvent;

public class PreventDroppingListener extends ArenaListener {

    private ArenaSpawner spawner;
    private Area area;

    public PreventDroppingListener(ArenaSpawner spawner, Area area) {
        super();
        this.spawner = spawner;
        this.area = area;
    }
    /*
    On creation of Entity Presets this has to be set to the entity.
     */
    @Listener
    public void onItemDrop(DropItemEvent event){
        event.setCancelled(true);
    }
}
