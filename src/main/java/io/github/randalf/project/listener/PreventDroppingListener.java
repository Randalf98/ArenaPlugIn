package io.github.randalf.project.listener;

import io.github.randalf.project.arenaparts.Arena;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.DropItemEvent;ClassCastException


/**
 * Listener used to detect and prevent items from dropping in the arena
 */
public class PreventDroppingListener extends ArenaListener {

    /**
     * Constructor for achieving the functionality of the listener
     * @param arena arena object
     */
    public PreventDroppingListener(Arena arena) {
        super(arena);
    }

    /**
     * Listener to react when an event of an item dropping occurs
     * @param event the DropItemEvent containing all necessary information to evaluate if the object gets dropped by a entity which is part of the arena
     */
    @Listener
    public void onItemDrop(DropItemEvent event){
        try{
            if(arena.getSpawner().isLastDiedEntity((Entity) event.getSource())){
                event.setCancelled(true);
            }
        } catch (ClassCastException ex){
            System.out.println(ex.getCause().getMessage());
        }
    }
}
