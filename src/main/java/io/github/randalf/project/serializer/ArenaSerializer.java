package io.github.randalf.project.serializer;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.arenaparts.ArenaOptions;
import io.github.randalf.project.arenaparts.spawner.FloodMode;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.entity.EntityType;

import java.util.EnumSet;
import java.util.Set;

public class ArenaSerializer implements TypeSerializer<Arena>{

    @Override
    public Arena deserialize(TypeToken<?> typeToken, ConfigurationNode configurationNode) throws ObjectMappingException {
        String arenaName = configurationNode.getNode("arenaName").getValue(TypeToken.of(String.class));
        String areaName = configurationNode.getNode("areaName").getValue(TypeToken.of(String.class));
        String mode =  configurationNode.getNode("arenaMode").getValue(TypeToken.of(String.class));
        Set<ArenaOptions> options = EnumSet.noneOf(ArenaOptions.class);
        for(ArenaOptions arenaOption: ArenaOptions.values()){
            if((Boolean)(configurationNode.getNode("arenaOptions").getNode(arenaOption.name()).getValue(TypeToken.of(Boolean.class)))){
                options.add(ArenaOptions.valueOf(arenaOption.name()));
            }
        }
        if(mode.equals("FloodMode")){
            EntityType entityType = configurationNode.getNode("arenaEnemies").getValue(TypeToken.of(EntityType.class));
            int entityAmount = configurationNode.getNode("arenaEnemyAmount").getValue(TypeToken.of(Integer.class));
            return new Arena(arenaName, areaName, mode, entityType, entityAmount, options);
        } else {
            return new Arena(arenaName, areaName, options);
        }
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Arena arena, ConfigurationNode configurationNode) throws ObjectMappingException {
        configurationNode.getNode("arenaName").setValue(TypeToken.of(String.class), arena.getName());
        configurationNode.getNode("areaName").setValue(TypeToken.of(String.class), arena.getArea().getAreaName());
        String mode = arena.getMode().getClass().getSimpleName();
        configurationNode.getNode("arenaMode").setValue(TypeToken.of(String.class), mode);
        Set<ArenaOptions> arenaOptions = arena.getOptions();
        for(ArenaOptions arenaOption: ArenaOptions.values()){
            configurationNode.getNode("arenaOptions").getNode(arenaOption.name()).setValue(TypeToken.of(Boolean.class), arenaOptions.contains(arenaOption));
        }
        if(mode.equals("FloodMode")){
            configurationNode.getNode("arenaEnemies").setValue(TypeToken.of(EntityType.class), ((FloodMode)arena.getMode()).getEntityType());
            configurationNode.getNode("arenaEnemyAmount").setValue(TypeToken.of(Integer.class), ((FloodMode)arena.getMode()).getEntityAmount());
        }
    }
}