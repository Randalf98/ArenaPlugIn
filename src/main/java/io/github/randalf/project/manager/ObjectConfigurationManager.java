package io.github.randalf.project.manager;

import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.config.DefaultConfig;

import javax.inject.Inject;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Superclass for managagers of specific objects
 */
public abstract class ObjectConfigurationManager {
    @Inject
    @DefaultConfig(sharedRoot = false)
    HoconConfigurationLoader configLoader;
    protected Object object;

    /**
     * Constructor for superclass
     *
     * @param objectType name of the type of object which will be de/serialized
     * @param objectName name of the object which will be de/serialized
     * @param object the object which will be de/serialized
     */
    ObjectConfigurationManager(String objectType, String objectName, Object object){
        this.object = object;
        Path configPath = FileSystems.getDefault().getPath("config", "SpongeArenaPlugIn\\"+objectType+"\\" + objectName + ".conf");
        if(!configPath.toFile().exists()){
            configPath.toFile().getParentFile().mkdirs();
        }
        configLoader = HoconConfigurationLoader.builder().setPath(configPath).build();
    }

    /**
     * Abstract function for saving the given object
     */
    abstract void save();

    /**
     * Abstract function for loading the given object
     */
    abstract void load();

    /**
     * Checks if the given object exists
     * @param objectType name of the type of object which will be de/serialized
     * @param objectName name of the object which will be de/serialized
     * @return the boolean value of the request
     */
    static boolean configExists(String objectType, String objectName){
        return FileSystems.getDefault().getPath("SpongeArenaPlugIn\\"+objectType, objectName + ".conf").toFile().exists();
    }
}
