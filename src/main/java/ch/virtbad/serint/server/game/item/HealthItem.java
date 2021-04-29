package ch.virtbad.serint.server.game.item;

import ch.virtbad.serint.server.game.registers.PlayerAttributes;

public class HealthItem extends Item {

    public HealthItem() {
        super(3);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.changeHealth(1);
    }
}
