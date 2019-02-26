package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.entity.Entity;
import java.util.List;

/**
 * Unimplemented Round mode where you can define specific waves
 */
public class RoundMode implements SpawnMode {

    List<Round> roundList;

    /**
     * @since not yet
     */
    @Override
    public List<Entity> getNextEntities(Vector3d location) {
        return null;
    }
}
