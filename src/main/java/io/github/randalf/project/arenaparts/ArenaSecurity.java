package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import javax.inject.Singleton;

@Singleton
public class ArenaSecurity {

    public static final String DEFAULT = "default";
    private ArenaController controller;
    private ArenaArea area;
    private ArenaMode mode;

    public ArenaSecurity(ArenaController controller,ArenaArea area, ArenaMode mode, String securityString) {
        this.controller = controller;
        this.area = area;
        this.mode = mode;
        if(securityString == DEFAULT){

        } else {
            ArenaManager.getInstance().removeArena("Securitystring wasn't in specified order. It was: " + securityString, controller);
        }
    }

    @Listener
    public void onBlockBreaking(ChangeBlockEvent.Break event) {
        MessageChannel.TO_ALL.send(Text.of(event.getCause().toString()));
        event.setCancelled(true);

    }

}
