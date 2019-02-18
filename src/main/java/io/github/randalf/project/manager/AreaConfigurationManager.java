package io.github.randalf.project.manager;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.serializer.AreaSerializer;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.config.DefaultConfig;
import javax.inject.Inject;
import java.nio.file.Path;

public class AreaConfigurationManager extends ObjectConfigurationManager{
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configRoot;

    private CommentedConfigurationNode configurationNode;
    private static final String OBJECT_TYPE = "Area";
    private Area area = (Area) object;

    public AreaConfigurationManager(String objectName, Area area){
        super(OBJECT_TYPE, objectName, area);
    }

    public void save(){
        try {
            configurationNode = configLoader.load();
            new AreaSerializer().serialize(TypeToken.of(Area.class), area, configurationNode);
            configLoader.save(configurationNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(){
        try {
            configurationNode = configLoader.load();
            area = new AreaSerializer().deserialize(TypeToken.of(Area.class), configurationNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Area getArea() {
        return area;
    }
}
