package ch.virtbad.serint.server.game.item;

import ch.virtbad.serint.server.game.registers.PlayerAttributes;

/**
 * @author Virt
 */
public class SpeedItem extends Item {

    public SpeedItem() {
        super(1);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.setSpeed(attributes.getSpeed() + 5);

        // TODO: Make dynamic
    }
}
