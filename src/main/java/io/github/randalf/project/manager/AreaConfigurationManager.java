package io.github.randalf.project.manager;

import io.github.randalf.project.arenaparts.Area;
import io.github.randalf.project.arenaparts.ArenaController;
import io.github.randalf.project.serializer.AreaSerializer;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.config.DefaultConfig;

import javax.inject.Inject;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
        configPath = FileSystems.getDefault().getPath("area", objectName + ".conf");
        if(!configPath.toFile().exists()){
            configPath.toFile().mkdirs();
        }
        configLoader = HoconConfigurationLoader.builder().setPath(configPath).build();
    }

    public void save(){
        try {
            new AreaSerializer().serialize(null, area, configurationNode);
            configLoader.save(configurationNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(){
        try {
            configurationNode = configLoader.load();
            area = new AreaSerializer().deserialize(null, configurationNode);
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
