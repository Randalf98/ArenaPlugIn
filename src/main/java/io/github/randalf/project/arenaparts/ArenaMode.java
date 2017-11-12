package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaManager;

public class ArenaMode {

    public static final String DEFAULT = "default";
    private static ArenaController controller;

    private ArenaSpawner spawner;

    public ArenaMode(ArenaController controller, String modeString) {
        this.controller = controller;
        if(modeString == DEFAULT){

        } else {
            ArenaManager.getInstance().removeArena("Modestring wasn't in specified order. It was: " + modeString, controller);
        }
    }
}