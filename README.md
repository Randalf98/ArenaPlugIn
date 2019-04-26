# SpongeArenaPlugIn
Additional PlugIn for Sponge Server

This Version of the Arena Plugin Features a Floodmode limited to a configurable amount of an entity at a time.

Commands:

* /arena start <arenaName>                           Starts the arena, based on the saved parameter.
* /arena stop <arenaName>                            Stops the arena.
* /arena create arena <arenaName> <areaName>         Creates arena for a existing area.
* /arena create area <areaName>                      Create a area, which will be the field where the enemies spawn. The Players                   
                                                     Position will be the first spawnpoint
* /arena chunk add <areaName>                        Adds a chunk to the area.
* /arena chunk remove <areaName>                     Removes a chunk to the area.
* /arena spawnpoint add <areaName>                   Adds an additional spawnpoint where the enemies will spawn.
* /arena spawnpoint remove <areaName>                Removes a spawnpoint from the area.
* /arena list areas                                  Lists all existing areas.
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
* BUILDING      Prevents building in the area

Arenas and areas are getting saved in .conf Files
