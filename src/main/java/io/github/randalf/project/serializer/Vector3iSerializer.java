package io.github.randalf.project.serializer;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

/**
 * Created by b7hunnn on 01.12.2017.
 */
public class Vector3iSerializer implements TypeSerializer<Vector3i> {
    @Override
    public Vector3i deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        Double xCoordinate = configurationNode.getNode("xCoordinate").getValue(TypeToken.of(Double.class));
        Double yCoordinate = configurationNode.getNode("yCoordinate").getValue(TypeToken.of(Double.class));
        Double zCoordinate = configurationNode.getNode("zCoordinate").getValue(TypeToken.of(Double.class));
        return new Vector3i(xCoordinate, yCoordinate, zCoordinate);
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Vector3i vector, ConfigurationNode configurationNode) throws ObjectMappingException {
        configurationNode.getNode("xCoordinate").setValue(vector.getX());
        configurationNode.getNode("yCoordinate").setValue(vector.getY());
        configurationNode.getNode("zCoordinate").setValue(vector.getZ());
    }
}