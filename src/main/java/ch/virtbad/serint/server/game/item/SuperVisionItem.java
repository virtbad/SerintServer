package ch.virtbad.serint.server.game.item;

import ch.virtbad.serint.server.game.registers.AttributeModifier;
import ch.virtbad.serint.server.game.registers.PlayerAttributes;

public class SuperVisionItem extends Item {

    public SuperVisionItem() {
        super(6);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.addTemporaryAttribute(5, new AttributeModifier(0, 2.5f));
    }
}
