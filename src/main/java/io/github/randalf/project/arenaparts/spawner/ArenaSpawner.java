package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.listener.*;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;
import java.util.*;

/**
 * Spawner containing all necessary values for the handling of dead entities and spawning new ones
 */
public class ArenaSpawner {

    private Arena arena;
    private ArenaListener listener;
    private boolean shouldSpawn = true;
    private SpawnMode mode;
    private List<Entity> entitiesList;
    private UUID[] lastDiedEntities;
    private int counter;

    /**
     * Basic constructor for the spawner
     * @param arena the arena which contains this spawner
     * @param mode the mode from the arena
     */
    public ArenaSpawner(Arena arena, SpawnMode mode){
        this.arena = arena;
        this.listener = new SpawningListener(arena);
        this.mode = mode;
        entitiesList = new ArrayList<>();
        lastDiedEntities = new UUID[10];
        counter = 0;
    }

    /**
     * Gathering all new entities and spawning them
     */
    public void spawnEnemys(){
        if(shouldSpawn){
            ArrayList<Vector3d> spawnLocations = new ArrayList<>(arena.getZone().getSpawnLocations());
            Vector3d location = getBestSpawnLocation(spawnLocations, arena.getZone().getPlayerInZone());
            for(Entity e: mode.getNextEntities(location)){
                Optional<World> optionalWorld = Sponge.getServer().getWorld(arena.getZone().getWorldUUID());
                if (optionalWorld.isPresent()){
                    World world = optionalWorld.get();
                    entitiesList.add(e);
                    world.spawnEntity(e);
                }
            }
        }
    }

    /**
     * Getter for the most appropriate location to spawn the enemies
     * @param spawnLocations list of all possible spawnlocations
     * @param playerInZone list of all player in the zone
     * @return the most appropiate location
     */
    private Vector3d getBestSpawnLocation(ArrayList<Vector3d> spawnLocations, ArrayList<Player> playerInZone) {
        Vector3d furthestLocation = spawnLocations.get(0);
        double meanDistance = 0;
        for(Vector3d v : spawnLocations){
            double meanDistanceForLocation = 0;
            for (Player p : playerInZone){
                meanDistanceForLocation += p.getLocation().getPosition().distanceSquared(v);
            }
            meanDistanceForLocation = meanDistanceForLocation/playerInZone.size();
            if (meanDistanceForLocation > meanDistance){
                meanDistance = meanDistanceForLocation;
                furthestLocation=v;
            }
        }
        return furthestLocation;
    }

    /**
     * Setting the spawning on false
     */
    public void stop(){shouldSpawn = false;}

    /**
     * Starting spawning
     */
    public void start(){
        shouldSpawn = true;
        spawnEnemys();
    }

    /**
     * Setter for the hashcode for the last died entity
     * @param entity Entity
     */
    public void setLastDiedEntity(Entity entity){
        lastDiedEntities[counter] = entity.getUniqueId();
        counter++;
        if(counter == 10){counter = 0;}
    }

    /**
     * Check if entity is the same as the last entity which died
     * @param entity the entity which should get checked
     * @return boolean if the hashcode resembles
     */
    public boolean isLastDiedEntity(Entity entity){
        return ArrayUtils.contains(lastDiedEntities, entity.getUniqueId());
    }

    /**
     * Getter for the listener
     * @return ArenaListener of the spawner
     */
    public ArenaListener getListener(){return listener;}

    /**
     * Getter for the arena
     * @return Arena of the spawner
     */
    public Arena getArena(){return arena;}

    /**
     * Getter for the entity list
     * @return EntityList
     */
    public List<Entity> getEntitiesList(){return entitiesList;}

    public void killAll() {
        for (Entity entity:entitiesList){
            entity.remove();
        }
        entitiesList.clear();
    }
}
