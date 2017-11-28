package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.manager.AreaManager;
import io.github.randalf.project.manager.ArenaManager;
import org.spongepowered.api.Sponge;

import javax.inject.Inject;

public class ArenaController {

    private String name;
    private Area area;
    private ArenaMode mode;
    private ArenaSecurity security;
    private ArenaSpawner spawner;

    @Inject
    AreaManager areaManager;

    public ArenaController(String areaString, String modeString, String securityString) {

        area = areaManager.getArea(areaString);
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
        Sponge.getEventManager().unregisterListeners(spawner.getListener());
    }

    public void disableSpawning() {
        spawner.stop();
    }

    public void startArena() {
        spawner.spawnEnemys();
    }
}
