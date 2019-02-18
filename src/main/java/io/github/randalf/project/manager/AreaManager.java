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

public class AreaManager {

    private static AreaManager instance = null;
    private HashMap<String, Area> areaMap;

    protected AreaManager() {
        // Exists only to defeat instantiation.
    }

    public static AreaManager getInstance() {
        if(instance == null) {
            instance = new AreaManager();
            instance.loadAllAreas();
        }
        return instance;
    }

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

    public Area getArea(String areaName){
        if (areaMap.containsKey(areaName)) {
            return areaMap.get(areaName);
        } else {
            AreaConfigurationManager acm = new AreaConfigurationManager(areaName, null);
            acm.load();
            return acm.getArea();
        }
    }

    private void loadAllAreas(){
        areaMap = new HashMap<>();
        Path configPath = FileSystems.getDefault().getPath("config/area");
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

    public void addChunkToArena(String areaName, Player player) {
        Area area = getArea(areaName);
        Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        if (optionalChunk.isPresent()){
            if (area.addChunk(optionalChunk.get().getPosition())){
                saveArea(areaName, area);
            }
        }
    }

    public void addSpawnPointToArea(String areaName, Player player) {
        Area area = getArea(areaName);
        if (area.addSpawnLocation(player.getLocation().getPosition())){
            saveArea(areaName, area);
        }
    }

    public void saveArea(String areaName, Area area){
        new AreaConfigurationManager(areaName, area).save();
    }

    public Set <String> getAreaNames() {
        return areaMap.keySet();
    }
}
