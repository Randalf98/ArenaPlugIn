package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import io.github.randalf.project.arenaparts.ArenaController;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FloodMode implements SpawnMode {

    private ArenaController controller;
    private Entity entity;
    private List<Entity> entitiesList;

    public FloodMode(ArenaController controller){
        this.controller = controller;
        entitiesList = new ArrayList<>();
        Optional<World> optionalWorld = Sponge.getServer().getWorld(controller.getArea().getWorldUUID());
        Optional<Vector3d> optionalLocation = controller.getArea().getSpawnLocations().stream().findFirst();
        if (optionalWorld.isPresent() && optionalLocation.isPresent()){
            World world = optionalWorld.get();
            entity = world
                    .createEntity(EntityTypes.ZOMBIE, optionalLocation.get());
            entity.offer(Keys.HEIGHT, 50f);
            entity.offer(Keys.GLOWING, true);
            entity.offer(Keys.HEALTH, 100d);
            entity.offer(Keys.DISPLAY_NAME, (Text.of("Zomboid")));
        }
    }

    @Override
    public List<Entity> getNextEntities(Vector3d location) {
        Optional<World> optionalWorld = Sponge.getServer().getWorld(controller.getArea().getWorldUUID());
        List<Entity> entitiesToBeSpawned = new ArrayList<>();
        if(optionalWorld.isPresent()){
            cleanEntitiesList();
            entity.setLocation(optionalWorld.get().getLocation(location));
            while(entitiesList.size()<10) {
                Entity entityToBeSpawned = ((Entity) entity.copy());
                entitiesToBeSpawned.add(entityToBeSpawned);
                entitiesList.add(entityToBeSpawned);
            }
        }
        return entitiesToBeSpawned;
    }

    private void cleanEntitiesList() {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity e : entitiesList){
            if(((Living)e).getHealthData().health().get().equals(0.0d)){
                entitiesToRemove.add(e);
            }
        }
        entitiesList.removeAll(entitiesToRemove);
    }
}
