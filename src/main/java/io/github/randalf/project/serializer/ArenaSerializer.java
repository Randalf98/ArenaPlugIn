package io.github.randalf.project.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.Arena;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class ArenaSerializer implements TypeSerializer<Arena>{

    @Override
    public Arena deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
       return null;
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Arena arena, ConfigurationNode configurationNode) throws ObjectMappingException {
    }
}