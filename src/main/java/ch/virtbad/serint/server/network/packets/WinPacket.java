package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;

/**
 * Sent to the player when one wins
 */
public class WinPacket extends Packet {

    /**
     * Constructor
     */
    public WinPacket() {
        super(54);
    }
}
