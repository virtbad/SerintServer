package ch.virtbad.serint.server.network.handling;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * This class keeps track of clients that have connected to the server
 * @author Virt
 */
@Slf4j
public class ConnectionRegister {

    HashMap<UUID, Connection> connections;

    HashMap<Integer, UUID> gameIdMappings;
    private int lastId = 0;

    /**
     * Initializes the Connection register
     */
    public ConnectionRegister(){
        this.connections = new HashMap<>();
        this.gameIdMappings = new HashMap<>();
    }

    /**
     * Adds a Client that has connected to the register
     * @param uuid uuid of that client
     */
    public void connect(UUID uuid){
        log.info("Client Connected: {}", uuid);
        connections.put(uuid, new Connection(uuid));
    }

    /**
     * Marks the client as inGame
     * @param uuid uuid of the client
     */
    public void join(UUID uuid){
        int id = ++lastId;
        connections.get(uuid).setInGame(id);
        gameIdMappings.put(id, uuid);

        log.info("Client {} joined with id: {}", uuid, id);
    }

    /**
     * Marks the client as not inGame
     * @param uuid uuid of the client
     */
    public void leave(UUID uuid){
        log.info("Client Left: {}", uuid);
        gameIdMappings.remove(connections.get(uuid).getGameId());
        connections.get(uuid).setOutGame();
    }

    /**
     * Removes a client that has disconnected form the register
     * @param uuid client to remove
     */
    public void disconnect(UUID uuid){
        log.info("Client Disconnected: {}", uuid);
        gameIdMappings.remove(connections.get(uuid).getGameId());
        connections.remove(uuid);
    }

    /**
     * Sets the client as accepted. Accepted means, that the client is compatible with the server
     * @param uuid uuid of that client
     */
    public void accept(UUID uuid){
        connections.get(uuid).accept();
    }

    /**
     * Returns whether the client is accepted
     * @param uuid uuid of client
     * @return is accepted
     */
    public boolean isAccepted(UUID uuid){
        return connections.get(uuid).isAccepted();
    }

    /**
     * Returns the game id of the client
     * @param uuid server uuid of client
     * @return game id
     */
    public int getGameId(UUID uuid){
        if (!connections.containsKey(uuid)) return 0;
        return connections.get(uuid).getGameId();
    }

    /**
     * Returns the uuid of a client identified by a game id
     * @param id game id of the client
     * @return server uuid
     */
    public UUID getUUID(int id){
        return gameIdMappings.get(id);
    }

    /**
     * Returns whether a client is in game
     * @param uuid uuid of that client
     * @return is in game
     */
    public boolean isInGame(UUID uuid){
        return connections.get(uuid).isInGame();
    }

    /**
     * Returns all client that qualify for the given selector
     * @param selector connection selector to select with
     * @return array of server client ids
     */
    public UUID[] selectInGame(ConnectionSelector selector){
        List<UUID> uuids = new ArrayList<>();

        if (selector.getMode() == ConnectionSelector.INCLUDED){
            for (int i : selector.getIncluded()) {
                UUID uuid = getUUID(i);
                if (isInGame(uuid)) uuids.add(uuid);
            }
        }

        if (selector.getMode() == ConnectionSelector.EXCLUDED) {
            ex: for (Map.Entry<Integer, UUID> entry : gameIdMappings.entrySet()) {

                for (int i : selector.getExcluded()) {
                    if (entry.getKey() == i) {
                        continue ex;
                    }
                }

                UUID uuid = entry.getValue();
                if (isInGame(uuid)) uuids.add(uuid);
            }
        }

        return uuids.toArray(new UUID[0]);
    }

    /**
     * Returns every client connected to the server
     * @return Array of clients
     */
    public UUID[] getEveryone(){
        return connections.keySet().toArray(new UUID[0]);
    }
}
