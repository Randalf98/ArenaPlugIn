package io.github.randalf.project.arenaparts;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collection;

public class ArenaSpawner {

    private ArenaController controller;
    private ArenaArea area;
    private ArenaMode mode;

    private boolean shouldSpawn = true;

    public ArenaSpawner(ArenaController Controller, ArenaArea area, ArenaMode mode) {
        this.controller = controller;
        this.area = area;
        this.mode = mode;
    }

    public void spawnEnemys(){
        if(shouldSpawn){
            Collection<Player> players = Sponge.getGame().getServer().getOnlinePlayers();
            Player p = (Player) players.toArray()[0];
            spawnEnemy(p.getLocation(), EntityTypes.CHICKEN);
        }
    }
    private void spawnEnemy(Location<World> spawnLocation,EntityType entity){
        World world = spawnLocation.getExtent();
        Entity entityToSpawn = world
                .createEntity(entity, spawnLocation.getPosition());
        SpawnCause spawnCause = SpawnCause.builder().type(SpawnTypes.PLUGIN).build();
        world.spawnEntity(entityToSpawn, Cause.source(spawnCause).build());
    }

    public void stop(){
        shouldSpawn = false;
    }
}
