package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaManager;
import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.DefaultListener;
import org.spongepowered.api.Sponge;
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
    private ArenaListener listener;


    public ArenaSecurity(ArenaController controller,ArenaArea area, ArenaMode mode, String securityString) {
        this.controller = controller;
        this.area = area;
        this.mode = mode;
        if(securityString.equals(DEFAULT)){
            if(securityString.equals(DEFAULT)){
                listener = new DefaultListener();
                Sponge.getEventManager().registerListeners(ArenaPlugIn.getInstance(), listener);
            }
        } else {
            ArenaManager.getInstance().removeArena("Securitystring wasn't in specified order. It was: " + securityString, controller);
        }
    }

    public ArenaListener getListener() {
        return listener;
    }
}
