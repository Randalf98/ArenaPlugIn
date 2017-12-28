package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.World;
import java.util.Optional;

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
        if(event.getCause().first(Player.class).isPresent()){
            Player player = event.getCause().first(Player.class).get();
            Vector3i playerPosition = player.getLocation().getChunkPosition();
            for (Vector3i chunk: area.getAreaChunks()){
                Optional<World> optionalWorld = Sponge.getServer().getWorld(area.getWorldUUID());
                World world;
                if (optionalWorld.isPresent()){
                    world = optionalWorld.get();
                    if(world.getChunk(chunk).get().getPosition().equals(playerPosition)){
                        MessageChannel.TO_ALL.send(Text.of(event.getCause().toString()));
                        spawner.spawnEnemys();
                    }
                }
            }
        }
    }
}
