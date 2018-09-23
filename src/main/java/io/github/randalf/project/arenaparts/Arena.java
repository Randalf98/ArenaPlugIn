package io.github.randalf.project.arenaparts;

import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import io.github.randalf.project.arenaparts.spawner.FloodMode;
import io.github.randalf.project.arenaparts.spawner.SpawnMode;
import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.Sponge;

public class Arena {
        private String arenaName;
        private Area area;
        private SpawnMode mode;
        private ArenaSecurity security;
        private ArenaSpawner spawner;
        private AreaManager areaManager;

        public Arena(String arenaName, String areaString) {
            this.arenaName = arenaName;
            areaManager = AreaManager.getInstance();
            area = areaManager.getArea(areaString);
            mode = new FloodMode(this);
            security = new ArenaSecurity(this, area, mode ,"default");
            spawner = new ArenaSpawner(this, area, mode);
        }

        public String getName() {
            return this.arenaName;
        }

        public void removeListener() {
            Sponge.getEventManager().unregisterListeners(security.getListener());
            Sponge.getEventManager().unregisterListeners(spawner.getListener());
        }

        public void disableSpawning() {
            spawner.stop();
        }

        public void startArena() {
            spawner.spawnEnemys();
        }

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public String getArenaName() {
            return arenaName;
        }

        public void setArenaName(String name) {
            this.arenaName = arenaName;
        }

        public SpawnMode getMode() {
            return mode;
        }

        public void setMode(SpawnMode mode) {
            this.mode = mode;
        }

        public ArenaSecurity getSecurity() {
            return security;
        }

        public void setSecurity(ArenaSecurity security) {
            this.security = security;
        }

        public ArenaSpawner getSpawner() {
            return spawner;
        }

        public void setSpawner(ArenaSpawner spawner) {
            this.spawner = spawner;
        }

        public AreaManager getAreaManager() {
            return areaManager;
        }

        public void setAreaManager(AreaManager areaManager) {
            this.areaManager = areaManager;
        }
    }
