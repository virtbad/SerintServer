package ch.virtbad.serint.server.game;

import ch.virtbad.serint.server.game.actions.Action;
import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.item.ItemRegister;
import ch.virtbad.serint.server.game.item.ItemSpawner;
import ch.virtbad.serint.server.game.map.MapLoader;
import ch.virtbad.serint.server.game.map.TileMap;
import ch.virtbad.serint.server.game.player.Player;
import ch.virtbad.serint.server.game.player.PlayerAttributes;
import ch.virtbad.serint.server.game.player.PlayerRegister;
import ch.virtbad.serint.server.local.Time;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import ch.virtbad.serint.server.network.Communications;
import ch.virtbad.serint.server.network.handling.ConnectionSelector;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class handles all the gameplay specific things on the server
 *
 * @author Virt
 */
@Slf4j
public class Game {

    private Communications com;

    private PlayerRegister players;
    private ItemRegister items;
    private ItemSpawner itemSpawner;
    private MapLoader loader;

    private String currentMap;

    private int tickDelta = 0;

    @Getter
    private GameUpdater updater;

    private ArrayList<Action> actions;

    /**
     * Creates a game
     *
     * @param communications communications based on
     */
    public Game(Communications communications) {
        this.com = communications;
        communications.setGame(this);

        updater = new GameUpdater(this, ConfigHandler.getConfig().getTps());

        players = new PlayerRegister();
        items = new ItemRegister();
        itemSpawner = new ItemSpawner(communications, items);

        actions = new ArrayList<>();

        loader = new MapLoader();
        loader.read();
        currentMap = "TestLobby";
    }


    /**
     * Updates the server game
     */
    public void update() {
        tickDelta++;

        if (tickDelta % 200 == 0) {
            TileMap.Action action = loader.getMap(currentMap).selectRandomAction(TileMap.Action.ActionType.ITEM);

            // TODO: Implement real spawning

            //Item item = new DebugItem();
            itemSpawner.spawnItem(action);


        }

        float time = Time.getSeconds();
        for (int i = 0; i < actions.size(); i++) {

            if (time >= actions.get(i).getExpired()){
                actions.get(i).execute();
                actions.remove(i); // Not sus at all
            }
        }

        for (Player player : players.getPlayers()) {
            if (player.getAttributes().checkAttributes()) {
                com.sendPlayerAttributes(player, ConnectionSelector.exclude());
            }

        }
    }

    /**
     * Creates a player for a client
     *
     * @param client id of the client
     * @param name   name of the player
     * @param colour colour of the player
     * @return player id for that client
     */
    public int initializeClient(int client, String name, int colour) {
        // Get and Send Map
        com.sendMap(loader.getMap(currentMap), ConnectionSelector.include(client));

        // Sends all current players to the new player
        for (Player player : players.getPlayers()) {
            com.sendCreatePlayer(player, ConnectionSelector.include(client));
            com.sendPlayerLocation(player, ConnectionSelector.include(client));
        }

        // Sends all current items to the player
        for (Item item : items.getItems()) {
            com.sendCreateItem(item, ConnectionSelector.include(client));
        }

        // Creates player
        players.createPlayer(new Player(name, new Color(colour)), client);
        Player created = players.getPlayer(players.getPlayerId(client));

        // Sends new player to all other players
        com.sendCreatePlayer(created, ConnectionSelector.exclude(client));

        return players.getPlayerId(client);
    }

    /**
     * Spawns the client and sets its info
     *
     * @param client client to spawn
     */
    public void spawnClient(int client) {
        Player player = players.getPlayer(players.getPlayerId(client));

        // Set attributes
        player.setAttributes(new PlayerAttributes());
        com.sendPlayerAttributes(player, ConnectionSelector.exclude());

        // Set Spawn Location
        TileMap.Action spawn = loader.getMap(currentMap).selectRandomAction(TileMap.Action.ActionType.SPAWN);
        player.getLocation().setPosX(spawn.getX());
        player.getLocation().setPosY(spawn.getY());
        com.sendPlayerLocation(player, ConnectionSelector.exclude());
    }

