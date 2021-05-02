package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;

public class NotJoinedPacket extends Packet {
    private String reason;

    /**
     * Constructor
     */
    public NotJoinedPacket(String reason) {
        super(12);
        this.reason = reason;
    }
}
