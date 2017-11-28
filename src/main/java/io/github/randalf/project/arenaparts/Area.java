package io.github.randalf.project.arenaparts;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.Collection;

@ConfigSerializable
public class Area {

    @Setting(comment = "Startpoint from the location")
    private Location startPoint;

    @Setting(comment = "The Chunks of the arena")
    private Collection<Chunk> areaChunks;

    @Setting(comment = "The spawn locations of the arena")
    private Collection<Location> spawnLocations;

    public Area(){
        //Default values
        Player player = ((Player)Sponge.getGame().getServer().getOnlinePlayers().toArray()[0]);
        setStartPoint(player.getLocation());

        Collection<Chunk> areaChunks = new ArrayList<>();
        areaChunks.add((player.getWorld().getChunk(player.getLocation().getChunkPosition())).get());
        setAreaChunks(areaChunks);

        Collection<Location> spawnLocations = new ArrayList<>();
        spawnLocations.add(player.getLocation());
        setSpawnLocations(spawnLocations);
    }

    public Area(Location startPoint, Collection<Chunk> areaChunks, Collection<Location> spawnLocations){
        setStartPoint(startPoint);
        setAreaChunks(areaChunks);
        setSpawnLocations(spawnLocations);
    }

    public Location getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Location startPoint) {
        this.startPoint = startPoint;
    }

    public Collection<Chunk> getAreaChunks() {
        return areaChunks;
    }

    public void setAreaChunks(Collection<Chunk> areaChunks) {
        this.areaChunks = areaChunks;
    }

    public void addChunk(Chunk chunk){
        if(!areaChunks.contains(chunk)){
            areaChunks.add(chunk);
        }
    }

    public Collection<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public void setSpawnLocations(Collection<Location> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }
}
