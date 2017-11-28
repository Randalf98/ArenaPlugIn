package io.github.randalf.project.arenaparts;

import io.github.randalf.project.manager.ArenaManager;
import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.DefaultListener;
import org.spongepowered.api.Sponge;

import javax.inject.Singleton;

@Singleton
public class ArenaSecurity {

    public static final String DEFAULT = "default";
    private ArenaController controller;
    private Area area;
    private ArenaMode mode;
    private ArenaListener listener;


    public ArenaSecurity(ArenaController controller, Area area, ArenaMode mode, String securityString) {
        this.controller = controller;
        this.area = area;
        this.mode = mode;
        if(securityString.equals(DEFAULT)){
            if(securityString.equals(DEFAULT)){
                listener = new DefaultListener(area.getAreaChunks());
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
