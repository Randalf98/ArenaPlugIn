package io.github.randalf.project.arenaparts;

import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import io.github.randalf.project.arenaparts.spawner.FloodMode;
import io.github.randalf.project.arenaparts.spawner.SpawnMode;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.entity.EntityType;
import java.util.EnumMap;
import java.util.Set;

/**
 * Data object for a arena
 * Holding all information necessary for an instance
 */
public class Arena {
        private String arenaName;
        private Area area;
        private SpawnMode mode;
        private ArenaSpawner spawner;
        private AreaManager areaManager;
        private ArenaListenerManager arenaListenerManager;
        private boolean active;

    /**
     * Basic Constructor for Arena
     * @param arenaName name of the arena
     * @param areaString name of the area
     */
    public Arena(String arenaName, String areaString) {
        this.arenaName = arenaName;
        areaManager = AreaManager.getInstance();
        area = areaManager.getArea(areaString);
        mode = new FloodMode(this);
        spawner = new ArenaSpawner(this, area, mode);
        EnumMap<ArenaOptions, ArenaListener> arenaListeners = new EnumMap<>(ArenaOptions.class);
        arenaListenerManager = new ArenaListenerManager(this, arenaListeners);
    }

    /**
     * Constructor for arena with options
     * @param arenaName name of the arena
     * @param areaString name of the area
     * @param options List of active options for the arena
     */
    public Arena(String arenaName, String areaString, Set<ArenaOptions> options) {
        this.arenaName = arenaName;
        areaManager = AreaManager.getInstance();
        area = areaManager.getArea(areaString);
        mode = new FloodMode(this);
        spawner = new ArenaSpawner(this, area, mode);
        EnumMap<ArenaOptions, ArenaListener> arenaListeners = new EnumMap<>(ArenaOptions.class);
        arenaListenerManager = new ArenaListenerManager(this, arenaListeners);
        for(ArenaOptions option: options)
            arenaListenerManager.setOption(option, true);
    }

    /**
     * Constructor for arena with mode
     * @param arenaName name of the arena
     * @param areaString name of the area
     * @param modus flood or round mode
     * @param et type of entity
     * @param enemyAmount amount of enemys in the arena
     */
    public Arena(String arenaName, String areaString,String modus, EntityType et, int enemyAmount) {
        this.arenaName = arenaName;
        areaManager = AreaManager.getInstance();
        area = areaManager.getArea(areaString);
        if(modus.equals("FloodMode")){
            mode = new FloodMode(this, et , enemyAmount);
        } else {
            mode = new FloodMode(this);
        }
        spawner = new ArenaSpawner(this, area, mode);
    }

    /**
     * Constructor for arena with mode and options
     * @param arenaName name of the arena
     * @param areaString name of the area
     * @param modus flood or round mode
     * @param et type of entity
     * @param enemyAmount amount of enemys in the arena
     * @param options List of active options for the arena
     */
    public Arena(String arenaName, String areaString,String modus, EntityType et, int enemyAmount, Set<ArenaOptions> options) {
        this.arenaName = arenaName;
        areaManager = AreaManager.getInstance();
        area = areaManager.getArea(areaString);
        if(modus.equals("FloodMode")){
            mode = new FloodMode(this, et , enemyAmount);
        } else {
            mode = new FloodMode(this);
        }
        spawner = new ArenaSpawner(this, area, mode);
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
    }

    /**
     * Getter for the area of the arena
     * @return the area of the arena
     */
    public Area getArea() {
        return area;
    }

    /**
     * Setter for the area of the arena
     * @param area new arena for the area
     */
    public void setArea(Area area) {
        this.area = area;
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
        this.arenaName = arenaName;
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

