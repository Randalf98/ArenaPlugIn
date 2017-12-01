package io.github.randalf.project.arenaparts;

import com.flowpowered.math.vector.Vector3d;
import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.SpawningListener;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.text.Text;
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
            entityToSpawn.offer(Keys.HEIGHT, 50f);
            entityToSpawn.offer(Keys.GLOWING, true);
            entityToSpawn.offer(Keys.HEALTH, 0.5);
            entityToSpawn.offer(Keys.DISPLAY_NAME, (Text.of("Little Chicken")));
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