    /**
     * Removes a player from the game
     *
     * @param id id of the client
     */
    public void removeClient(int id) {
        if(players.hasRemote(id)) { // Check whether player is still in register

            // Sends the removal of the client to all other clients
            com.sendRemovePlayer(players.getPlayerId(id), ConnectionSelector.exclude(id));

            // Removes the player from the game
            players.removePlayer(players.getPlayerId(id));
        }
    }

    /**
     * Updates the location of the player of a client
     *
     * @param id        id of the client
     * @param x         x coordinate
     * @param y         y coordinate
     * @param velocityX x velocity
     * @param velocityY y velocity
     */
    public void updatePlayerLocation(int id, float x, float y, float velocityX, float velocityY) {
        // Updates the location of the player
        Player player = players.getPlayer(players.getPlayerId(id));

        player.getLocation().setPosX(x);
        player.getLocation().setPosY(y);
        player.getLocation().setVelocityX(velocityX);
        player.getLocation().setVelocityY(velocityY);

        // Sends new player location to all other players
        com.sendPlayerLocation(player, ConnectionSelector.exclude(id));
    }


    /**
     * Collects an item
     *
     * @param itemId   item that was collected
     * @param clientId client that collected
     */
    public void collectItem(int itemId, int clientId) {
        if (!items.has(itemId)) return;
        itemSpawner.removeItem(itemId);

        items.removeItem(itemId).collect(players.getPlayer(players.getPlayerId(clientId)).getAttributes());
        com.sendRemoveItem(itemId, ConnectionSelector.exclude());
        com.sendPlayerAttributes(players.getPlayer(players.getPlayerId(clientId)), ConnectionSelector.exclude());
    }

    /**
     * Attacks a player
     *
     * @param targetId attacked player
     * @param issuerId client that attacked
     */
    public void attackPlayer(int targetId, int issuerId) {
        if (!players.has(targetId)) return;

        Player target = players.getPlayer(targetId);
        Player issuer = players.getPlayer(players.getPlayerId(issuerId));

        // Decrease Health
        target.getAttributes().clearAttributes();
        target.getAttributes().changeHealth(-1);
        com.sendPlayerAttributes(target, ConnectionSelector.exclude());

        if (target.getAttributes().getHealth() > 0){ // Player has still remaining lives

            log.info("Absorbing player");

            // Temporarily remove Player
            com.sendRemovePlayer(targetId, ConnectionSelector.exclude(players.getRemoteId(targetId)));
            com.sendAbsorption(issuer, ConfigHandler.getConfig().getRespawnTime(), ConnectionSelector.include(players.getRemoteId(targetId)));

            // Respawn player after delay
            actions.add(new Action(ConfigHandler.getConfig().getRespawnTime()) {
                @Override
                public void execute() {
                    log.info("Respawning player");
                    com.sendCreatePlayer(players.getPlayer(targetId), ConnectionSelector.exclude(players.getRemoteId(targetId)));

                    TileMap.Action spawn = loader.getMap(currentMap).selectRandomAction(TileMap.Action.ActionType.SPAWN);
                    players.getPlayer(targetId).getLocation().setPosX(spawn.getX());
                    players.getPlayer(targetId).getLocation().setPosY(spawn.getY());
                    com.sendPlayerLocation(players.getPlayer(targetId), ConnectionSelector.exclude());

                    com.sendRespawn(ConnectionSelector.include(players.getRemoteId(targetId)));
                }
            });

        }else { // Player is out of lives

            log.info("Loosing player");

            // Remove Player
            com.sendRemovePlayer(targetId, ConnectionSelector.exclude(players.getRemoteId(targetId)));
            com.sendLoose(issuer, ConnectionSelector.include(players.getRemoteId(targetId)));
            players.removePlayer(targetId);

            if (players.getPlayerAmount() == 1) { // Only one player left -> Last kill -> Game is won
                log.info("Game is won");
                com.sendWin(ConnectionSelector.include(issuerId));

                // Kick remaining clients (may be refined)
                actions.add(new Action(ConfigHandler.getConfig().getWinScreenTime() ) {
                    @Override
                    public void execute() {
                        log.info("Kicking everyone from server");
                        com.kickEveryone("Game Ended!");
                    }
                });
            }
        }
    }
}
