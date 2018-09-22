package io.github.randalf.project.manager;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Arena;
import io.github.randalf.project.serializer.ArenaSerializer;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.config.DefaultConfig;
import javax.inject.Inject;
import java.nio.file.Path;

public class ArenaConfigurationManager extends ObjectConfigurationManager{
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configRoot;

    private CommentedConfigurationNode configurationNode;
    private Arena arena = (Arena) object;
    private Path configPath;
    private static final String OBJECT_TYPE = "Arena";

    public ArenaConfigurationManager(String objectName, Arena arena){
        super(OBJECT_TYPE, objectName, arena);
    }

    public void save(){
        try {
            configurationNode = configLoader.load();
            new ArenaSerializer().serialize(TypeToken.of(Arena.class), arena, configurationNode);
            configLoader.save(configurationNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(){
        try {
            configurationNode = configLoader.load();
            arena = new ArenaSerializer().deserialize(TypeToken.of(Arena.class), configurationNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Arena getArena() {
        return arena;
    }
}
