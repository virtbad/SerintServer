package ch.virtbad.serint.server.game.registers;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Virt
 */
@Slf4j
public class PlayerRegister {

    private final Map<Integer, Player> players;
    private int lastId;

    private final Map<Integer, Integer> remotePlayerIdMappings;
    private final Map<Integer, Integer> playerRemoteIdMappings;

    public PlayerRegister(){
        players = new HashMap<>();
        remotePlayerIdMappings = new HashMap<>();
        playerRemoteIdMappings = new HashMap<>();
    }

    public int createPlayer(Player player, int remote){
        int id = ++lastId;
        log.info("Adding Player with name {} from client {} which is mapped to {}", player.getName(), remote, id);

        player.setId(id);
        players.put(id, player);

        remotePlayerIdMappings.put(remote, id);
        playerRemoteIdMappings.put(id, remote);

        return id;
    }

    public void removePlayer(int id){
        if (!players.containsKey(id)) {
            log.error("Trying to remove invalid player with id {}", id);
            return;
        }

        log.info("Removing Player with id: {}", id);
        int remote = playerRemoteIdMappings.get(id);
        remotePlayerIdMappings.remove(remote);
        playerRemoteIdMappings.get(id);

        players.remove(id);
    }

    public Player getPlayer(int id){
        return players.get(id);
    }

    public int getRemoteId(int player){
        return playerRemoteIdMappings.get(player);
    }

    public int getPlayerId(int remote){
        return remotePlayerIdMappings.get(remote);
    }

    public Player[] getPlayers(){
        return players.values().toArray(new Player[0]);
    }

}
