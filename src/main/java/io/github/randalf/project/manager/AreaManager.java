package io.github.randalf.project.manager;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Area;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Chunk;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

/**
 * Singleton which deals as a serverwide supplier of area objects
 */
public class AreaManager {

    private static AreaManager instance = null;
    private HashMap<String, Area> areaMap;

    /**
     * Protected Constructor exists only to defeat instantiation.
     */
    protected AreaManager() {}

    /**
     * Synchronized getter of the singleton instance of areamanager
     * @return the instance of areamanager
     */
    public static AreaManager getInstance() {
        if(instance == null) {
            instance = new AreaManager();
            instance.loadAllAreas();
        }
        return instance;
    }


    /**
     * Checks the given areaname and creates a new area based on the players location
     * @param areaName the name for the new area
     * @param player the player which will be used as locator of the arena
     */
    public void createArea(String areaName,Player player){
        if (!areaMap.containsKey(areaName)){
            if(!AreaConfigurationManager.configExists("Area", areaName)){
                Vector3d startPoint = player.getLocation().getPosition();

                Collection<Vector3i> areaChunks = new ArrayList<>();
                Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
                optionalChunk.ifPresent(chunk -> areaChunks.add(chunk.getPosition()));

                UUID worldUUID = player.getLocation().getExtent().getUniqueId();

                Collection<Vector3d> spawnLocations = new ArrayList<>();
                spawnLocations.add(player.getLocation().getPosition());

                Area newArea = new Area(areaName, startPoint, worldUUID, areaChunks, spawnLocations);

                saveArea(areaName, newArea);
            }
            Area area = getArea(areaName);
            areaMap.put(areaName, area);
        }
    }

    /**
     * Checks the given areaname and returns the related area
     * @param areaName the name of the area
     * @return the area object stored in the areaMap
     */
    public Area getArea(String areaName){
        if (areaMap.containsKey(areaName)) {
            return areaMap.get(areaName);
        } else {
            AreaConfigurationManager acm = new AreaConfigurationManager(areaName, null);
            acm.load();
            return acm.getArea();
        }
    }

    /**
     * loads the areas out of the existing config files
     */
    private void loadAllAreas(){
        areaMap = new HashMap<>();
        Path configPath = FileSystems.getDefault().getPath("config/SpongeArenaPlugIn/Area");
        File directory = configPath.toFile();

        File[] fList = directory.listFiles();
        for (File file : fList != null ? fList : new File[0]){
            if (file.canRead()){
                try {
                    AreaConfigurationManager acm = new AreaConfigurationManager(file.getName().replaceAll(".conf", ""), null);
                    acm.load();
                    Area area = acm.getArea();
                    areaMap.put(file.getName().replaceAll(".conf", ""), area);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets the chunk from the players position and adds it to the given area
     * @param areaName the name of the area which will be extended
     * @param player value of the commanding player used for the chunk location
     */
    public void addChunkToArena(String areaName, Player player) {
        Area area = getArea(areaName);
        Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        if (optionalChunk.isPresent()){
            if (area.addChunk(optionalChunk.get().getPosition())){
                saveArea(areaName, area);
            }
        }
    }

    /**
     * Gets the chunk from the players position and removes it from the given area
     * @param areaName the name of the area which will be removed
     * @param player value of the commanding player used for the chunk location
     */
    public void removeChunkFromArena(String areaName, Player player) {
        Area area = getArea(areaName);
        Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        if (optionalChunk.isPresent()){
            if (area.removeChunk(optionalChunk.get().getPosition())){
                saveArea(areaName, area);
            }
        }
    }

    /**
     * Adds the position of the player as a spawnpoint to the area
     * @param areaName the name of the area
     * @param player player from which the location gets saved
     */
    public void addSpawnPointToArea(String areaName, Player player) {
        Area area = getArea(areaName);
        if (area.addSpawnLocation(player.getLocation().getPosition())){
            saveArea(areaName, area);
        }
    }

    /**
     * Removes the position of the player from the spawnpoints of the area
     * @param areaName the name of the area
     * @param player player from which the location gets removed
     */
    public void removeSpawnPointFromArea(String areaName, Player player) {
        Area area = getArea(areaName);
        if (area.removeSpawnLocation(player.getLocation().getPosition())){
            saveArea(areaName, area);
        }
    }

    /**
     * Saves the given area under the areaName
     * @param areaName the name of the area
     * @param area the area object
     */
    public void saveArea(String areaName, Area area){
        new AreaConfigurationManager(areaName, area).save();
    }

    /**
     * Checks if an areaName has a correlating arena in the areamap
     * @param areaName the name which will be checked
     * @return boolean value if the map contains the arenaName
     */
    public boolean mapContains(String areaName) {
        return areaMap.containsKey(areaName);
    }

    /**
     * Gets all keys from the areaMap
     * @return the keyset of the areaMap
     */
    public Set <String> getAreaNames() {
        return areaMap.keySet();
    }
}
