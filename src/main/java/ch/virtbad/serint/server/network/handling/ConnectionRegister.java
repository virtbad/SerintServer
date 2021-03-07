package ch.virtbad.serint.server.network.handling;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Virt
 */
@Slf4j
public class ConnectionRegister {

    HashMap<UUID, Connection> connections;

    HashMap<Integer, UUID> gameIdMappings;
    private int lastId = 0;

    public ConnectionRegister(){
        this.connections = new HashMap<>();
        this.gameIdMappings = new HashMap<>();
    }

    public void connect(UUID uuid){
        log.info("Client Connected: {}", uuid);
        connections.put(uuid, new Connection(uuid));
    }

    public void join(UUID uuid){
        int id = ++lastId;
        connections.get(uuid).setInGame(id);
        gameIdMappings.put(id, uuid);

        log.info("Client {} joined with id: {}", uuid, id);
    }

    public void leave(UUID uuid){
        log.info("Client Left: {}", uuid);
        gameIdMappings.remove(connections.get(uuid).getGameId());
        connections.get(uuid).setOutGame();
    }

    public void disconnect(UUID uuid){
        log.info("Client Disconnected: {}", uuid);
        gameIdMappings.remove(connections.get(uuid).getGameId());
        connections.remove(uuid);
    }

    public void accept(UUID uuid){
        connections.get(uuid).accept();
    }

    public boolean isAccepted(UUID uuid){
        return connections.get(uuid).isAccepted();
    }

    public int getGameId(UUID uuid){
        if (!connections.containsKey(uuid)) return 0;
        return connections.get(uuid).getGameId();
    }

    public UUID getUUID(int id){
        return gameIdMappings.get(id);
    }

    public boolean isInGame(UUID uuid){
        return connections.get(uuid).isInGame();
    }
}
