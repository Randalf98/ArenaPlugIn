package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

public class PreventXPDroppingListener extends ArenaListener {

    private ArenaSpawner spawner;

    public PreventXPDroppingListener(ArenaSpawner spawner, Area area) {
        super();
        this.spawner = spawner;
    }

    /*
    On creation of Entity Presets this has to be set to the entity.
     */
    @Listener
    public void onXPDrop(SpawnEntityEvent event) {
        if (event.getSource() instanceof Entity){
            Entity entity = (Entity)event.getSource();
            if (spawner.checkEntity(entity)){
                if (spawner.isLastDiedEntity(entity)){
                    if (((Entity) event.getEntities().get(0)).getType() == EntityTypes.EXPERIENCE_ORB) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}