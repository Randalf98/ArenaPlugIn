package io.github.randalf.project.listener;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.filter.type.Include;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Collection;

public class AreaManipulationListener extends ArenaListener {

    private Collection<Vector3i> areaChunks;

    public AreaManipulationListener(Collection<Vector3i> areaChunks) {
        super();
        this.areaChunks =areaChunks;
    }

    @Listener
    @Include({
            ChangeBlockEvent.Break.class,
            ChangeBlockEvent.Place.class
    })
    public void onBlockBreakingAndPlacing(ChangeBlockEvent event, @Root Player player) {
        Vector3i playerPosition = player.getLocation().getChunkPosition();
        for (Vector3i chunk: areaChunks){
            if (chunk.equals(playerPosition)){
                MessageChannel.TO_ALL.send(Text.of(event.getCause().toString()));
                event.setCancelled(true);
            }
        }
    }

}
