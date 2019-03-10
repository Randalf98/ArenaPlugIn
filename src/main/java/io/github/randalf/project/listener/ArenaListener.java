package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Arena;

/**
 * Abstract listener used to detect and/or prevent occurrences from happening in the arena
 */
public abstract class ArenaListener {
    public Arena arena;

    public ArenaListener(Arena arena){
        this.arena = arena;
    }
}