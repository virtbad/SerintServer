package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;
import ch.virtbad.serint.server.game.registers.PlayerAttributes;

/**
 * This packet is used to transmit new attributes
 * @author Virt
 */
public class PlayerAttributePacket extends Packet {

    private int player;
    private PlayerAttributes attributes;

    /**
     * Constructor
     * @param attributes attributes to transmit
     */
    public PlayerAttributePacket(PlayerAttributes attributes, int player) {
        super(34);

        this.attributes = attributes;
        this.player = player;
    }
}
