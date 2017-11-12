package io.github.randalf.project.arenaparts;

public class ArenaSpawner {

    private ArenaController controller;
    private ArenaArea area;
    private ArenaMode mode;

    public ArenaSpawner(ArenaController Controller, ArenaArea area, ArenaMode mode) {
        this.controller = controller;
        this.area = area;
        this.mode = mode;
    }
}
