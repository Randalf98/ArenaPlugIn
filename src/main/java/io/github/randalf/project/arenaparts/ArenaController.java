package io.github.randalf.project.arenaparts;

import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.Sponge;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class ArenaController {

    private String name;
    private Area area;
    private ArenaMode mode;
    private ArenaSecurity security;
    private ArenaSpawner spawner;
    private AreaManager areaManager;

    public ArenaController(String areaString, String modeString, String securityString) {
        name = areaString;
        areaManager = AreaManager.getInstance();
        area = areaManager.getArea(name);
        mode = new ArenaMode(this, modeString);
        security = new ArenaSecurity(this, area, mode ,securityString);
        spawner = new ArenaSpawner(this, area, mode);
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArenaMode getMode() {
        return mode;
    }

    public void setMode(ArenaMode mode) {
        this.mode = mode;
    }

    public ArenaSecurity getSecurity() {
        return security;
    }

    public void setSecurity(ArenaSecurity security) {
        this.security = security;
    }

    public ArenaSpawner getSpawner() {
        return spawner;
    }

    public void setSpawner(ArenaSpawner spawner) {
        this.spawner = spawner;
    }

    public AreaManager getAreaManager() {
        return areaManager;
    }

    public void setAreaManager(AreaManager areaManager) {
        this.areaManager = areaManager;
    }
}
