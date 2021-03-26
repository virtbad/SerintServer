package ch.virtbad.serint.server.game.item;

import ch.virtbad.serint.server.game.primitives.positioning.FixedLocation;
import ch.virtbad.serint.server.game.registers.PlayerAttributes;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the base class for an item
 * @author Virt
 */
public abstract class Item {

    @Getter
    private int type;
    @Getter @Setter
    private FixedLocation location;

    /**
     * Initializes an item setting its type and position
     * @param type type of the idem
     */
    public Item(int type) {
        this.type = type;
        location = new FixedLocation();
    }

    /**
     * This method triggers the collection of an item
     * @param attributes attributes to apply to
     */
    public abstract void collect(PlayerAttributes attributes);

}
