package io.github.randalf.project;

import io.github.randalf.project.arenaparts.ArenaController;
import io.github.randalf.project.arenaparts.ArenaSecurity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.HashMap;
import java.util.Random;

public class ArenaManager {

    private HashMap<String , ArenaController> arenaMap;

    private static ArenaManager instance = null;

    protected ArenaManager() {
        // Exists only to defeat instantiation.
    }
    public static ArenaManager getInstance() {
        if(instance == null) {
            instance = new ArenaManager();
        }
        return instance;
    }

    public void addArena(String arenaName, ArenaController arenaController){
        while(arenaMap.containsKey(arenaName)){
            arenaName += Integer.toString((new Random()).nextInt((9-1)+1)+1);
        }
        if(!arenaMap.containsValue(arenaController)){
            arenaController.setName(arenaName);
            arenaMap.put(arenaName, arenaController);
        }
    }

    public void removeArena(String message, ArenaController arenaController){
        arenaController.removeListener();
        arenaController.disableSpawning();
        arenaMap.remove(arenaController.getName());
        MessageChannel.TO_CONSOLE.send(Text.of(message));
    }

    public void startArena(String arenaName){
        arenaMap.get(arenaName).startArena();
    }

    public void deleteArena(String arena) {
        removeArena("Removed arena by User command", arenaMap.get(arena));
    }

    public boolean mapContains(String mapName) {
        return arenaMap.containsKey(mapName);
    }
}
