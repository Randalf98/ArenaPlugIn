package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

/**
 * Listener used to detect the spawning of entities and denying to spawn entities which are part of an arena
 */
public class PreventXPDroppingListener extends ArenaListener {

    /**
     * Constructor for achieving the functionality of the listener
     * @param arena arena object
     */
    public PreventXPDroppingListener(Arena arena) {
        super(arena);
    }

    /**
     * Listener to react when an event of an entity spawning occurs
     * @param event the SpawnEntityEvent containing all necessary information to evaluate if the to be spawned entity is from type EXP_ORB
     */
    @Listener
    public void onXPDrop(SpawnEntityEvent event) {
        if (event.getSource() instanceof Entity){
            Entity entity = (Entity)event.getSource();
            if (arena.getSpawner().isLastDiedEntity(entity) &&  !event.getEntities().isEmpty()){
                if (((Entity) event.getEntities().get(0)).getType() == EntityTypes.EXPERIENCE_ORB) {
                    event.setCancelled(true);
                }
            }
        }
    }
}