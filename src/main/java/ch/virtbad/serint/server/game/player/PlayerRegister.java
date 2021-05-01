package ch.virtbad.serint.server.game.player;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps track of players that are active on the server
 * @author Virt
 */
@Slf4j
public class PlayerRegister {

    private final Map<Integer, Player> players;
    private int lastId;

    private final Map<Integer, Integer> remotePlayerIdMappings;
    private final Map<Integer, Integer> playerRemoteIdMappings;

    /**
     * Creates a register
     */
    public PlayerRegister(){
        players = new HashMap<>();
        remotePlayerIdMappings = new HashMap<>();
        playerRemoteIdMappings = new HashMap<>();
    }

    /**
     * Adds a player
     * @param player player to add
     * @param remote id of the client the player belongs to
     * @return id of the player
     */
    public int createPlayer(Player player, int remote){
        int id = ++lastId;
        log.info("Adding Player with name {} from client {} which is mapped to {}", player.getName(), remote, id);

        player.setId(id);
        players.put(id, player);

        remotePlayerIdMappings.put(remote, id);
        playerRemoteIdMappings.put(id, remote);

        return id;
    }

    public boolean has(int id){
        return players.containsKey(id);
    }

    public boolean hasRemote(int id){
        return remotePlayerIdMappings.containsKey(id);
    }

    /**
     * Removes a player
     * @param id id of the player
     */
    public Player removePlayer(int id){
        if (!players.containsKey(id)) {
            log.error("Trying to remove invalid player with id {}", id);
            return null;
        }

        log.info("Removing Player with id: {}", id);
        int remote = playerRemoteIdMappings.get(id);
        remotePlayerIdMappings.remove(remote);
        playerRemoteIdMappings.get(id);

        return players.remove(id);
    }

    /**
     * Returns a player from the given id
     * @param id player id
     * @return player with that id
     */
    public Player getPlayer(int id){
        return players.get(id);
    }

    /**
     * Returns the id of the client from a player id
     * @param player player id
     * @return client id
     */
    public int getRemoteId(int player){
        return playerRemoteIdMappings.get(player);
    }

    /**
     * Returns the id of a player from a client id
     * @param remote remote client id
     * @return player id
     */
    public int getPlayerId(int remote){
        return remotePlayerIdMappings.get(remote);
    }

    /**
     * Returns all players that are in the game
     * @return players that are in the game
     */
    public Player[] getPlayers(){
        return players.values().toArray(new Player[0]);
    }

    /**
     * Returns the amount of players in the game
     * @return players in the game
     */
    public int getPlayerAmount(){
        return players.size();
    }

}
