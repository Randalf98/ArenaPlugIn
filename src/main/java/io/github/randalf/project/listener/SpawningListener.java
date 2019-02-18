package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
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
        }
        try{
            spawner.getEntitiesList().remove(event.getTargetEntity());
        } catch(Exception ignored){}
    }
    /*
    On creation of Entity Presets this has to be set to the entity.
     */
    @Listener
    public void onFire(DamageEntityEvent event){
        Entity e = event.getTargetEntity();
        if(spawner.getEntitiesList().contains(e) && ((DamageSource)event.getCause().root()).getType() == DamageTypes.FIRE){
            event.setCancelled(true);
            e.offer(Keys.IS_AFLAME, false);
        }
    }
}
