package ch.virtbad.serint.server.network;

import ch.virt.pseudopackets.handlers.ServerPacketHandler;
import ch.virt.pseudopackets.packets.Packet;
import ch.virt.pseudopackets.server.Server;
import ch.virtbad.serint.server.Serint;
import ch.virtbad.serint.server.game.Game;
import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.map.TileMap;
import ch.virtbad.serint.server.game.primitives.positioning.MovedLocation;
import ch.virtbad.serint.server.game.registers.Player;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import ch.virtbad.serint.server.network.handling.ConnectionRegister;
import ch.virtbad.serint.server.network.handling.ConnectionSelector;
import ch.virtbad.serint.server.network.packets.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * This class handles all packet traffic
 * @author Virt
 */
@Slf4j
public class Communications extends CustomServerPacketHandler {

    @Setter private Server server; // Is getting set automatically after server creation
    @Setter private Game game; // Same as server

    private final ConnectionRegister register;

    /**
     * Creates the communication
     */
    public Communications(){
        register = new ConnectionRegister();
    }


    // ----- Packet Sending -----

    /**
     * Sends a packet over the server
     * @param packet packet to send
     * @param client uuid to identify client
     */
    private void sendPacket(Packet packet, UUID client){
        log.trace("Sent packet {} to client {}", packet.getId(), client); // Should sooner or later be implemented in PseudoPackets
        server.sendPacket(packet, client);
    }
    /**
     * Sends packet over the server
     * @param packet packet to send
     * @param id id to identify connected client
     */
    private void sendPacket(Packet packet, int id){
        sendPacket(packet, register.getUUID(id));
    }

    /**
     * Sends packet over the server using a selector
     * @param packet packet to send
     * @param selector selector for targets
     */
    private void sendPacket(Packet packet, ConnectionSelector selector){
        UUID[] uuids = register.selectInGame(selector);

        for (UUID uuid : uuids) {
            sendPacket(packet, uuid);
        }
    }


    // ----- Basic Handling -----

    @Override
    public void connected(UUID client) {
        register.connect(client);
    }

    @Override
    public void disconnected(UUID client) {

        if (register.isInGame(client)){
            game.removeClient(register.getGameId(client)); //TODO: Proper kick behaviour
        }

        register.disconnect(client);
    }


    // ----- Connection Packets -----

    public void handle(PingPacket packet, UUID id) {
        sendPacket(packet, id); // Just send Packet back, since it is a ping
    }

    public void handle(LoginPacket packet, UUID id){
        if (!Serint.CLIENT_VERSION.equals(packet.getVersion())){ // Check whether accepted version matches, if not disconnect it
            log.info("Client " + id + " tried to join with version " + packet.getVersion() + " but was rejected");
            sendPacket(new KickPaket("Client version does not match! Version " + Serint.VERSION + " expected."), id);
        } else {
            register.accept(id);
            sendPacket(new LoggedInPacket(Serint.VERSION, ConfigHandler.getConfig().getName(), ConfigHandler.getConfig().getDescription()), id);
        }
    }

    public void handle(JoinPacket packet, UUID id){
        if (register.isAccepted(id)){

            register.join(id);
            log.info("Client {} joined the game with id {}", id, register.getGameId(id));

            int playerId = game.initializeClient(register.getGameId(id), packet.getName(), packet.getColor());

            sendPacket(new JoinedPacket(playerId), id);

            game.spawnClient(register.getGameId(id));

        } else log.info("Client {} tried to join without being accepted!", id);
    }


    // ----- Gameplay Packets -----

    public void handle(PlayerLocationPacket packet, UUID id){
        if (register.isInGame(id)){
            game.updatePlayerLocation(register.getGameId(id), packet.getX(), packet.getY(), packet.getVelocityX(), packet.getVelocityY());
        }
    }

    public void handle(ItemCollectionPacket packet, UUID id){
        game.collectItem(packet.getItemId(), register.getGameId(id));
    }

    public void handle(PlayerAbsorbPacket packet, UUID id){
        game.attackPlayer(packet.getPlayerId(), register.getGameId(id));
    }


    // ----- Outgoing Data -----

    /**
     * This Method sends a Packet that creates a player on
     * @param player player to create
     * @param selector target
     */
    public void sendCreatePlayer(Player player, ConnectionSelector selector){
        sendPacket(new PlayerCreatePacket(player.getId(), player.getName(), player.getColor().getRGB()), selector);
    }

    /**
     * Sends the removal of a player
     * @param id id to remove
     * @param selector target
     */
    public void sendRemovePlayer(int id, ConnectionSelector selector){
        sendPacket(new PlayerDestroyPacket(id), selector);
    }

    /**
     * Sends an update of the position of the player
     * @param player player that has repositioned
     * @param selector target
     */
    public void sendPlayerLocation(Player player, ConnectionSelector selector){
        sendPacket(new PlayerLocationPacket(player.getId(), player.getLocation().getPosX(), player.getLocation().getPosY(), player.getLocation().getVelocityX(), player.getLocation().getVelocityY()), selector);
    }

    /**
     * Sends a player attributes
     * @param player player to send attributes for
     * @param selector target
     */
    public void sendPlayerAttributes(Player player, ConnectionSelector selector){
        sendPacket(new PlayerAttributePacket(player.getAttributes(), player.getId()), selector);
    }

    /**
     * Sends a map to the client
     * @param map map to send
     * @param selector target
     */
    public void sendMap(TileMap map, ConnectionSelector selector){
        sendPacket(new MapPacket(map.getName(), map), selector);
    }

    /**
     * Sends the creation of an item
     * @param item item to create
     * @param selector target
     */
    public void sendCreateItem(Item item, ConnectionSelector selector){
        sendPacket(new ItemCreatePacket(item.getType(), item.getId(), item.getLocation().getPosX(), item.getLocation().getPosY()), selector);
    }

    /**
     * Sends the destruction of an item
     * @param id id of said item
     * @param selector
     */
    public void sendRemoveItem(int id, ConnectionSelector selector) {
        sendPacket(new ItemDestroyPacket(id), selector);
    }

    /**
     * Kicks a player from the game
     * @param reason reason for kick
     * @param selector target
     */
    public void kickPlayer(String reason, ConnectionSelector selector) {
        sendPacket(new KickPaket(reason), selector);
    }
}
