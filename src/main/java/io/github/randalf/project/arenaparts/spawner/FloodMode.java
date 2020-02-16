package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Mode for a flood arena
 * Will spawn always enemies until they reach a specific amount
 */
public class FloodMode implements SpawnMode {

    private Arena arena;
    private Entity entity;
    private EntityType entityType = EntityTypes.ZOMBIE;
    private List<Entity> toBeSpawnedEntitiesList;
    private int entityAmount = 10;

    /**
     * Basic Constructor
     * @param arena the arena which gets the mode assigned
     */
    public FloodMode(Arena arena){
        this.arena = arena;
        toBeSpawnedEntitiesList = new ArrayList<>();
    }

    /**
     * Constructor with specific values
     * @param arena the arena which gets the mode assigned
     * @param et the enemy type which will be flooded
     * @param entityAmount the maximum amount of enemies
     */
    public FloodMode(Arena arena, EntityType et, int entityAmount){
        this.arena = arena;
        this.entityAmount = entityAmount;
        this.entityType = et;
        toBeSpawnedEntitiesList = new ArrayList<>();
    }

    /**
     * Returns a list of entities which will be spawned
     * @param location spawnpoint location
     * @return list of enemies which will be spawned
     */
    @Override
    public List<Entity> getNextEntities(Vector3d location) {
        Optional<World> optionalWorld = Sponge.getServer().getWorld(arena.getZone().getWorldUUID());
        List<Entity> entitiesToBeSpawned = new ArrayList<>();
        if(optionalWorld.isPresent()){
            cleanEntitiesList();
            entity.setLocation(optionalWorld.get().getLocation(location));
            while(toBeSpawnedEntitiesList.size()<entityAmount) {
                Entity entityToBeSpawned = ((Entity) entity.copy());
                entitiesToBeSpawned.add(entityToBeSpawned);
                toBeSpawnedEntitiesList.add(entityToBeSpawned);
            }
        }
        return entitiesToBeSpawned;
    }

    /**
     * Removes all dead entities from the list
     */
    private void cleanEntitiesList() {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity e : toBeSpawnedEntitiesList){
            if(((Living)e).getHealthData().health().get().equals(0.0d)||!e.isLoaded()){
                entitiesToRemove.add(e);
            }
        }
        toBeSpawnedEntitiesList.removeAll(entitiesToRemove);
    }

    /**
     * Create the entity which will work as base for all spawning enemies
     */
    public void createEntity(){
        Optional<World> optionalWorld = Sponge.getServer().getWorld(arena.getZone().getWorldUUID());
        Optional<Vector3d> optionalLocation = arena.getZone().getSpawnLocations().stream().findFirst();
        if (optionalWorld.isPresent() && optionalLocation.isPresent()){
            World world = optionalWorld.get();
            entity = world.createEntity(entityType, optionalLocation.get());
        }
    }

    /**
     * Setter for the entityType and recreates base entity
     * @param entityType string of the entity which should be set as entitytype
     */
    public void setEntityType(String entityType){
        this.entityType = Entitys.getEntity(entityType);
        createEntity();
    }

    /**
     * Getter for the entityType
     * @return EntityType value of the arena
     */
    public EntityType getEntityType(){
        return this.entityType;
    }

    /**
     * Setter for the amount of entities
     * @param entityAmount int value of the maximum of entities
     */
    public void setEntityAmount(int entityAmount){
        this.entityAmount = entityAmount;
    }

    /**
     * Getter for the amount of entities
     * @return int representation of the maximum amount
     */
    public int getEntityAmount(){
        return this.entityAmount;
    }
}
