package io.github.randalf.project.arenaparts.spawner;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;

import java.util.HashMap;
import java.util.Map;

public class Entitys {

    private static Map<String, EntityType> entityMap = new HashMap<String, EntityType>();
    static {
        entityMap.put("BAT",EntityTypes.BAT);
        entityMap.put("BLAZE",EntityTypes.BLAZE);
        entityMap.put("CAVE_SPIDER",EntityTypes.CAVE_SPIDER);
        entityMap.put("CHICKEN",EntityTypes.CHICKEN );
        entityMap.put("COW",EntityTypes.COW );
        entityMap.put("CREEPER",EntityTypes.CREEPER);
        entityMap.put("DONKEY",EntityTypes.DONKEY);
        entityMap.put("ELDER_GUARDIAN",EntityTypes.ELDER_GUARDIAN );
        entityMap.put("ENDERMAN", EntityTypes.ENDERMAN);
        entityMap.put("ENDERMITE", EntityTypes.ENDERMITE);
        entityMap.put("ENDER_DRAGON", EntityTypes.ENDER_DRAGON);
        entityMap.put("EVOCATION_ILLAGER", EntityTypes.EVOCATION_ILLAGER);
        entityMap.put("EXPERIENCE_ORB", EntityTypes.EXPERIENCE_ORB);
        entityMap.put("FIREBALL", EntityTypes.FIREBALL);
        entityMap.put("GHAST", EntityTypes.GHAST);
        entityMap.put("GIANT", EntityTypes.GIANT);
        entityMap.put("GUARDIAN", EntityTypes.GUARDIAN);
        entityMap.put("HORSE", EntityTypes.HORSE);
        entityMap.put("HUMAN", EntityTypes.HUMAN);
        entityMap.put("HUSK", EntityTypes.HUSK);
        entityMap.put("IRON_GOLEM", EntityTypes.IRON_GOLEM);
        entityMap.put("LLAMA", EntityTypes.LLAMA);
        entityMap.put("MAGMA_CUBE", EntityTypes.MAGMA_CUBE);
        entityMap.put("MULE", EntityTypes.MULE);
        entityMap.put("OCELOT", EntityTypes.OCELOT);
        entityMap.put("PARROT", EntityTypes.PARROT);
        entityMap.put("PIG", EntityTypes.PIG);
        entityMap.put("PIG_ZOMBIE", EntityTypes.PIG_ZOMBIE);
        entityMap.put("POLAR_BEAR", EntityTypes.POLAR_BEAR);
        entityMap.put("RABBIT", EntityTypes.RABBIT);
        entityMap.put("SHEEP", EntityTypes.SHEEP);
        entityMap.put("SHULKER", EntityTypes.SHULKER);
        entityMap.put("SILVERFISH", EntityTypes.SILVERFISH);
        entityMap.put("SKELETON", EntityTypes.SKELETON);
        entityMap.put("SKELETON_HORSE", EntityTypes.SKELETON_HORSE);
        entityMap.put("SLIME", EntityTypes.SLIME);
        entityMap.put("SNOWMAN", EntityTypes.SNOWMAN);
        entityMap.put("SPIDER", EntityTypes.SPIDER);
        entityMap.put("SQUID", EntityTypes.SQUID);
        entityMap.put("VEX", EntityTypes.VEX);
        entityMap.put("VILLAGER", EntityTypes.VILLAGER);
        entityMap.put("VINDICATION_ILLAGER", EntityTypes.VINDICATION_ILLAGER);
        entityMap.put("WITCH", EntityTypes.WITCH);
        entityMap.put("WITHER", EntityTypes.WITHER);
        entityMap.put("WITHER_SKELETON", EntityTypes.WITHER_SKELETON);
        entityMap.put("WOLF", EntityTypes.WOLF);
        entityMap.put("ZOMBIE", EntityTypes.ZOMBIE);
        entityMap.put("ZOMBIE_VILLAGER", EntityTypes.ZOMBIE_VILLAGER);
    }

    public static EntityType getEntity(String entityType){
        return entityMap.get(entityType.toUpperCase());
    }
}
