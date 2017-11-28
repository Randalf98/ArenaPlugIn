package io.github.randalf.project.serializer;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;

import java.util.Collection;

public class AreaSerializer implements TypeSerializer<Area>{
    @Override
    public Area deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        Location startPoint = configurationNode.getNode("startpoint").getValue(TypeToken.of(Location.class));
        Collection<Chunk> areaChunks = configurationNode.getNode("areaChunks").getValue(TypeToken.of(Collection.class));
        Collection<Location> spawnLocations = configurationNode.getNode("spawnLocations").getValue(TypeToken.of(Collection.class));
        return new Area(startPoint, areaChunks, spawnLocations);
}

    @Override
    public void serialize(TypeToken<?> typeToken, Area area, ConfigurationNode configurationNode) throws ObjectMappingException {
        configurationNode.getNode("startpoint").setValue(area.getStartPoint());
        configurationNode.getNode("areaChunks").setValue(area.getAreaChunks());
        configurationNode.getNode("spawnLocations").setValue(area.getSpawnLocations());
    }
}
