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
 * Data object for a area
 * Holding all information necessary for an instance
 */
@ConfigSerializable
public class Area {

    @Setting(comment = "Name of the area")
    private String areaName;

    @Setting(comment = "Startpoint from the location")
    private Vector3d startPoint;

    @Setting(comment = "UUID from the world of the area")
    private UUID worldUUID;

    @Setting(comment = "The Chunks of the area")
    private Collection<Vector3i> areaChunks;

    @Setting(comment = "The spawn locations of the area")
    private Collection<Vector3d> spawnLocations;

    /**
     * Default constructor for an area
     */
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

    /**
     * Constructor for area with specific values
     * @param areaName name of the area
     * @param startPoint initial location for the area
     * @param worldUUID world UUID in which it takes place
     * @param areaChunks all chunks which are assigned to the area
     * @param spawnLocations all locations which are assigned to the area
     */
    public Area(String areaName, Vector3d startPoint, UUID worldUUID, Collection<Vector3i> areaChunks, Collection<Vector3d> spawnLocations){
        setAreaName(areaName);
        setStartPoint(startPoint);
        setWorldUUID(worldUUID);
        setAreaChunks(areaChunks);
        setSpawnLocations(spawnLocations);
    }

    /**
     * Adds a chunk to the collection
     * @param chunk Vector3i which is a new chunk for the arena
     * @return boolean if it worked
     */
    public boolean addChunk(Vector3i chunk){
        if(!areaChunks.contains(chunk)){
            areaChunks.add(chunk);
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
        if(areaChunks.contains(chunk)){
            areaChunks.remove(chunk);
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
        if(spawnLocations.contains(spawnLocation)){
            spawnLocations.remove(spawnLocation);
            return true;
        }
        return false;
    }

    /**
     * Request of getting every player which is in the area
     * @return List of players which are in the area
     */
    public ArrayList<Player> getPlayerInArea() {
        ArrayList<Player> playerList = new ArrayList<>();
        for (Player p : Sponge.getServer().getOnlinePlayers()){
            if(contains(p.getLocation().getChunkPosition())){
                playerList.add(p);
            }
        }
        return playerList;
    }

    /**
     * Checks the position of the player against the area chunks
     * @param playerPosition position which needs to be checked
     * @return boolean value if the given vector is in collection
     */
    public boolean contains(Vector3i playerPosition){
        return areaChunks.contains(playerPosition);
    }

    /**
     * Getter for the areaName
     * @return
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * Setter for the areaName
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
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
     * Getter for the collection of areachunks
     * @return
     */
    public Collection<Vector3i> getAreaChunks() {
        return areaChunks;
    }

    /**
     * Setter for the areaChunks
     * @param areaChunks
     */
    public void setAreaChunks(Collection<Vector3i> areaChunks) {
        this.areaChunks = areaChunks;
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
