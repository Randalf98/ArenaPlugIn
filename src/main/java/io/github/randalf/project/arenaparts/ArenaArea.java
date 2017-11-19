package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaManager;
import io.github.randalf.project.ServerConfigManager;

public class ArenaArea {
    public static final String WORLD = "world";
    public static ArenaController controller;
    private String area;

    public ArenaArea(ArenaController controller, String areaString) throws Throwable {
        this.controller = controller;
        if(areaString.equals(WORLD)){
            if(areaString.equals(WORLD)){
                area = WORLD;
            } else if(false){
                ServerConfigManager scm = new ServerConfigManager();
                scm.getConfig().setValue(new Arena());
            }
        } else {
            ArenaManager.getInstance().removeArena("Areastring wasn't in specified order. It was: " + areaString, controller);
        }
    }
}
