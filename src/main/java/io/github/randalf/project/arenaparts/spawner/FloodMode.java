package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FloodMode implements SpawnMode {

    private Arena arena;
    private Entity entity;
    private EntityType entityType = EntityTypes.ZOMBIE;
    private List<Entity> toBeSpawnedEntitiesList;
    private int entityAmount = 10;

    public FloodMode(Arena arena){
        this.arena = arena;
        toBeSpawnedEntitiesList = new ArrayList<>();
        createEntity();
    }

    public FloodMode(Arena arena, EntityType et, int entityAmount){
        this.arena = arena;
        this.entityAmount = entityAmount;
        this.entityType = et;
        toBeSpawnedEntitiesList = new ArrayList<>();
        createEntity();
    }

    @Override
    public List<Entity> getNextEntities(Vector3d location) {
        Optional<World> optionalWorld = Sponge.getServer().getWorld(arena.getArea().getWorldUUID());
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

    private void cleanEntitiesList() {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity e : toBeSpawnedEntitiesList){
            if(((Living)e).getHealthData().health().get().equals(0.0d)){
                entitiesToRemove.add(e);
            }
        }
        toBeSpawnedEntitiesList.removeAll(entitiesToRemove);
    }

    public void createEntity(){
        Optional<World> optionalWorld = Sponge.getServer().getWorld(arena.getArea().getWorldUUID());
        Optional<Vector3d> optionalLocation = arena.getArea().getSpawnLocations().stream().findFirst();
        if (optionalWorld.isPresent() && optionalLocation.isPresent()){
            World world = optionalWorld.get();
            entity = world.createEntity(entityType, optionalLocation.get());
        }
    }

    public void setEntityType(String entityType){
        this.entityType = Entitys.getEntity(entityType);
        createEntity();
    }

    public EntityType getEntityType(){
        return this.entityType;
    }

    public void setEntityAmount(int entityAmount){
        this.entityAmount = entityAmount;
    }

    public int getEntityAmount(){
        return this.entityAmount;
    }
}
