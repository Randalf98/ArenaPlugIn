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

/**
 * Singleton which deals as a serverwide supplier of arena objects
 */
@Singleton
public class ArenaManager {

    private HashMap<String , Arena> arenaMap;

    private static ArenaManager instance;

    /**
     * Protected Constructor exists only to defeat instantiation.
     */
    protected ArenaManager() {}

    /**
     * Synchronized getter of the singleton instance of arenamanager
     * @return the instance of arenamanager
     */
    public static synchronized ArenaManager getInstance() {
        if(instance == null) {
            instance = new ArenaManager();
            instance.loadAllArenas();
        }
        return instance;
    }

    /**
     * Stops the arena and sends a message into the console
     * @param message message around the stopping circumstances
     * @param arena the stopping arena
     */
    public void stopArena(String message, Arena arena){
        arena.stopArena();
        MessageChannel.TO_CONSOLE.send(Text.of(message));
    }

    /**
     * Starting an arena based on the given arenaname
     * @param arenaName name of the arena
     */
    public void startArena(String arenaName){
        arenaMap.get(arenaName).startArena();
    }

    /**
     * Stopping an arena based on the given arenaname
     * @param arenaName name of the arena
     */
    public void stopArena(String arenaName) {
        stopArena("Stopped arena by User command", arenaMap.get(arenaName));
    }

    /**
     * Checks if an arenaName has a correlating arena in the arenamap
     * @param arenaName the name which will be checked
     * @return boolean value if the map contains the arenaName
     */
    public boolean mapContains(String arenaName) {
        return arenaMap.containsKey(arenaName);
    }

    /**
     * loads the arenas out of the existing config files
     */
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

    /**
     * Checks the given arenaname and creates a new arena
     * @param arenaName the name for the new arena
     * @param areaName the name for the linked area
     */
    public void createArena(String arenaName, String areaName) {
        if (!arenaMap.containsKey(arenaName)){
            if(!ArenaConfigurationManager.configExists("Arena",arenaName)){
                Arena newArena = new Arena(arenaName, areaName);
                saveArena(arenaName, newArena);
            }
            Arena arena = getArena(arenaName);
            arenaMap.put(arenaName, arena);
        }
    }

    /**
     * Checks the given arenaname and returns the related arena
     * @param arenaName the name of the arena
     * @return the arena object stored in the arenaMap
     */
    public Arena getArena(String arenaName){
        if (arenaMap.containsKey(arenaName)) {
            return arenaMap.get(arenaName);
        } else {
            ArenaConfigurationManager acm = new ArenaConfigurationManager(arenaName, null);
            acm.load();
            return acm.getArena();
        }
    }

    /**
     * Saves the arena with the arenaName
     * @param arenaName the name of the arena
     * @param arena the arena which will be saved
     */
    public void saveArena(String arenaName, Arena arena){
        new ArenaConfigurationManager(arenaName, arena).save();
    }

    /**
     * Gets all keys from the arenaMap
     * @return the keyset of the arenaMap
     */
    public Set<String> getArenaNames() {
        return arenaMap.keySet();
    }
}
