package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import io.github.randalf.project.arenaparts.Area;
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
    private int[] lastDiedEntities;
    private int counter;

    /**
     * Basic constructor for the spawner
     * @param arena the arena which contains this spawner
     * @param area the area from the area
     * @param mode the mode from the arena
     */
    public ArenaSpawner(Arena arena, Area area, SpawnMode mode){
        this.arena = arena;
        this.listener = new SpawningListener(this, area);
        this.mode = mode;
        entitiesList = new ArrayList<>();
        lastDiedEntities = new int[10];
        counter = 0;
    }

    /**
     * Gathering all new entities and spawning them
     */
    public void spawnEnemys(){
        if(shouldSpawn){
            ArrayList<Vector3d> spawnLocations = new ArrayList<>();
            spawnLocations.addAll(arena.getArea().getSpawnLocations());
            Vector3d location = getBestSpawnLocation(spawnLocations, arena.getArea().getPlayerInArea());
            for(Entity e: mode.getNextEntities(location)){
                Optional<World> optionalWorld = Sponge.getServer().getWorld(arena.getArea().getWorldUUID());
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
     * @param playerInArea list of all player in the area
     * @return the most appropiate location
     */
    private Vector3d getBestSpawnLocation(ArrayList<Vector3d> spawnLocations, ArrayList<Player> playerInArea) {
        Vector3d furthestLocation = spawnLocations.get(0);
        double meanDistance = 0;
        for(Vector3d v : spawnLocations){
            double meanDistanceForLocation = 0;
            for (Player p : playerInArea){
                meanDistanceForLocation += p.getLocation().getPosition().distanceSquared(v);
            }
            meanDistanceForLocation = meanDistanceForLocation/playerInArea.size();
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
        lastDiedEntities[counter] = entity.hashCode();
        counter++;
        if(counter == 10){counter = 0;}
    }

    /**
     * Check if entity is the same as the last entity which died
     * @param entity the entity which should get checked
     * @return boolean if the hashcode resembles
     */
    public boolean isLastDiedEntity(Entity entity){
        return ArrayUtils.contains(lastDiedEntities, entity.hashCode());
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

    /**
     * Checks if the given entity has the appropiate entity type
     * @param entity entity which needs to be checked
     * @return boolean value which checks the entity type
     */
    public boolean checkEntity(Entity entity){return entity.getType().equals(((FloodMode)mode).getEntityType());}
}
