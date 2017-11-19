package io.github.randalf.project.arenaparts;

import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;

import java.util.Collection;

public class ArenaLoader {
    public Arena generateByConfig(String config){
        //Dummy Data before Hocon
        Location startpoint = null;
        Double length = 0d;
        Double width = 0d;
        Double heigt = 0d;
        Double lowestPoint = 0d;
        Collection<Location> spawnLocations = null;
        Arena arena = new Arena(startpoint, length, width, heigt,lowestPoint, spawnLocations);
        return arena;
    }
}
