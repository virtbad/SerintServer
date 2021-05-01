package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.AttributeModifier;
import ch.virtbad.serint.server.game.player.PlayerAttributes;

public class SuperVisionItem extends Item {

    public SuperVisionItem() {
        super(6);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.addTemporaryAttribute(5, new AttributeModifier(0, 2.5f));
    }
}
