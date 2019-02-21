package io.github.randalf.project.arenaparts;

import io.github.randalf.project.arenaparts.spawner.SpawnMode;
import io.github.randalf.project.manager.ArenaManager;
import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.AreaManipulationListener;
import org.spongepowered.api.Sponge;

import javax.inject.Singleton;

@Singleton
public class ArenaSecurity {

    public static final String DEFAULT = "default";
    private Arena arena;
    private Area area;
    private SpawnMode mode;
    private ArenaListener listener;


    public ArenaSecurity(Arena arena, Area area, SpawnMode mode, String securityString) {
        this.arena = arena;
        this.area = area;
        this.mode = mode;
        if(securityString.equals(DEFAULT)){
            if(securityString.equals(DEFAULT)){
                listener = new AreaManipulationListener(area.getAreaChunks());
            }
        } else {
            ArenaManager.getInstance().stopArena("Securitystring wasn't in specified order. It was: " + securityString, this.arena);
        }
    }

    public ArenaListener getListener() {
        return listener;
    }
}
