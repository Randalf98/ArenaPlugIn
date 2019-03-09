package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import io.github.randalf.project.arenaparts.spawner.FloodMode;
import io.github.randalf.project.arenaparts.spawner.SpawnMode;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.PreventBurningListener;
import io.github.randalf.project.listener.PreventDroppingListener;
import io.github.randalf.project.listener.PreventXPDroppingListener;
import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.Sponge;
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
        private ArenaSecurity security;
        private ArenaSpawner spawner;
        private AreaManager areaManager;
        private boolean active;
        private EnumMap<ArenaOptions, ArenaListener> arenaListeners = new EnumMap<>(ArenaOptions.class);

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
        security = new ArenaSecurity(this, area, mode ,"default");
        spawner = new ArenaSpawner(this, area, mode);
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
        security = new ArenaSecurity(this, area, mode ,"default");
        spawner = new ArenaSpawner(this, area, mode);
        for(ArenaOptions option: options)
            setOption(option, true);
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
        security = new ArenaSecurity(this, area, mode ,"default");
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
        security = new ArenaSecurity(this, area, mode ,"default");
        spawner = new ArenaSpawner(this, area, mode);
        for(ArenaOptions option: options)
            setOption(option, true);
    }

    /**
     * Getter for the name of the arena
     * @return name of the arena
     */
    public String getName() {
        return this.arenaName;
    }

    /**
     * Adds all given listener
     */
    private void addListener() {
        registerListener(security.getListener());
        registerListener(spawner.getListener());
        for(ArenaListener listener:arenaListeners.values()){
            registerListener(listener);
        }
    }

    /**
     * Removes all given listener
     */
    private void removeListener() {
        unregisterListener(security.getListener());
        unregisterListener(spawner.getListener());
        for(ArenaListener listener:arenaListeners.values()){
            unregisterListener(listener);
        }
    }

    /**
     * Registers a single listener to the Sponge Event Manager
     * @param listener a ArenaListener
     */
    private void registerListener(ArenaListener listener){
        Sponge.getEventManager().registerListeners(ArenaPlugIn.getInstance(), listener);
    }

    /**
     * Unregisters a single listener from the Sponge Event Manager
     * @param listener a ArenaListener
     */
    private void unregisterListener(ArenaListener listener){
        Sponge.getEventManager().unregisterListeners(listener);
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
        addListener();
        spawner.start();
    }

    /**
     * Stops the arena object
     */
    public void stopArena(){
        active = false;
        removeListener();
        disableSpawning();
    }

    /**
     * Gets a specific listener based on a given arenaOption
     * @param option ArenaOption for a specific arenaListener
     * @return ArenaListener specified by the option parameter
     */
    public ArenaListener getListener(ArenaOptions option){
        switch(option) {
            case BURNING:
                return (new PreventBurningListener(spawner, area));
            case DROP:
                return (new PreventDroppingListener(spawner, area));
            case XP:
                return (new PreventXPDroppingListener(spawner, area));
        }
        return null;
    }

    /**
     * Sets a given option when activated
     * @param option ArenaOption for a specific arenaListener
     * @param activation boolean if the listener really should added
     */
    public void setOption(ArenaOptions option, boolean activation){
        if(activation){
            arenaListeners.put(option, getListener(option));
        }
    }

    /**
     * Adds a single listener to a running arena
     * @param option ArenaOption for a specific arenaListener
     */
    public void addOption(ArenaOptions option){
        ArenaListener listener = getListener(option);
        arenaListeners.put(option, listener);
        if(active) {
            registerListener(listener);
        }
    }

    /**
     * Removes a single listener from a running arena
     * @param option ArenaOption for a specific arenaListener
     */
    public void removeOption(ArenaOptions option){
        if (arenaListeners.containsKey(option)) {
            unregisterListener(arenaListeners.get(option));
            arenaListeners.remove(option);
        }
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

    /**
     * Getter for option keys
     * @return all active options
     */
    public Set getOptions(){
        return arenaListeners.keySet();
    }
}

