package ch.virtbad.serint.server.network;

import ch.virt.pseudopackets.server.Server;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handles major networking stuff
 * @author Virt
 */
@Slf4j
public class NetworkHandler {

    private Server server;

    /**
     * Initializes the NetworkHandler and its components
     */
    public NetworkHandler(){
        createServer();
    }

    /**
     * Creates the protocol and Server
     */
    private void createServer(){

        log.info("Creating and Starting server on Port {}", ConfigHandler.getConfig().getPort());
        server = new Server(new ProtocolWrapper().getProtocol(), Communications.getInstance(), ConfigHandler.getConfig().getPort());

    }
}
