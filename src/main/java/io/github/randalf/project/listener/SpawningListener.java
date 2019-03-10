package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
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
        if(arena.getSpawner().getEntitiesList().contains(e) && (event.getCause().first(EntityDamageSource.class).isPresent() || event.getCause().first(IndirectEntityDamageSource.class).isPresent())){
            Player player = null;
            if(event.getCause().first(IndirectEntityDamageSource.class).isPresent()){
                player = (Player)event.getCause().first(IndirectEntityDamageSource.class).get().getIndirectSource();
            } else if (event.getCause().first(EntityDamageSource.class).get().getSource().getType().equals(EntityTypes.PLAYER)){
                player = (Player)event.getCause().first(EntityDamageSource.class).get().getSource();
            }
            if(player != null){
                Vector3i playerPosition = player.getLocation().getChunkPosition();
                if (arena.getArea().contains(playerPosition)){
                    arena.getSpawner().spawnEnemys();
                }
                arena.getSpawner().setLastDiedEntity(e);
            }
        }
        try{
            arena.getSpawner().getEntitiesList().remove(e);
        } catch(Exception ignored){}
    }
}
