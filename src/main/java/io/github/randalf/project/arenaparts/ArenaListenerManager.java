package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.listener.*;
import org.spongepowered.api.Sponge;

import java.util.EnumMap;
import java.util.Set;

public class ArenaListenerManager {

    private Arena arena;
    private EnumMap<ArenaOptions, ArenaListener> arenaListeners;

    public ArenaListenerManager(Arena arena, EnumMap<ArenaOptions, ArenaListener> arenaListeners){
        this.arena = arena;
        this.arenaListeners = arenaListeners;
    }

    /**
     * Gets a specific listener based on a given arenaOption
     * @param option ArenaOption for a specific arenaListener
     * @return ArenaListener specified by the option parameter
     */
    public ArenaListener getListener(ArenaOptions option){
        switch(option) {
            case BURNING:
                return (new PreventBurningListener(arena));
            case DROP:
                return (new PreventDroppingListener(arena));
            case XP:
                return (new PreventXPDroppingListener(arena));
            case BUILDING:
                return (new ZoneManipulationListener(arena));
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
        if(arena.isActive()) {
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
     * Adds all given listener
     */
    public void addListener() {
        registerListener(arena.getSpawner().getListener());
        for(ArenaListener listener:arenaListeners.values()){
            registerListener(listener);
        }
    }

    /**
     * Removes all given listener
     */
    public void removeListener() {
        unregisterListener(arena.getSpawner().getListener());
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
     * Getter for option keys
     * @return all active options
     */
    public Set<ArenaOptions> getOptions(){
        return arenaListeners.keySet();
    }
}
