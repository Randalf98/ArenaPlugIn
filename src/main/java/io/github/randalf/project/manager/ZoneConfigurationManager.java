package io.github.randalf.project.manager;

import com.google.common.reflect.TypeToken;
import io.github.randalf.project.arenaparts.Zone;
import io.github.randalf.project.serializer.ZoneSerializer;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.config.DefaultConfig;
import javax.inject.Inject;
import java.nio.file.Path;

/**
 * ConfigurationManager for Zone de/serialization
 */
public class ZoneConfigurationManager extends ObjectConfigurationManager{
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configRoot;

    private CommentedConfigurationNode configurationNode;
    private static final String OBJECT_TYPE = "Zone";
    private Zone zone = (Zone) object;

    /**
     * Constructor for ACM
     * @param objectName Name of the given zone
     * @param zone zone which should get saved or loaded
     */
    public ZoneConfigurationManager(String objectName, Zone zone){
        super(OBJECT_TYPE, objectName, zone);
    }

    /**
     * Saves the given object into a config
     */
    public void save(){
        try {
            configurationNode = configLoader.load();
            new ZoneSerializer().serialize(TypeToken.of(Zone.class), zone, configurationNode);
            configLoader.save(configurationNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the object from a given config
     */
    public void load(){
        try {
            configurationNode = configLoader.load();
            zone = new ZoneSerializer().deserialize(TypeToken.of(Zone.class), configurationNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for the zone
     * @return the zone
     */
    public Zone getZone() {
        return zone;
    }
}
