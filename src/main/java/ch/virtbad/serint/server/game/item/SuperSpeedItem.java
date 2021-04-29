package ch.virtbad.serint.server.game.item;

import ch.virtbad.serint.server.game.registers.AttributeModifier;
import ch.virtbad.serint.server.game.registers.PlayerAttributes;

public class SuperSpeedItem extends Item {
    public final int DURATION = 5;


    public SuperSpeedItem() {
        super(5);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.addTemporaryAttribute(5, new AttributeModifier(5, 0));
    }
}
