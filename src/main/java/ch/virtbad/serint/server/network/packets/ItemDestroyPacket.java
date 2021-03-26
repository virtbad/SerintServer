package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;

/**
 * @author Virt
 */
public class ItemDestroyPacket extends Packet {

    private int itemId;

    public ItemDestroyPacket(int itemId) {
        super(22);
        this.itemId = itemId;
    }
}
