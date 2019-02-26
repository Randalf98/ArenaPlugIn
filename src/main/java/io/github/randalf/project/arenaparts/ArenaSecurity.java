package io.github.randalf.project.arenaparts;

import io.github.randalf.project.arenaparts.spawner.SpawnMode;
import io.github.randalf.project.manager.ArenaManager;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.AreaManipulationListener;
import javax.inject.Singleton;

/**
 * Singleton containing security restrictments
 */
@Singleton
public class ArenaSecurity {

    public static final String DEFAULT = "default";
    private Arena arena;
    private Area area;
    private SpawnMode mode;
    private ArenaListener listener;

    /**
     * Constructor for basic functionality
     */
    public ArenaSecurity(Arena arena, Area area, SpawnMode mode, String securityString) {
        this.arena = arena;
        this.area = area;
        this.mode = mode;
        if(securityString.equals(DEFAULT)){
            listener = new AreaManipulationListener(area.getAreaChunks());
        } else {
            ArenaManager.getInstance().stopArena("Securitystring wasn't in specified order. It was: " + securityString, this.arena);
        }
    }


    /**
     * Getter for the listener
     * @return the securityListener
     */
    public ArenaListener getListener() {
        return listener;
    }
}