package io.github.randalf.project.arenaparts.spawner;

import java.util.List;

import org.spongepowered.api.entity.Entity;

import com.flowpowered.math.vector.Vector3d;

/**
 * interface for the mode management
 */
public interface SpawnMode {
	
    List<Entity> getNextEntities(Vector3d location);

    void createEntity();
    
}
