package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;
import ch.virtbad.serint.server.game.map.Map;
import lombok.Getter;

public class MapPacket extends Packet {

    @Getter
    private Map map;

    /**
     * Constructor
     *
     * @param map map instance
     */
    public MapPacket(Map map) {
        super(20);
        this.map = map;
    }
}
