package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.Chunk;

import java.util.Collection;

public class DefaultListener extends ArenaListener {

    private Collection<Vector3i> areaChunks;

    public DefaultListener(Collection<Vector3i> areaChunks) {
        super();
        this.areaChunks =areaChunks;
    }

    @Listener
    public void onBlockBreaking(ChangeBlockEvent.Break event) {
        if(event.getCause().first(Player.class).isPresent()){
            Player player = event.getCause().first(Player.class).get();
            Vector3i playerPosition = player.getLocation().getChunkPosition();
            for (Vector3i chunk: areaChunks){
                if (chunk.equals(playerPosition)){
                    MessageChannel.TO_ALL.send(Text.of(event.getCause().toString()));
                    event.setCancelled(true);
                }
            }
        }
    }

    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event) {
        if (event.getCause().first(Player.class).isPresent()) {
            Player player = event.getCause().first(Player.class).get();
            Vector3i playerPosition = player.getLocation().getChunkPosition();
            for (Vector3i chunk: areaChunks){
                if (chunk.equals(playerPosition)){
                    MessageChannel.TO_ALL.send(Text.of(event.getCause().toString()));
                    event.setCancelled(true);
                }
            }
        }
    }

}
