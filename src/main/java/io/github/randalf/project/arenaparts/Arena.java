package io.github.randalf.project.arenaparts;

import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import io.github.randalf.project.arenaparts.spawner.FloodMode;
import io.github.randalf.project.arenaparts.spawner.SpawnMode;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.manager.ZoneManager;
import org.spongepowered.api.entity.EntityType;
import java.util.EnumMap;
import java.util.Set;

/**
 * Data object for a arena
 * Holding all information necessary for an instance
 */
public class Arena {
        private String arenaName;
        private Zone zone;
        private SpawnMode mode;
        private ArenaSpawner spawner;
        private ZoneManager zoneManager;
        private ArenaListenerManager arenaListenerManager;
        private boolean active;

    /**
     * Basic Constructor for Arena
     * @param arenaName name of the arena
     * @param zoneString name of the zone
     */
    public Arena(String arenaName, String zoneString) {
        this.arenaName = arenaName;
        zoneManager = ZoneManager.getInstance();
        zone = zoneManager.getZone(zoneString);
        mode = new FloodMode(this);
        spawner = new ArenaSpawner(this, mode);
        EnumMap<ArenaOptions, ArenaListener> arenaListeners = new EnumMap<>(ArenaOptions.class);
        arenaListenerManager = new ArenaListenerManager(this, arenaListeners);
    }

    /**
     * Constructor for arena with options
     * @param arenaName name of the arena
     * @param zoneString name of the zone
     * @param options List of active options for the arena
     */
    public Arena(String arenaName, String zoneString, Set<ArenaOptions> options) {
        this.arenaName = arenaName;
        zoneManager = ZoneManager.getInstance();
        zone = zoneManager.getZone(zoneString);
        mode = new FloodMode(this);
        spawner = new ArenaSpawner(this, mode);
        EnumMap<ArenaOptions, ArenaListener> arenaListeners = new EnumMap<>(ArenaOptions.class);
        arenaListenerManager = new ArenaListenerManager(this, arenaListeners);
        for(ArenaOptions option: options)
            arenaListenerManager.setOption(option, true);
    }

    /**
     * Constructor for arena with mode
     * @param arenaName name of the arena
     * @param zoneString name of the zone
     * @param modus flood or round mode
     * @param et type of entity
     * @param enemyAmount amount of enemys in the arena
     */
    public Arena(String arenaName, String zoneString,String modus, EntityType et, int enemyAmount) {
        this.arenaName = arenaName;
        zoneManager = ZoneManager.getInstance();
        zone = zoneManager.getZone(zoneString);
        if(modus.equals("FloodMode")){
            mode = new FloodMode(this, et , enemyAmount);
        } else {
            mode = new FloodMode(this);
        }
        spawner = new ArenaSpawner(this, mode);
    }

    /**
     * Constructor for arena with mode and options
     * @param arenaName name of the arena
     * @param zoneString name of the zone
     * @param modus flood or round mode
     * @param et type of entity
     * @param enemyAmount amount of enemys in the arena
     * @param options List of active options for the arena
     */
    public Arena(String arenaName, String zoneString,String modus, EntityType et, int enemyAmount, Set<ArenaOptions> options) {
        this.arenaName = arenaName;
        zoneManager = ZoneManager.getInstance();
        zone = zoneManager.getZone(zoneString);
        if(modus.equals("FloodMode")){
            mode = new FloodMode(this, et , enemyAmount);
        } else {
            mode = new FloodMode(this);
        }
        spawner = new ArenaSpawner(this, mode);
        EnumMap<ArenaOptions, ArenaListener> arenaListeners = new EnumMap<>(ArenaOptions.class);
        arenaListenerManager = new ArenaListenerManager(this, arenaListeners);
        for(ArenaOptions option: options)
            arenaListenerManager.setOption(option, true);
    }

    /**
     * Getter for the name of the arena
     * @return name of the arena
     */
    public String getName() {
        return this.arenaName;
    }

    /**
     * Forwards the command to the spawner to stop
     */
    private void disableSpawning() {
        spawner.stop();
    }

    /**
     * Starts the arena object
     */
    public void startArena() {
        active = true;
        arenaListenerManager.addListener();
        spawner.start();
    }

    /**
     * Stops the arena object
     */
    public void stopArena(){
        active = false;
        arenaListenerManager.removeListener();
        disableSpawning();
        spawner.killAll();
    }

    /**
     * Getter for the zone of the arena
     * @return the zone of the arena
     */
    public Zone getZone() {
        return zone;
    }

    /**
     * Setter for the zone of the arena
     * @param zone new arena for the zone
     */
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    /**
     * Getter for the arenaName
     * @return the arenaName
     */
    public String getArenaName() {
        return arenaName;
    }

    /**
     * Setter for the arenaName
     * @param name new arenaName
     */
    public void setArenaName(String name) {
        this.arenaName = name;
    }

    /**
     * Getter for the mode of the arena
     * @return SpawnMode of the arena
     */
    public SpawnMode getMode() {
        return mode;
    }

    /**
     * Getter for the spawner of the arena
     * @return Spawner of the arena
     */
    public ArenaSpawner getSpawner() {
        return spawner;
    }

    /**
     * Setter for the spawner of the arena
     * @param spawner new ArenaSpawner for the arena
     */
    public void setSpawner(ArenaSpawner spawner) {
        this.spawner = spawner;
    }

    public boolean isActive(){
        return active;
    }

    public ArenaListenerManager getALM() {return arenaListenerManager;}
}

