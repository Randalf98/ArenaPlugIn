package io.github.randalf.project.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

/**
 * TypeSerializer for Vector3d.class
 *
 * @see ninja.leaping.configurate.objectmapping.serialize.TypeSerializer
 */
public class Vector3dSerializer implements TypeSerializer<Vector3d> {
    @Override
    public Vector3d deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        Double xCoordinate = configurationNode.getNode("xCoordinate").getValue(TypeToken.of(Double.class));
        Double yCoordinate = configurationNode.getNode("yCoordinate").getValue(TypeToken.of(Double.class));
        Double zCoordinate = configurationNode.getNode("zCoordinate").getValue(TypeToken.of(Double.class));
        return new Vector3d(xCoordinate, yCoordinate, zCoordinate);
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Vector3d vector, ConfigurationNode configurationNode) throws ObjectMappingException {
        configurationNode.getNode("xCoordinate").setValue(vector.getX());
        configurationNode.getNode("yCoordinate").setValue(vector.getY());
        configurationNode.getNode("zCoordinate").setValue(vector.getZ());
    }
}