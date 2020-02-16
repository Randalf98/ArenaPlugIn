# SpongeArenaPlugIn
Additional PlugIn for Sponge Server

This Version of the Arena Plugin Features a Floodmode limited to a configurable amount of an entity at a time.

Commands:

* /arena start <arenaName>                           Starts the arena, based on the saved parameter.
* /arena stop <arenaName>                            Stops the arena.
* /arena create arena <arenaName> <zoneName>         Creates arena for a existing zone.
* /arena create zone <zoneName>                      Create a zone, which will be the field where the enemies spawn. The Players
                                                     Position will be the first spawnpoint
* /arena chunk add <zoneName>                        Adds a chunk to the zone.
* /arena chunk remove <zoneName>                     Removes a chunk to the zone.
* /arena spawnpoint add <zoneName>                   Adds an additional spawnpoint where the enemies will spawn.
* /arena spawnpoint remove <zoneName>                Removes a spawnpoint from the zone.
* /arena list zones                                  Lists all existing zones.
* /arena list arenas                                 Lists all existing arenas.
* /arena information <arenaName>                     Shows the relevant information from a arena.
* /arena set enemytype <arenaName> <entitytype>      Sets the entitytype of the arena mobs.
* /arena set enemyamount <arenaName> <enemyamount>   Sets the amount of enemies in the arena.
* /arena option add <arenaName> <arenaOption>        Adds an arenaOption to the arena.
* /arena option remove <arenaName> <arenaOption>     Removes an arenaOption from the arena.
  
ArenaOptions:
* XP            Prohibits XP dropping 
* DROP          Prohibits Item dropping
* BURNING       Prevents entity taking damage from burning
* BUILDING      Prevents building in the zone

Arenas and zones are getting saved in .conf Files
