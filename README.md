# SerintServer

> Server for the Serint project

## Related
* [SerintClient](https://github.com/virtbad/SerintClient) - Client for the Serint project
* [SerintMaps](https://github.com/virtbad/SerintMaps) - Map generator tool for the Serint project

## Usage

After running the server, the host interacts with it using commands sent through stdin. The following is a table of all supported commands:

| Command | Description                                                                            |
|---------|----------------------------------------------------------------------------------------|
| `start` | Starts a new game should at least two players be waiting in the lobby                  |
| `stop`  | Stop the active game should one currently be active                                    |
| `exit`  | Stops the server process. After running this command the server needs to be restarted! |
| `test`  | Tests whether the command interface is up and running                                  |

### Usage with docker

The server has an official docker image which can be pulled from `ghcr.io/virtbad/serint`. 

The following is an example docker-compose which can be used to deploy a server:

```yaml
services:
  serint:
    image: ghcr.io/virtbad/serint:latest
  tty: true
  stdin_open: true
  restart: unless-stopped
  ports:
    - "17371:17371/tcp"
  volumes:
    - "./config.json:/app/config.json"
```

Once deployed, one needs to attach to the server using `docker compose attach serint` to enter commands via stdin.


## Configuration

The server can be customized using a configuration file. The configuration file is a JSON file named `config.json` placed relative to the server jar.

The following shows the schema of the config file with its default values:

```typescript
{
  "tps": 20, // ticks per second at which the server runs
  "port": 17371, // port on which the server listens
  "name": "Serint Server", // name of the server
  "description": "A default Serint Server!", // description of the server
  "chanceVision": 0.475, // chance for a vision item to spawn
  "chanceSpeed": 0.475, // chance for a speed item to spawn
  "chanceSuperVision": 0.5, // chance for a vision item to be a super variant
  "chanceSuperSpeed": 0.5, // chance for a speed item to be a super variant
  "baseSpeed": 3, // base speed of the players
  "maxSpeed": 8, // maximum speed of the players
  "stepSpeed": 1, // delta by which the speed is increased for normal item
  "superStepSpeed": 5, // delta by which the speed is increased for super item
  "superDurationSpeed": 10, // duration of the super item effect in seconds
  "baseVision": 1.5, // base vision of the players
  "maxVision": 6, // maximum vision of the players
  "stepVision": 0.5, // delta by which the vision is increased for normal item
  "superStepVision": 10, // delta by which the vision is increased for super item
  "superDurationVision": 10, // duration of the super item effect in seconds
  "baseHealth": 5, // base health of the players
  "maxHealth": 5, // maximum health of the players
  "stepHealth": 1, // delta by which the health value is increased for health items
  "itemSpawnMin": 10, // minimum delay in seconds between spawning two items
  "itemSpawnMax": 20, // maximum delay in seconds between spawning two items
  "keepIssuer": 0.5, // factor of powerup stats a player keeps when killing a player
  "getIssuer": 0.0, // factor of powerup stats a player absorbs from a victim
  "keepVictim": 0.0, // factor of powerup stats a player keeps when being killed
  "getVictim": 0.0, // factor of powerup stats a victim absorbs from it's killer
  "respawnTime": 5, // respawn time of players in seconds
  "winScreenTime": 10, // duration of the win screen being shown in seconds
  "startTime": 10, // countdown duration after a game is started
  "gameMap": "Game", // name of the game map (not file name!)
  "lobbyMap": "Lobby" // name of the lobby map (not file name!)
}
```

For every item spawn an item is picked according to the following definition. For $x \in [0, 1)$,

```math
item = 
\begin{cases} 
vision & \text{if } x < chanceVision \\ 
speed & \text{if } x < chanceVision + chanceSpeed \\ 
health & \text{otherwise} 
\end{cases}
```

 ## License
 MIT © [VirtBad](https://github.com/virtbad/)
