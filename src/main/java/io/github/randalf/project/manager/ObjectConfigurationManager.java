package io.github.randalf.project.manager;

import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.config.DefaultConfig;

import javax.inject.Inject;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public abstract class ObjectConfigurationManager {
    @Inject
    @DefaultConfig(sharedRoot = false)

    protected HoconConfigurationLoader configLoader;
    protected Object object;

    public ObjectConfigurationManager(String objectType, String objectName, Object object){
        this.object = object;
        Path configPath = FileSystems.getDefault().getPath("config", objectType+"\\" + objectName + ".conf");
        if(!configPath.toFile().exists()){
            configPath.toFile().getParentFile().mkdirs();
        }
        configLoader = HoconConfigurationLoader.builder().setPath(configPath).build();
    }

    abstract void save();

    abstract void load();

    public static boolean configExists(String objectType, String objectName){
        return FileSystems.getDefault().getPath(objectType, objectName + ".conf").toFile().exists();
    }
}
