package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.SpawningListener;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import java.util.*;

public class ArenaSpawner {

    private Arena arena;
    private ArenaListener listener;
    private boolean shouldSpawn = true;
    private SpawnMode mode;

    public ArenaSpawner(Arena arena, Area area, SpawnMode mode){
        this.arena = arena;
        this.listener = new SpawningListener(this, area);
        this.mode = mode;
        Sponge.getEventManager().registerListeners(ArenaPlugIn.getInstance(), listener);
    }

    public void spawnEnemys(){
        if(shouldSpawn){
            ArrayList<Vector3d> spawnLocations = new ArrayList<>();
            spawnLocations.addAll(arena.getArea().getSpawnLocations());
            Vector3d location = getBestSpawnLocation(spawnLocations, arena.getArea().getPlayerInArea());
            for(Entity e: mode.getNextEntities(location)){
                Optional<World> optionalWorld = Sponge.getServer().getWorld(arena.getArea().getWorldUUID());
                if (optionalWorld.isPresent()){
                    World world = optionalWorld.get();
                    world.spawnEntity(e);
                }
            }
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

    public void stop(){
        shouldSpawn = false;
    }

    public ArenaListener getListener(){
        return listener;
    }
}
