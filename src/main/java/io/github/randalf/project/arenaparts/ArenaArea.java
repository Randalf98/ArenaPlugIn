package io.github.randalf.project.arenaparts;

import io.github.randalf.project.manager.ArenaManager;
import io.github.randalf.project.manager.AreaConfigurationManager;

public class ArenaArea {
    public static final String WORLD = "world";
    public static ArenaController controller;
    private String area;

    public ArenaArea(ArenaController controller, String areaString) {
        this.controller = controller;
        if(areaString.equals(WORLD)){
            if(areaString.equals(WORLD)){
                area = WORLD;
            }
        } else {
            ArenaManager.getInstance().removeArena("Areastring wasn't in specified order. It was: " + areaString, controller);
        }
    }
}
