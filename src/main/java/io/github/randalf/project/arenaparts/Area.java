package io.github.randalf.project.arenaparts;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.Collection;

@ConfigSerializable
public class Area {

    @Setting(comment = "Startpoint from the location")
    private Location startPoint;

    @Setting(comment = "Length of the Area")
    private Double length;

    @Setting(comment = "Width of the Area")
    private Double width;

    @Setting(comment = "Height of the allowed Area")
    private Double height;

    @Setting(comment = "Lowest Point in the Area")
    private Double lowestPoint;

    private Collection<Location> spawnLocations;

    public Area(){
        //Default values
        setStartPoint(((Player)Sponge.getGame().getServer().getOnlinePlayers().toArray()[0]).getLocation());
        setLength(20.0);
        setWidth(10d);
        setHeight(40d);
        setLowestPoint(64d);

        Collection<Location> spawnLocations = new ArrayList<>();
        spawnLocations.add(((Player)Sponge.getGame().getServer().getOnlinePlayers().toArray()[0]).getLocation());
    }

    public Area(Location startPoint, Double length, Double width, Double height, Double lowestPoint, Collection<Location> spawnLocations){
        setStartPoint(startPoint);
        setLength(length);
        setWidth(width);
        setHeight(height);
        setLowestPoint(lowestPoint);
        setSpawnLocations(spawnLocations);
    }


    public Location getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Location startPoint) {
        this.startPoint = startPoint;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLowestPoint() {
        return lowestPoint;
    }

    public void setLowestPoint(Double lowestPoint) {
        this.lowestPoint = lowestPoint;
    }

    public Collection<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public void setSpawnLocations(Collection<Location> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }
}
