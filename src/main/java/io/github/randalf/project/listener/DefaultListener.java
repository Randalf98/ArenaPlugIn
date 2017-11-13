package io.github.randalf.project.listener;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

public class DefaultListener extends ArenaListener {

    @Listener
    public void onBlockBreaking(ChangeBlockEvent.Break event) {
        MessageChannel.TO_ALL.send(Text.of(event.getCause().toString()));
        event.setCancelled(true);
    }

    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event) {
        MessageChannel.TO_ALL.send(Text.of(event.getCause().toString()));
        event.setCancelled(true);
    }
}
