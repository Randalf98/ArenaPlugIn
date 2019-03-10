package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;

/**
 * Listener used to detect and prevent arena entities from receiving burning damage
 */
public class PreventBurningListener extends ArenaListener {

    /**
     * Constructor for achieving the functionality of the listener
     * @param arena arena object
     */
    public PreventBurningListener(Arena arena) {
        super(arena);
    }

    /**
     * Listener to react when an event of an entity spawning occurs
     * @param event the DamageEntityEvent containing all necessary information to evaluate if the entity receives burning damage
     */
    @Listener
    public void onFire(DamageEntityEvent event){
        Entity e = event.getTargetEntity();
        if(arena.getSpawner().getEntitiesList().contains(e) && ((DamageSource)event.getCause().root()).getType() == DamageTypes.FIRE){
            event.setCancelled(true);
            e.offer(Keys.IS_AFLAME, false);
        }
    }
}
