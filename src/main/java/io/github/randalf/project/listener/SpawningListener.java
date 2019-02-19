package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;

public class SpawningListener extends ArenaListener {

    private ArenaSpawner spawner;
    private Area area;

    public SpawningListener(ArenaSpawner spawner, Area area) {
        super();
        this.spawner = spawner;
        this.area = area;
    }

    @Listener
    public void onDeadEntity(DestructEntityEvent event) {
        Entity  e = event.getTargetEntity();
        if(spawner.getEntitiesList().contains(e) && event.getCause().first(Player.class).isPresent()){
            Player player = event.getCause().first(Player.class).get();
            Vector3i playerPosition = player.getLocation().getChunkPosition();
            if (area.contains(playerPosition)){
                spawner.spawnEnemys();
            }
            spawner.setLastDiedEntity(e);
        }
        try{
            spawner.getEntitiesList().remove(e);
        } catch(Exception ignored){}
    }
}
