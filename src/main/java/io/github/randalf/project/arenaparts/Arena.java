package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaPlugIn;
import io.github.randalf.project.arenaparts.spawner.ArenaSpawner;
import io.github.randalf.project.arenaparts.spawner.FloodMode;
import io.github.randalf.project.arenaparts.spawner.SpawnMode;
import io.github.randalf.project.listener.ArenaListener;
import io.github.randalf.project.listener.PreventBurningListener;
import io.github.randalf.project.listener.PreventDroppingListener;
import io.github.randalf.project.listener.PreventXPDroppingListener;
import io.github.randalf.project.manager.AreaManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityType;

import java.util.EnumMap;
import java.util.List;
import java.util.Set;

import static io.github.randalf.project.arenaparts.ArenaOptions.*;

public class Arena {
        private String arenaName;
        private Area area;
        private SpawnMode mode;
        private ArenaSecurity security;
        private ArenaSpawner spawner;
        private AreaManager areaManager;
        private boolean active;
        private EnumMap<ArenaOptions, ArenaListener> arenaListeners = new EnumMap<>(ArenaOptions.class);

        public Arena(String arenaName, String areaString) {
            this.arenaName = arenaName;
            areaManager = AreaManager.getInstance();
            area = areaManager.getArea(areaString);
            mode = new FloodMode(this);
            security = new ArenaSecurity(this, area, mode ,"default");
            spawner = new ArenaSpawner(this, area, mode);
        }

        public Arena(String arenaName, String areaString, Set<ArenaOptions> options) {
            this.arenaName = arenaName;
            areaManager = AreaManager.getInstance();
            area = areaManager.getArea(areaString);
            mode = new FloodMode(this);
            security = new ArenaSecurity(this, area, mode ,"default");
            spawner = new ArenaSpawner(this, area, mode);
            for(ArenaOptions option: options)
                setOption(option, true);
        }

        public Arena(String arenaName, String areaString,String modus, EntityType et, int enemyAmount) {
            this.arenaName = arenaName;
            areaManager = AreaManager.getInstance();
            area = areaManager.getArea(areaString);
            if(modus.equals("FloodMode")){
                mode = new FloodMode(this, et , enemyAmount);
            } else {
                mode = new FloodMode(this);
            }
            security = new ArenaSecurity(this, area, mode ,"default");
            spawner = new ArenaSpawner(this, area, mode);
        }

        public Arena(String arenaName, String areaString,String modus, EntityType et, int enemyAmount, Set<ArenaOptions> options) {
            this.arenaName = arenaName;
            areaManager = AreaManager.getInstance();
            area = areaManager.getArea(areaString);
            if(modus.equals("FloodMode")){
                mode = new FloodMode(this, et , enemyAmount);
            } else {
                mode = new FloodMode(this);
            }
            security = new ArenaSecurity(this, area, mode ,"default");
            spawner = new ArenaSpawner(this, area, mode);
            for(ArenaOptions option: options)
                setOption(option, true);
        }

        public String getName() {
            return this.arenaName;
        }

        private void addListener() {
            registerListener(security.getListener());
            registerListener(spawner.getListener());
            for(ArenaListener listener:arenaListeners.values()){
                registerListener(listener);
            }
        }
        private void removeListener() {
            unregisterListener(security.getListener());
            unregisterListener(spawner.getListener());
            for(ArenaListener listener:arenaListeners.values()){
                unregisterListener(listener);
            }
        }

        private void registerListener(ArenaListener listener){
            Sponge.getEventManager().registerListeners(ArenaPlugIn.getInstance(), listener);
        }

        private void unregisterListener(ArenaListener listener){
            Sponge.getEventManager().unregisterListeners(listener);
        }

        private void disableSpawning() {
            spawner.stop();
        }

        public void startArena() {
            active = true;
            addListener();
            spawner.start();
        }

        public void stopArena(){
            active = false;
            removeListener();
            disableSpawning();
        }

        public ArenaListener getListener(ArenaOptions option){
            switch(option) {
                case BURNING:
                    return (new PreventBurningListener(spawner, area));
                case DROP:
                    return (new PreventDroppingListener(spawner, area));
                case XP:
                    return (new PreventXPDroppingListener(spawner, area));
            }
            return null;
        }

        public void setOption(ArenaOptions option, boolean activation){
            if(activation){
                arenaListeners.put(option, getListener(option));
            }
        }

        public void addOption(ArenaOptions option){
            ArenaListener listener = getListener(option);
            arenaListeners.put(option, listener);
            registerListener(listener);
        }

        public void removeOption(ArenaOptions option){
            if (arenaListeners.containsKey(option)) {
                unregisterListener(arenaListeners.get(option));
                arenaListeners.remove(option);
            }
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

        public Set getOptions(){
            return arenaListeners.keySet();
        }
    }

