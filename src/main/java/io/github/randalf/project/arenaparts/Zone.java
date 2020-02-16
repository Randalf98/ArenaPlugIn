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

/**
 * Data object for a zone
 * Holding all information necessary for an instance
 */
@ConfigSerializable
public class Zone {

    @Setting(comment = "Name of the zone")
    private String zoneName;

    @Setting(comment = "Startpoint from the location")
    private Vector3d startPoint;

    @Setting(comment = "UUID from the world of the zone")
    private UUID worldUUID;

    @Setting(comment = "The Chunks of the zone")
    private Collection<Vector3i> zoneChunks;

    @Setting(comment = "The spawn locations of the zone")
    private Collection<Vector3d> spawnLocations;

    /**
     * Default constructor for an zone
     */
    public Zone(){
        Player player = ((Player)Sponge.getGame().getServer().getOnlinePlayers().toArray()[0]);
        setStartPoint(player.getLocation().getPosition());
        setWorldUUID(player.getLocation().getExtent().getUniqueId());

        Collection<Vector3i> zoneChunks = new ArrayList<>();
        Optional<Chunk> optChunk = player.getWorld().getChunk(player.getLocation().getChunkPosition());
        optChunk.ifPresent(chunk -> zoneChunks.add(chunk.getPosition()));
        setZoneChunks(zoneChunks);

        Collection<Vector3d> spawnLocations = new ArrayList<>();
        spawnLocations.add(player.getLocation().getPosition());
        setSpawnLocations(spawnLocations);
    }

    /**
     * Constructor for zone with specific values
     * @param zoneName name of the zone
     * @param startPoint initial location for the zone
     * @param worldUUID world UUID in which it takes place
     * @param zoneChunks all chunks which are assigned to the zone
     * @param spawnLocations all locations which are assigned to the zone
     */
    public Zone(String zoneName, Vector3d startPoint, UUID worldUUID, Collection<Vector3i> zoneChunks, Collection<Vector3d> spawnLocations){
        setZoneName(zoneName);
        setStartPoint(startPoint);
        setWorldUUID(worldUUID);
        setZoneChunks(zoneChunks);
        setSpawnLocations(spawnLocations);
    }

    /**
     * Adds a chunk to the collection
     * @param chunk Vector3i which is a new chunk for the arena
     * @return boolean if it worked
     */
    public boolean addChunk(Vector3i chunk){
        if(!zoneChunks.contains(chunk)){
            zoneChunks.add(chunk);
            return true;
        }
        return false;
    }

    /**
     * Removes a chunk from the collection
     * @param chunk Vector3i which is a chunk from the arena
     * @return boolean if it worked
     */
    public boolean removeChunk(Vector3i chunk){
        if(zoneChunks.contains(chunk)){
            zoneChunks.remove(chunk);
            return true;
        }
        return false;
    }

    /**
     * Add a spawn location to the collection
     * @param spawnLocation Vector3d which is a new spawn location
     * @return boolean if it worked
     */
    public boolean addSpawnLocation(Vector3d spawnLocation){
        if(!spawnLocations.contains(spawnLocation)){
            spawnLocations.add(spawnLocation);
            return true;
        }
        return false;
    }

    /**
     * Add a spawn location to the collection
     * @param spawnLocation Vector3d which is a new spawn location
     * @return boolean if it worked
     */
    public boolean removeSpawnLocation(Vector3d spawnLocation){
        return spawnLocations.removeIf(sl -> sl.distanceSquared(spawnLocation)<1);
    }

    /**
     * Request of getting every player which is in the zone
     * @return List of players which are in the zone
     */
    public ArrayList<Player> getPlayerInZone() {
        ArrayList<Player> playerList = new ArrayList<>();
        for (Player p : Sponge.getServer().getOnlinePlayers()){
            if(contains(p.getLocation().getChunkPosition())){
                playerList.add(p);
            }
        }
        return playerList;
    }

    /**
     * Checks the position of the player against the zone chunks
     * @param playerPosition position which needs to be checked
     * @return boolean value if the given vector is in collection
     */
    public boolean contains(Vector3i playerPosition){
        return zoneChunks.contains(playerPosition);
    }

    /**
     * Getter for the zoneName
     * @return
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * Setter for the zoneName
     * @param zoneName
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * Getter for the startPoint
     * @return
     */
    public Vector3d getStartPoint() {
        return startPoint;
    }

    /**
     * Setter for the startPoint
     * @param startPoint
     */
    public void setStartPoint(Vector3d startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Getter for the world UUID
     * @return
     */
    public UUID getWorldUUID() {
        return worldUUID;
    }

    /**
     * Setter for the world UUID
     * @param worldUUID
     */
    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
    }

    /**
     * Getter for the collection of zoneChunks
     * @return
     */
    public Collection<Vector3i> getZoneChunks() {
        return zoneChunks;
    }

    /**
     * Setter for the zoneChunks
     * @param zoneChunks
     */
    public void setZoneChunks(Collection<Vector3i> zoneChunks) {
        this.zoneChunks = zoneChunks;
    }

    /**
     * Getter for the spawn locations
     * @return
     */
    public Collection<Vector3d> getSpawnLocations() {
        return spawnLocations;
    }

    /**
     * Setter for the spawnLocations
     * @param spawnLocations
     */
    public void setSpawnLocations(Collection<Vector3d> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }
}
