package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;
import lombok.Getter;

/**
 * Packet sent to player when respawned
 */
@Getter
public class RespawnPacket extends Packet {

    /**
     * Constructor
     */
    public RespawnPacket() {
        super(53);
    }
}
