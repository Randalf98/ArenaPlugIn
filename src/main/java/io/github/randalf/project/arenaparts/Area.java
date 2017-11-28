package io.github.randalf.project.arenaparts;

import com.flowpowered.math.vector.Vector3i;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ConfigSerializable
public class Area {

    @Setting(comment = "Startpoint from the location")
    private Location startPoint;

    @Setting(comment = "The Chunks of the area")
    private Collection<Vector3i> areaChunks;

    @Setting(comment = "The spawn locations of the area")
    private Collection<Location> spawnLocations;

    public Area(){
        //Default values
        Player player = ((Player)Sponge.getGame().getServer().getOnlinePlayers().toArray()[0]);
        setStartPoint(player.getLocation());

        Collection<Vector3i> areaChunks = new ArrayList<>();
        Optional<Chunk> optChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        if (optChunk.isPresent()){
            areaChunks.add(optChunk.get().getPosition());
        }
        setAreaChunks(areaChunks);

        Collection<Location> spawnLocations = new ArrayList<>();
        spawnLocations.add(player.getLocation());
        setSpawnLocations(spawnLocations);
    }

    public Area(Location startPoint, Collection<Vector3i> areaChunks, Collection<Location> spawnLocations){
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

    public Collection<Vector3i> getAreaChunks() {
        return areaChunks;
    }

    public void setAreaChunks(Collection<Vector3i> areaChunks) {
        this.areaChunks = areaChunks;
    }

    public void addChunk(Vector3i chunk){
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
