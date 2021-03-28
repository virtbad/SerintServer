package ch.virtbad.serint.server.game;

import ch.virtbad.serint.server.game.item.DebugItem;
import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.item.ItemRegister;
import ch.virtbad.serint.server.game.item.SpeedItem;
import ch.virtbad.serint.server.game.map.MapLoader;
import ch.virtbad.serint.server.game.map.TileMap;
import ch.virtbad.serint.server.game.primitives.positioning.FixedLocation;
import ch.virtbad.serint.server.game.registers.Player;
import ch.virtbad.serint.server.game.registers.PlayerAttributes;
import ch.virtbad.serint.server.game.registers.PlayerRegister;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import ch.virtbad.serint.server.network.Communications;
import ch.virtbad.serint.server.network.handling.ConnectionSelector;
import lombok.Getter;

import java.awt.*;

/**
 * This class handles all the gameplay specific things on the server
 * @author Virt
 */
public class Game {

    private Communications com;

    private PlayerRegister players;
    private ItemRegister items;
    private MapLoader loader;

    private String currentMap;

    private int tickDelta = 0;

    @Getter
    private GameUpdater updater;

    /**
     * Creates a game
     * @param communications communications based on
     */
    public Game(Communications communications){
        this.com = communications;
        communications.setGame(this);

        updater = new GameUpdater(this, ConfigHandler.getConfig().getTps());

        players = new PlayerRegister();
        items = new ItemRegister();

        loader = new MapLoader();
        loader.read();
        currentMap = "TestLobby";
    }

    /**
     * Updates the server game
     */
    public void update(){
        tickDelta++;

        if (tickDelta % 600 == 0){
            TileMap.Action action = loader.getMap(currentMap).selectRandomAction(TileMap.Action.ActionType.ITEM);

            // TODO: Implement real spawning

            //Item item = new DebugItem();

            Item item = new SpeedItem(); // Yes indeed, very "pfusch"

            item.getLocation().setPosX(action.getX());
            item.getLocation().setPosY(action.getY());

            spawnItem(item);
        }
    }

    /**
     * Creates a player for a client
     * @param client id of the client
     * @param name name of the player
     * @param colour colour of the player
     * @return player id for that client
     */
    public int initializeClient(int client, String name, int colour){
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
     * @param client client to spawn
     */
    public void spawnClient(int client){
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
     * @param id id of the client
     */
    public void removeClient(int id){
        // Sends the removal of the client to all other clients
        com.sendRemovePlayer(players.getPlayerId(id), ConnectionSelector.exclude(id));

        // Removes the player from the game
        players.removePlayer(players.getPlayerId(id));
    }

    /**
     * Updates the location of the player of a client
     * @param id id of the client
     * @param x x coordinate
     * @param y y coordinate
     * @param velocityX x velocity
     * @param velocityY y velocity
     */
    public void updatePlayerLocation(int id, float x, float y, float velocityX, float velocityY){
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
     * Spawns an item into the world
     * @param item item to spawn
     */
    public void spawnItem(Item item){
        item = items.getItem(items.createItem(item)); // Id is being updated
        com.sendCreateItem(item, ConnectionSelector.exclude());
    }

    /**
     * Collects an item
     * @param itemId item that was collected
     * @param clientId client that collected
     */
    public void collectItem(int itemId, int clientId){
        if (!items.has(itemId)) return;
        items.removeItem(itemId).collect(players.getPlayer(players.getPlayerId(clientId)).getAttributes());
        com.sendRemoveItem(itemId, ConnectionSelector.exclude());
        com.sendPlayerAttributes(players.getPlayer(players.getPlayerId(clientId)), ConnectionSelector.exclude());
    }

    /**
     * Attacks a player
     * @param targetId attacked player
     * @param clientId client that attacked
     */
    public void attackPlayer(int targetId, int clientId){
        if (!players.has(targetId)) return;
        int newHealth = players.getPlayer(targetId).getAttributes().getHealth() - 1;

        // Set respawn location
        TileMap.Action spawn = loader.getMap(currentMap).selectRandomAction(TileMap.Action.ActionType.SPAWN);
        players.getPlayer(targetId).getLocation().setPosX(spawn.getX());
        players.getPlayer(targetId).getLocation().setPosY(spawn.getY());
        com.sendPlayerLocation(players.getPlayer(targetId), ConnectionSelector.exclude());

        // Reset Attributes
        players.getPlayer(targetId).setAttributes(new PlayerAttributes());
        players.getPlayer(targetId).getAttributes().setHealth(newHealth);
        com.sendPlayerAttributes(players.getPlayer(targetId), ConnectionSelector.exclude());

        if (players.getPlayer(targetId).getAttributes().getHealth() == 0){
            com.sendRemovePlayer(targetId, ConnectionSelector.exclude(players.getRemoteId(targetId)));
            com.kickPlayer("You ran out of lives!", ConnectionSelector.include(players.getRemoteId(targetId)));
        }

    }
}
