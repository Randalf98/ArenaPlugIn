package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;

/**
 * Listener used to detect the death of entities and let new entitys spawn
 */
public class SpawningListener extends ArenaListener {

    /**
     * Constructor for achieving the functionality of the listener
     * @param arena arena object
     */
    public SpawningListener(Arena arena) {
        super(arena);
    }

    /**
     * Listener to react when an entity gets a destruction event
     * @param event the DestructEntityEvent containing all necessary information to evaluate the death of an entity
     */
    @Listener
    public void onDeadEntity(DestructEntityEvent event) {
        Entity  e = event.getTargetEntity();
        if(arena.getSpawner().getEntitiesList().contains(e)){
            arena.getSpawner().spawnEnemys();
            arena.getSpawner().setLastDiedEntity(e);
        }
        try{
            arena.getSpawner().getEntitiesList().remove(e);
        } catch(Exception ex){System.out.println(ex.getMessage());}
    }
}
