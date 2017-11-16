package io.github.randalf.project.serializer;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Arena;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.world.Location;

import java.util.Collection;

public class ArenaSerializer implements TypeSerializer<Arena>{
    @Override
    public Arena deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        Location startPoint = configurationNode.getNode("startpoint").getValue(TypeToken.of(Location.class));
        Double length = configurationNode.getNode("length").getDouble();
        Double width = configurationNode.getNode("width").getDouble();
        Double height = configurationNode.getNode("height").getDouble();
        Double lowestPoint = configurationNode.getNode("lowestPoint").getDouble();
        Collection<Location> spawnLocations = configurationNode.getNode("spawnLocations").getValue(TypeToken.of(Collection.class));
        return new Arena(startPoint, length, width, height, lowestPoint, spawnLocations);
}

    @Override
    public void serialize(TypeToken<?> typeToken, Arena arena, ConfigurationNode configurationNode) throws ObjectMappingException {
        configurationNode.getNode("startpoint").setValue(arena.getStartPoint());
        configurationNode.getNode("length").setValue(arena.getLength());
        configurationNode.getNode("width").setValue(arena.getWidth());
        configurationNode.getNode("height").setValue(arena.getHeight());
        configurationNode.getNode("lowestPoint").setValue(arena.getLowestPoint());
        configurationNode.getNode("spawnLocations").setValue(arena.getSpawnLocations());
    }
}
