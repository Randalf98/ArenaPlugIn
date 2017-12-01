package io.github.randalf.project.arenaparts;

import com.flowpowered.math.vector.Vector3d;
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

import java.util.Optional;

public class ArenaSpawner {

    private ArenaController controller;
    private Area area;
    private ArenaMode mode;
    private ArenaListener listener;

    private boolean shouldSpawn = true;

    ArenaSpawner(ArenaController Controller, Area area, ArenaMode mode){
        this.controller = controller;
        this.area = area;
        this.mode = mode;
        this.listener = new SpawningListener(this, area);
        Sponge.getEventManager().registerListeners(ArenaPlugIn.getInstance(), listener);
    }

    public void spawnEnemys(){
        if(shouldSpawn){
            spawnEnemy(EntityTypes.CHICKEN);
        }
    }

    private void spawnEnemy(EntityType entity){
        Optional<World> optionalWorld = Sponge.getServer().getWorld(area.getWorldUUID());
        Optional<Vector3d> optionalLocation = area.getSpawnLocations().stream().findFirst();
        if (optionalWorld.isPresent() && optionalLocation.isPresent()){
            World world = optionalWorld.get();
            Entity entityToSpawn = world
                    .createEntity(entity, optionalLocation.get());
            world.spawnEntity(entityToSpawn);
        }
    }

    void stop(){
        shouldSpawn = false;
    }

    ArenaListener getListener(){
        return listener;
    }
}
