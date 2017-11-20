package io.github.randalf.project.arenaparts;

import org.spongepowered.api.world.Location;

import java.util.Collection;

public class ArenaLoader {
    public Area generateByConfig(String config){
        //Dummy Data before Hocon
        Location startpoint = null;
        Double length = 0d;
        Double width = 0d;
        Double heigt = 0d;
        Double lowestPoint = 0d;
        Collection<Location> spawnLocations = null;
        Area area = new Area(startpoint, length, width, heigt,lowestPoint, spawnLocations);
        return area;
    }
}
