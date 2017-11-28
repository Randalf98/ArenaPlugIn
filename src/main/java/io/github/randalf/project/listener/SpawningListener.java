package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.ArenaSpawner;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.Chunk;

import java.util.Collection;

public class SpawningListener extends ArenaListener {

    private ArenaSpawner spawner;
    private Collection<Chunk> areaChunks;

    public SpawningListener(ArenaSpawner spawner, Collection<Chunk> areaChunks) {
        super();
        this.spawner = spawner;
        this.areaChunks = areaChunks;
    }

    @Listener
    public void onDeadEntity(DestructEntityEvent event) {
        if(event.getCause().first(Player.class).isPresent()){
            Player player = event.getCause().first(Player.class).get();
            Vector3i playerPosition = player.getLocation().getChunkPosition();
            for (Chunk chunk: areaChunks){
                if (chunk.containsBlock(playerPosition)){
                    MessageChannel.TO_ALL.send(Text.of(event.getCause().toString()));
                    spawner.spawnEnemys();
                }
            }
        }
    }
}
