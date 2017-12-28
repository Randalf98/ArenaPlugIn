package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.ArenaController;
import io.github.randalf.project.arenaparts.ArenaMode;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.SpawningListener;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import java.util.*;

public class ArenaSpawner {

    private ArenaController controller;
    private ArenaMode mode;
    private ArenaListener listener;
    private boolean shouldSpawn = true;
    private List<Entity> entitiesList;

    public ArenaSpawner(ArenaController controller, Area area, ArenaMode mode){
        this.controller = controller;
        this.mode = mode;
        this.listener = new SpawningListener(this, area);
        Sponge.getEventManager().registerListeners(ArenaPlugIn.getInstance(), listener);
        entitiesList = new ArrayList<>();
    }

    public void spawnEnemys(){
        if(shouldSpawn){
            ArrayList<Vector3d> spawnLocations = new ArrayList<>();
            spawnLocations.addAll(controller.getArea().getSpawnLocations());
            Vector3d Location = getBestSpawnLocation(spawnLocations, controller.getArea().getPlayerInArea());
            cleanEntitiesList();
            while(entitiesList.size()<10){
                spawnEnemy(Location, EntityTypes.SNOWMAN);
            }
        }
    }

    private void spawnEnemy(Vector3d location, EntityType entity) {
        Optional<World> optionalWorld = Sponge.getServer().getWorld(controller.getArea().getWorldUUID());
        if (optionalWorld.isPresent()){
            World world = optionalWorld.get();
            Entity entityToSpawn = world
                    .createEntity(entity, location);
            entityToSpawn.offer(Keys.HEIGHT, 50f);
            entityToSpawn.offer(Keys.GLOWING, true);
            entityToSpawn.offer(Keys.HEALTH, 0.5);
            world.spawnEntity(entityToSpawn);
            entitiesList.add(entityToSpawn);
        }
    }

    private void spawnEnemy(EntityType entity){
        Optional<World> optionalWorld = Sponge.getServer().getWorld(controller.getArea().getWorldUUID());
        Optional<Vector3d> optionalLocation = controller.getArea().getSpawnLocations().stream().findFirst();
        if (optionalWorld.isPresent() && optionalLocation.isPresent()){
            World world = optionalWorld.get();
            Entity entityToSpawn = world
                    .createEntity(entity, optionalLocation.get());
            entityToSpawn.offer(Keys.HEIGHT, 50f);
            entityToSpawn.offer(Keys.GLOWING, true);
            entityToSpawn.offer(Keys.HEALTH, 0.5);
            entityToSpawn.offer(Keys.DISPLAY_NAME, (Text.of("Little Chicken")));
            world.spawnEntity(entityToSpawn);
        }
    }

    private Vector3d getBestSpawnLocation(ArrayList<Vector3d> spawnLocations, ArrayList<Player> playerInArea) {
        Vector3d furthestLocation = null;
        Double furthestDistance = null;
        for(Vector3d v : spawnLocations){
            Double d = 0d;
            for (Player p : playerInArea){
                d += p.getLocation().getPosition().distance(v);
            }
            if (furthestDistance == null || d > furthestDistance) {
                furthestDistance = d;
                furthestLocation = v;
            }
        }
        return furthestLocation;
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

    public void stop(){
        shouldSpawn = false;
    }

    public ArenaListener getListener(){
        return listener;
    }
}
