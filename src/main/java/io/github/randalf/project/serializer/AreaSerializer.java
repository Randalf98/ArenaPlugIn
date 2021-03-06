package io.github.randalf.project.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import java.util.*;

/**
 * TypeSerializer for Area.class
 *
 * @see ninja.leaping.configurate.objectmapping.serialize.TypeSerializer
 */
public class AreaSerializer implements TypeSerializer<Area>{

    @Override
    public Area deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        String areaName = configurationNode.getNode("areaName").getValue(TypeToken.of(String.class));
        Vector3d startPoint = configurationNode.getNode("startPoint").getValue(TypeToken.of(Vector3d.class));
        UUID worldUUID = configurationNode.getNode("worldUUID").getValue(TypeToken.of(UUID.class));
        Collection<Vector3i> areaChunks = new ArrayList<>();
        for (Map.Entry<Object, ? extends ConfigurationNode> map : configurationNode.getNode("areaChunks").getChildrenMap().entrySet()){
            areaChunks.add(map.getValue().getValue(TypeToken.of(Vector3i.class)));
        }
        Collection<Vector3d> spawnLocations = new ArrayList<>();
        for (Map.Entry<Object, ? extends ConfigurationNode> map : configurationNode.getNode("spawnLocations").getChildrenMap().entrySet()){
            spawnLocations.add(map.getValue().getValue(TypeToken.of(Vector3d.class)));
        }
        return new Area(areaName, startPoint, worldUUID, areaChunks, spawnLocations);
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Area area, ConfigurationNode configurationNode) throws ObjectMappingException {
        configurationNode.getNode("areaName").setValue(TypeToken.of(String.class), area.getAreaName());
        configurationNode.getNode("startPoint").setValue(TypeToken.of(Vector3d.class), area.getStartPoint());
        configurationNode.getNode("worldUUID").setValue(TypeToken.of(UUID.class),area.getWorldUUID());
        int i = 0;
        for(Vector3i areaChunk: area.getAreaChunks()){
            configurationNode.getNode("areaChunks").getNode("chunk"+i++).setValue(TypeToken.of(Vector3i.class), areaChunk);
        }
        i = 0;
        for(Vector3d location: area.getSpawnLocations()){
            configurationNode.getNode("spawnLocations").getNode("location"+i++).setValue(TypeToken.of(Vector3d.class), location);
        }
    }
}