package ch.virtbad.serint.server.network;

import ch.virt.pseudopackets.packets.Protocol;
import lombok.Getter;

/**
 * This class wraps the Protocol for its creation
 * @author Virt
 */
public class ProtocolWrapper {

    @Getter
    private final Protocol protocol;

    /**
     * Initializes a ProtocolWrapper and registers the packets
     */
    public ProtocolWrapper(){
        protocol = new Protocol();

        registerPackets();
    }

    /**
     * This method calls all register Packet methods to insert packets into the protocol.
     * Every Packet should be added here
     */
    private void registerPackets(){
        // Register Packets here


    }
}
