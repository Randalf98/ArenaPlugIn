package io.github.randalf.project.arenaparts.spawner;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.entity.Entity;

import java.util.List;

public class RoundMode implements SpawnMode {

    List<Round> roundList;

    @Override
    public List<Entity> getNextEntities(Vector3d location) {
        return null;
    }

    @Override
    public void setEntityType(String s) {}


}
