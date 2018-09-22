package io.github.randalf.project.manager;

import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import java.util.HashMap;

public class ArenaManager {

    private static HashMap<String , Arena> arenaMap;

    private static ArenaManager instance = null;

    protected ArenaManager() {
        // Exists only to defeat instantiation.
    }

    public static ArenaManager getInstance() {
        if(instance == null) {
            instance = new ArenaManager();
            arenaMap = new HashMap<>();
        }
        return instance;
    }

    public void removeArena(String message, Arena arena){
        arena.removeListener();
        arena.disableSpawning();
        arenaMap.remove(arena.getName());
        MessageChannel.TO_CONSOLE.send(Text.of(message));
    }

    public void startArena(String arenaName){
        arenaMap.get(arenaName).startArena();
    }

    public void deleteArena(String arenaNane) {
        removeArena("Removed arena by User command", arenaMap.get(arenaNane));
    }

    public boolean mapContains(String arenaName) {
        return arenaMap.containsKey(arenaName);
    }

    public void createArena(String arenaName, String areaName) {
        if (!arenaMap.containsKey(arenaName)){
            if(!ArenaConfigurationManager.configExists("Arena",arenaName)){

                Arena newArena = new Arena(arenaName, areaName);

                saveArena(arenaName, newArena);
            }
            Arena arena = getArena(areaName);
            arenaMap.put(arenaName, arena);
        }
    }

    public Arena getArena(String arenaName){
        if (arenaMap.containsKey(arenaName)) {
            return arenaMap.get(arenaName);
        } else {
            ArenaConfigurationManager acm = new ArenaConfigurationManager(arenaName, null);
            acm.load();
            return acm.getArena();
        }
    }

    public void saveArena(String arenaName, Arena arena){
        new ArenaConfigurationManager(arenaName, arena).save();
    }
}
