package io.github.randalf.project.manager;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.serializer.AreaSerializer;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.config.DefaultConfig;
import javax.inject.Inject;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class AreaConfigurationManager {
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configRoot;

    private HoconConfigurationLoader configLoader;
    private CommentedConfigurationNode configurationNode;
    private Area area;
    private Path configPath;

    public AreaConfigurationManager(String objectName, Area area){
        this.area = area;
        configPath = FileSystems.getDefault().getPath("config", "area\\" + objectName + ".conf");
        if(!configPath.toFile().exists()){
            configPath.toFile().getParentFile().mkdirs();
        }
        configLoader = HoconConfigurationLoader.builder().setPath(configPath).build();
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

    public static boolean configExists(String areaName){
        return FileSystems.getDefault().getPath("area", areaName + ".conf").toFile().exists();
    }

    public void setConfig(CommentedConfigurationNode config){
        this.configurationNode = config;
    }

    public CommentedConfigurationNode getConfig() {
        return configurationNode;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
