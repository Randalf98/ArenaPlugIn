package io.github.randalf.project.manager;

import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import javax.inject.Singleton;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;

@Singleton
public class ArenaManager {

    private HashMap<String , Arena> arenaMap;

    private static ArenaManager instance;

    protected ArenaManager() {
        // Exists only to defeat instantiation.
    }

    public static synchronized  ArenaManager getInstance() {
        if(instance == null) {
            instance = new ArenaManager();
            instance.loadAllArenas();
        }
        return instance;
    }

    public void stopArena(String message, Arena arena){
        arena.stopArena();
        MessageChannel.TO_CONSOLE.send(Text.of(message));
    }

    public void startArena(String arenaName){
        arenaMap.get(arenaName).startArena();
    }

    public void stopArena(String arenaName) {
        stopArena("Stopped arena by User command", arenaMap.get(arenaName));
    }

    public boolean mapContains(String arenaName) {
        return arenaMap.containsKey(arenaName);
    }

    private void loadAllArenas(){
        arenaMap = new HashMap<>();
        Path configPath = FileSystems.getDefault().getPath("config/arena");
        File directory = configPath.toFile();

        File[] fList = directory.listFiles();
        for (File file : fList != null ? fList : new File[0]){
            if (file.canRead()){
                try {
                    ArenaConfigurationManager acm = new ArenaConfigurationManager(file.getName().replaceAll(".conf", ""), null);
                    acm.load();
                    Arena arena = acm.getArena();
                    arenaMap.put(file.getName().replaceAll(".conf", ""), arena);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
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

    public Set<String> getArenaNames() {
        return arenaMap.keySet();
    }
}
