package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.PlayerAttributes;

public class VisionItem extends Item {

    public VisionItem() {
        super(2);
    }


    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.changeVision(0.5f);
    }
}
