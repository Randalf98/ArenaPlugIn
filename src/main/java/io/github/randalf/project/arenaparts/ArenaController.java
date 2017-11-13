package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.listener.ArenaListener;
import org.spongepowered.api.Sponge;

public class ArenaController {

    private String name;
    private ArenaArea area;
    private ArenaMode mode;
    private ArenaSecurity security;
    private ArenaSpawner spawner;

    public ArenaController(String areaString, String modeString, String securityString){
        area = new ArenaArea(this, areaString);
        mode = new ArenaMode(this, modeString);
        security = new ArenaSecurity(this, area, mode ,securityString);
        spawner = new ArenaSpawner(this, area, mode);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
       return this.name;
    }

    public void removeListener() {
        Sponge.getEventManager().unregisterListeners(security.getListener());
    }
}
