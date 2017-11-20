package io.github.randalf.project.arenaparts;

import io.github.randalf.project.ArenaManager;
import io.github.randalf.project.ServerConfigManager;

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
            //Just for testing
            try {
                if(true){
                    ServerConfigManager scm = new ServerConfigManager();
                    scm.getConfig().setValue(new Area());
                    scm.save();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            ArenaManager.getInstance().removeArena("Areastring wasn't in specified order. It was: " + areaString, controller);
        }
    }
}
