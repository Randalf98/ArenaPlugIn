package io.github.randalf.project.serializer;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.world.Location;

import java.util.Collection;

public class ArenaSerializer implements TypeSerializer<Area>{
    @Override
    public Area deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        Location startPoint = configurationNode.getNode("startpoint").getValue(TypeToken.of(Location.class));
        Double length = configurationNode.getNode("length").getDouble();
        Double width = configurationNode.getNode("width").getDouble();
        Double height = configurationNode.getNode("height").getDouble();
        Double lowestPoint = configurationNode.getNode("lowestPoint").getDouble();
        Collection<Location> spawnLocations = configurationNode.getNode("spawnLocations").getValue(TypeToken.of(Collection.class));
        return new Area(startPoint, length, width, height, lowestPoint, spawnLocations);
}

    @Override
    public void serialize(TypeToken<?> typeToken, Area area, ConfigurationNode configurationNode) throws ObjectMappingException {
        configurationNode.getNode("startpoint").setValue(area.getStartPoint());
        configurationNode.getNode("length").setValue(area.getLength());
        configurationNode.getNode("width").setValue(area.getWidth());
        configurationNode.getNode("height").setValue(area.getHeight());
        configurationNode.getNode("lowestPoint").setValue(area.getLowestPoint());
        configurationNode.getNode("spawnLocations").setValue(area.getSpawnLocations());
    }
}
