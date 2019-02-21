package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
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
        if(spawner.getEntitiesList().contains(e) && (event.getCause().first(EntityDamageSource.class).isPresent() || event.getCause().first(IndirectEntityDamageSource.class).isPresent())){
            Player player = null;
            if(event.getCause().first(IndirectEntityDamageSource.class).isPresent()){
                player = (Player)event.getCause().first(IndirectEntityDamageSource.class).get().getIndirectSource();
            } else if (event.getCause().first(EntityDamageSource.class).get().getSource().getType().equals(EntityTypes.PLAYER)){
                player = (Player)event.getCause().first(EntityDamageSource.class).get().getSource();
            }
            if(player != null){
                Vector3i playerPosition = player.getLocation().getChunkPosition();
                if (area.contains(playerPosition)){
                    spawner.spawnEnemys();
                }
                spawner.setLastDiedEntity(e);
            }
        }
        try{
            spawner.getEntitiesList().remove(e);
        } catch(Exception ignored){}
    }
}
