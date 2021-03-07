package ch.virtbad.serint.server.network;

import ch.virt.pseudopackets.server.Server;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * This class handles major networking stuff
 * @author Virt
 */
@Slf4j
public class NetworkHandler {

    private Server server;
    private Communications communications;

    /**
     * Initializes the NetworkHandler and its components
     */
    public NetworkHandler(){

    }

    /**
     * Creates the protocol and Server
     */
    public Communications createServer(Communications newComms){

        communications = newComms;

        log.info("Creating and Starting server on Port {}", ConfigHandler.getConfig().getPort());
        server = new Server(new ProtocolWrapper().getProtocol(), communications, ConfigHandler.getConfig().getPort());

        communications.setServer(server);

        return communications;
    }
}
