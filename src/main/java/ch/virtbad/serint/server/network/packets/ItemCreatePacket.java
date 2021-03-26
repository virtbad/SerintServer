package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;

/**
 * @author Virt
 */
public class ItemCreatePacket extends Packet {

    private int itemType;
    private int itemId;

    private float x, y;


    public ItemCreatePacket(int itemType, int itemId, float x, float y) {
        super(21);

        this.itemType = itemType;
        this.itemId = itemId;
        this.x = x;
        this.y = y;
    }
}
