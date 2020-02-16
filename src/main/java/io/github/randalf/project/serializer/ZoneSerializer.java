package io.github.randalf.project.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Zone;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import java.util.*;

/**
 * TypeSerializer for Zone.class
 *
 * @see ninja.leaping.configurate.objectmapping.serialize.TypeSerializer
 */
public class ZoneSerializer implements TypeSerializer<Zone>{

    @Override
    public Zone deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        String zoneName = configurationNode.getNode("zoneName").getValue(TypeToken.of(String.class));
        Vector3d startPoint = configurationNode.getNode("startPoint").getValue(TypeToken.of(Vector3d.class));
        UUID worldUUID = configurationNode.getNode("worldUUID").getValue(TypeToken.of(UUID.class));
        Collection<Vector3i> zoneChunks = new ArrayList<>();
        for (Map.Entry<Object, ? extends ConfigurationNode> map : configurationNode.getNode("zoneChunks").getChildrenMap().entrySet()){
            zoneChunks.add(map.getValue().getValue(TypeToken.of(Vector3i.class)));
        }
        Collection<Vector3d> spawnLocations = new ArrayList<>();
        for (Map.Entry<Object, ? extends ConfigurationNode> map : configurationNode.getNode("spawnLocations").getChildrenMap().entrySet()){
            spawnLocations.add(map.getValue().getValue(TypeToken.of(Vector3d.class)));
        }
        return new Zone(zoneName, startPoint, worldUUID, zoneChunks, spawnLocations);
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Zone zone, ConfigurationNode configurationNode) throws ObjectMappingException {
        configurationNode.getNode("zoneName").setValue(TypeToken.of(String.class), zone.getZoneName());
        configurationNode.getNode("startPoint").setValue(TypeToken.of(Vector3d.class), zone.getStartPoint());
        configurationNode.getNode("worldUUID").setValue(TypeToken.of(UUID.class), zone.getWorldUUID());
        int i = 0;
        for(Vector3i zoneChunk: zone.getZoneChunks()){
            configurationNode.getNode("zoneChunks").getNode("chunk"+i++).setValue(TypeToken.of(Vector3i.class), zoneChunk);
        }
        i = 0;
        for(Vector3d location: zone.getSpawnLocations()){
            configurationNode.getNode("spawnLocations").getNode("location"+i++).setValue(TypeToken.of(Vector3d.class), location);
        }
    }
}