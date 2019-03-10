# SpongeArenaPlugIn
Additional PlugIn for Sponge Server

This Version of the Arena Plugin Features a Floodmode limited to 10 Zombies at a time.

Commands:

* /startarena <arenaName>                     Starts the arena, based on the saved parameter.
* /stoparena <arenaName>                      Stops the arena.
* /createarena <arenaName> <areaName>         Creates arena for a existing area.
* /createarea <areaName>                      Create a area, which will be the field where the enemies spawn. The Players Position will     be the first spawnpoint
* /addchunktoarea <areaName>                  Adds a chunk to the area.
* /addspawnpointtoarea <areaName>             Adds an additional spawnpoint where the enemies will spawn.
* /listareas                                  Lists all existing areas.
* /listarenas                                 Lists all existing arenas.
* /arenaInformation <arenaName>               Shows the relevant information from a arena.
* /addoption <arenaName> <arenaOption>        Adds an arenaOption to the arena.
* /removeoption <arenaName> <arenaOption>     Removes an arenaOption from the arena.
  
ArenaOptions:
* XP            Prohibits XP dropping 
* DROP          Prohibits Item dropping
* BURNING       Prevents entity taking damage from burning
* BUILDING      Prevents building in the area

Arenas and areas are getting saved in .conf Files
