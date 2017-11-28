package io.github.randalf.project.manager;

import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.ArenaArea;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

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
                Location startPoint = player.getLocation();

                Collection<Chunk> areaChunks = new ArrayList<>();
                Optional<Chunk> optionalChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
                optionalChunk.ifPresent(areaChunks::add);

                Collection<Location> spawnLocations = new ArrayList<>();
                spawnLocations.add(player.getLocation());

                Area newArea = new Area(startPoint, areaChunks, spawnLocations);

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
        for (File file : fList){
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
        optionalChunk.ifPresent(area::addChunk);
    }
}
