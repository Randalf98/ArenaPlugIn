package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaManager;

public class ArenaArea {
    public static final String WORLD = "world";
    public static ArenaController controller;

    public ArenaArea(ArenaController controller, String areaString) {
        this.controller = controller;
        if(areaString == WORLD){

        } else {
            ArenaManager.getInstance().removeArena("Areastring wasn't in specified order. It was: " + areaString, controller);
        }
    }
}
