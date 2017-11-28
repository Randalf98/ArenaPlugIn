package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.DefaultListener;
import io.github.randalf.project.listener.SpawningListener;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collection;

public class ArenaSpawner {

    private ArenaController controller;
    private Area area;
    private ArenaMode mode;
    private ArenaListener listener;

    private boolean shouldSpawn = true;

    public ArenaSpawner(ArenaController Controller, Area area, ArenaMode mode) {
        this.controller = controller;
        this.area = area;
        this.mode = mode;
        this.listener = new SpawningListener(this, area.getAreaChunks());
        Sponge.getEventManager().registerListeners(ArenaPlugIn.getInstance(), listener);
    }

    public void spawnEnemys(){
        if(shouldSpawn){
            spawnEnemy((Location<World>)area.getSpawnLocations().toArray()[0], EntityTypes.CHICKEN);
        }
    }
    private void spawnEnemy(Location<World> spawnLocation,EntityType entity){
        World world = spawnLocation.getExtent();
        Entity entityToSpawn = world
                .createEntity(entity, spawnLocation.getPosition());
        world.spawnEntity(entityToSpawn);
    }

    public void stop(){
        shouldSpawn = false;
    }

    public ArenaListener getListener(){
        return listener;
    }
}
