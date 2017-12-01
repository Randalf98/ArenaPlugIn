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
            if(!AreaConfigurationManager.configExists(areaName)){
                Vector3d startPoint = player.getLocation().getPosition();

                Collection<Vector3i> areaChunks = new ArrayList<>();
                Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
                optionalChunk.ifPresent(chunk -> areaChunks.add(chunk.getPosition()));

                UUID worldUUID = player.getLocation().getExtent().getUniqueId();

                Collection<Vector3d> spawnLocations = new ArrayList<>();
                spawnLocations.add(player.getLocation().getPosition());

                Area newArea = new Area(startPoint, worldUUID, areaChunks, spawnLocations);

                areaMap.put(areaName, newArea);

                new AreaConfigurationManager(areaName, newArea).save();
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
        Path configPath = FileSystems.getDefault().getPath("area");
        File directory = configPath.toFile();

        File[] fList = directory.listFiles();
        for (File file : fList != null ? fList : new File[0]){
            if (file.isFile()){
                try {
                    AreaConfigurationManager acm = new AreaConfigurationManager(file.getName(), null);
                    acm.load();
                    Area area = acm.getArea();
                    areaMap.put(file.getName(), area);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

    public void addChunkToArena(String areaName, Player player) {
        Area area = getArea(areaName);
        Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        optionalChunk.ifPresent(chunk -> area.addChunk(chunk.getPosition()));
    }
}
