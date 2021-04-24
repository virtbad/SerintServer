package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;

/**
 * This packet is sent when the initiating handshake fails, thus logging the client out
 */
public class LoggedOutPacket extends Packet {
    private String reason;

    /**
     * Constructor
     */
    public LoggedOutPacket(String reason) {
        super(4);
        this.reason = reason;
    }
}
