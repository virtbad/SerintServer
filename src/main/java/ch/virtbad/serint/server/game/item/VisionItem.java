package ch.virtbad.serint.server.game.item;

import ch.virtbad.serint.server.game.registers.PlayerAttributes;

public class VisionItem extends Item {

    public VisionItem() {
        super(2);
    }


    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.changeVision(0.5f);
    }
}
