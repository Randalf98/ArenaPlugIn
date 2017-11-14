package io.github.randalf.project.arenaparts;

import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;

public class ArenaLoader {
    public Arena generateByConfig(String config){
        //Dummy Data before Hocon
        Location startpoint = null;
        Double length = 0d;
        Double width = 0d;
        Double heigt = 0d;
        Double lowestPoint = 0d;
        Arena arena = new Arena(startpoint, length, width, heigt,lowestPoint);
        return arena;
    }
}
