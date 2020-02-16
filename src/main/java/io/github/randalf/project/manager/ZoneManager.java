package io.github.randalf.project.manager;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import io.github.randalf.project.arenaparts.Zone;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Chunk;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

/**
 * Singleton which deals as a serverwide supplier of zone objects
 */
public class ZoneManager {

    private static ZoneManager instance = null;
    private HashMap<String, Zone> zoneMap;

    /**
     * Protected Constructor exists only to defeat instantiation.
     */
    private ZoneManager() {}

    /**
     * Synchronized getter of the singleton instance of zonemanager
     * @return the instance of zonemanager
     */
    public static ZoneManager getInstance() {
        if(instance == null) {
            instance = new ZoneManager();
            instance.loadAllZones();
        }
        return instance;
    }


    /**
     * Checks the given zonename and creates a new zone based on the players location
     * @param zoneName the name for the new zone
     * @param player the player which will be used as locator of the arena
     */
    public void createZone(String zoneName, Player player){
        if (!zoneMap.containsKey(zoneName)){
            if(!ZoneConfigurationManager.configExists("Zone", zoneName)){
                Vector3d startPoint = player.getLocation().getPosition();

                Collection<Vector3i> zoneChunks = new ArrayList<>();
                Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
                optionalChunk.ifPresent(chunk -> zoneChunks.add(chunk.getPosition()));

                UUID worldUUID = player.getLocation().getExtent().getUniqueId();

                Collection<Vector3d> spawnLocations = new ArrayList<>();
                spawnLocations.add(player.getLocation().getPosition());

                Zone newZone = new Zone(zoneName, startPoint, worldUUID, zoneChunks, spawnLocations);

                saveZone(zoneName, newZone);
            }
            Zone zone = getZone(zoneName);
            zoneMap.put(zoneName, zone);
        }
    }

    /**
     * Checks the given zonename and returns the related zone
     * @param zoneName the name of the zone
     * @return the zone object stored in the zoneMap
     */
    public Zone getZone(String zoneName){
        if (zoneMap.containsKey(zoneName)) {
            return zoneMap.get(zoneName);
        } else {
            ZoneConfigurationManager acm = new ZoneConfigurationManager(zoneName, null);
            acm.load();
            return acm.getZone();
        }
    }

    /**
     * loads the zones out of the existing config files
     */
    private void loadAllZones(){
        zoneMap = new HashMap<>();
        Path configPath = FileSystems.getDefault().getPath("config/SpongeArenaPlugIn/Zone");
        File directory = configPath.toFile();

        File[] fList = directory.listFiles();
        for (File file : fList != null ? fList : new File[0]){
            if (file.canRead()){
                try {
                    ZoneConfigurationManager acm = new ZoneConfigurationManager(file.getName().replaceAll(".conf", ""), null);
                    acm.load();
                    Zone zone = acm.getZone();
                    zoneMap.put(file.getName().replaceAll(".conf", ""), zone);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets the chunk from the players position and adds it to the given zone
     * @param zoneName the name of the zone which will be extended
     * @param player value of the commanding player used for the chunk location
     */
    public void addChunkToArena(String zoneName, Player player) {
        Zone zone = getZone(zoneName);
        Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        if (optionalChunk.isPresent()){
            if (zone.addChunk(optionalChunk.get().getPosition())){
                saveZone(zoneName, zone);
            }
        }
    }

    /**
     * Gets the chunk from the players position and removes it from the given zone
     * @param zoneName the name of the zone which will be removed
     * @param player value of the commanding player used for the chunk location
     */
    public void removeChunkFromArena(String zoneName, Player player) {
        Zone zone = getZone(zoneName);
        Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        if (optionalChunk.isPresent()){
            if (zone.removeChunk(optionalChunk.get().getPosition())){
                saveZone(zoneName, zone);
            }
        }
    }

    /**
     * Adds the position of the player as a spawnpoint to the zone
     * @param zoneName the name of the zone
     * @param player player from which the location gets saved
     */
    public void addSpawnPointToZone(String zoneName, Player player) {
        Zone zone = getZone(zoneName);
        if (zone.addSpawnLocation(player.getLocation().getPosition())){
            saveZone(zoneName, zone);
        }
    }

    /**
     * Removes the position of the player from the spawnpoints of the zone
     * @param zoneName the name of the zone
     * @param player player from which the location gets removed
     */
    public void removeSpawnPointFromZone(String zoneName, Player player) {
        Zone zone = getZone(zoneName);
        if (zone.removeSpawnLocation(player.getLocation().getPosition())){
            saveZone(zoneName, zone);
        }
    }

    /**
     * Saves the given zone under the zoneName
     * @param zoneName the name of the zone
     * @param zone the zone object
     */
    private void saveZone(String zoneName, Zone zone){
        new ZoneConfigurationManager(zoneName, zone).save();
    }

    /**
     * Gets all keys from the zoneMap
     * @return the keyset of the zoneMap
     */
    public Set <String> getZoneNames() {
        return zoneMap.keySet();
    }

    public boolean mapContains(String zoneName) {
        return zoneMap.containsKey(zoneName);
    }
}
