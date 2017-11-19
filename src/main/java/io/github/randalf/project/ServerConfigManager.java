package io.github.randalf.project;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.config.DefaultConfig;

import javax.inject.Inject;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ServerConfigManager {
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configRoot;

    private HoconConfigurationLoader configLoader;
    private CommentedConfigurationNode config;

    public ServerConfigManager() throws Throwable {}

    public void init() throws Throwable {
        Path mainConfigPath = FileSystems.getDefault().getPath("config", "main-config.conf");
        if (!mainConfigPath.toFile().exists()){
            mainConfigPath.toFile().mkdirs();
        }
        configLoader = HoconConfigurationLoader.builder().setPath(mainConfigPath).build();
    }

    public void save() throws Throwable {
        configLoader.save(config);
    }

    public void load() throws Throwable {
        config = configLoader.load();
    }

    public void setConfig(CommentedConfigurationNode config){
        this.config = config;
    }

    public CommentedConfigurationNode getConfig() {
        return config;
    }
}
