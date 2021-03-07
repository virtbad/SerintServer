package ch.virtbad.serint.server.network;

import ch.virt.pseudopackets.handlers.ServerPacketHandler;
import ch.virt.pseudopackets.server.Server;
import ch.virtbad.serint.server.game.Game;
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

    @Setter
    private Server server; // Is getting set automatically after server creation
    @Setter
    private Game game; // Same as server

    /**
     * Creates the communication
     */
    public Communications(){

    }

    @Override
    public void connected(UUID client) {
        log.info("Client has connected!");
    }

    @Override
    public void disconnected(UUID client) {

    }

    public void handle(PingPacket packet, UUID id) {
        server.sendPacket(packet, id); // Just send Packet back, since it is a ping
    }

}
