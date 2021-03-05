package ch.virtbad.serint.server.network;

import ch.virt.pseudopackets.handlers.ServerPacketHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * This class handles all packet traffic
 * @author Virt
 */
@Slf4j
public class Communications extends ServerPacketHandler {

    @Getter
    private static Communications instance;

    /**
     * Loads the communications
     */
    public static void load(){
        log.info("Creating the Communications");
        instance = new Communications();
    }

    /**
     * Creates the communication
     */
    public Communications(){

    }

    @Override
    public void connected(UUID client) {

    }

    @Override
    public void disconnected(UUID client) {

    }
}
