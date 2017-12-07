package io.github.randalf.project.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import java.util.Collection;
import java.util.UUID;

public class AreaSerializer implements TypeSerializer<Area>{

    @Override
    public Area deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        Vector3d startPoint = configurationNode.getNode("startPoint").getValue(TypeToken.of(Vector3d.class));
        UUID worldUUID = configurationNode.getNode("worldUUID").getValue(TypeToken.of(UUID.class));
        Collection<Vector3i> areaChunks = configurationNode.getNode("areaChunks").getList(TypeToken.of(Vector3i.class));
        Collection<Vector3d> spawnLocations = configurationNode.getNode("spawnLocations").getList(TypeToken.of(Vector3d.class));
        return new Area(startPoint, worldUUID, areaChunks, spawnLocations);
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Area area, ConfigurationNode configurationNode) throws ObjectMappingException {
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
        configurationNode.getNode("spawnLocations").setValue(TypeToken.of(Collection.class), area.getSpawnLocations());
    }
}