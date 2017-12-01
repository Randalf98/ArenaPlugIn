package io.github.randalf.project.arenaparts;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Chunk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@ConfigSerializable
public class Area {

    @Setting(comment = "Startpoint from the location")
    private Vector3d startPoint;

    @Setting(comment = "UUID from the world of the area")
    private UUID worldUUID;

    @Setting(comment = "The Chunks of the area")
    private Collection<Vector3i> areaChunks;

    @Setting(comment = "The spawn locations of the area")
    private Collection<Vector3d> spawnLocations;

    public Area(){
        //Default values
        Player player = ((Player)Sponge.getGame().getServer().getOnlinePlayers().toArray()[0]);
        setStartPoint(player.getLocation().getPosition());
        setWorldUUID(player.getLocation().getExtent().getUniqueId());

        Collection<Vector3i> areaChunks = new ArrayList<>();
        Optional<Chunk> optChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        optChunk.ifPresent(chunk -> areaChunks.add(chunk.getPosition()));
        setAreaChunks(areaChunks);

        Collection<Vector3d> spawnLocations = new ArrayList<>();
        spawnLocations.add(player.getLocation().getPosition());
        setSpawnLocations(spawnLocations);
    }

    public Area(Vector3d startPoint, UUID worldUUID, Collection<Vector3i> areaChunks, Collection<Vector3d> spawnLocations){
        setStartPoint(startPoint);
        setWorldUUID(worldUUID);
        setAreaChunks(areaChunks);
        setSpawnLocations(spawnLocations);
    }

    public Vector3d getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Vector3d startPoint) {
        this.startPoint = startPoint;
    }

    public UUID getWorldUUID() {
        return worldUUID;
    }

    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
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

    public Collection<Vector3d> getSpawnLocations() {
        return spawnLocations;
    }

    public void setSpawnLocations(Collection<Vector3d> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }

    public void addSpawnLocation(Vector3d spawnLocation){
        if(!spawnLocations.contains(spawnLocation)){
            spawnLocations.add(spawnLocation);
        }
    }
}
