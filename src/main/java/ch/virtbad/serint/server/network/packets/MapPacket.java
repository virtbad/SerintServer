package ch.virtbad.serint.server.network.packets;

import ch.virt.pseudopackets.packets.Packet;
import ch.virtbad.serint.server.game.map.Map;
import lombok.Getter;

@Getter
public class MapPacket extends Packet {
    private String name;
    private Map map;

    /**
     * Constructor
     *
     * @param map  map instance
     * @param name name of the map e.g. "Lobby"
     */
    public MapPacket(String name, Map map) {
        super(20);
        this.name = name;
        this.map = map;
    }
}
