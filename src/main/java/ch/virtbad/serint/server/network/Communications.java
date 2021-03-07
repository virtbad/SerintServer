package ch.virtbad.serint.server.network;

import ch.virt.pseudopackets.handlers.ServerPacketHandler;
import ch.virt.pseudopackets.packets.Packet;
import ch.virt.pseudopackets.server.Server;
import ch.virtbad.serint.server.Serint;
import ch.virtbad.serint.server.game.Game;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import ch.virtbad.serint.server.network.handling.ConnectionRegister;
import ch.virtbad.serint.server.network.packets.KickPaket;
import ch.virtbad.serint.server.network.packets.LoggedInPacket;
import ch.virtbad.serint.server.network.packets.LoginPacket;
import ch.virtbad.serint.server.network.packets.PingPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * This class handles all packet traffic
 * @author Virt
 */
@Slf4j
public class Communications extends ServerPacketHandler {

    @Setter private Server server; // Is getting set automatically after server creation
    @Setter private Game game; // Same as server

    private final ConnectionRegister register;

    /**
     * Creates the communication
     */
    public Communications(){
        register = new ConnectionRegister();
    }

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

    @Override
    public void connected(UUID client) {
        register.connect(client);
    }

    @Override
    public void disconnected(UUID client) {
        register.disconnect(client);
    }

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

}
