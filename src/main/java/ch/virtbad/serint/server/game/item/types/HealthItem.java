package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.PlayerAttributes;

public class HealthItem extends Item {

    public HealthItem() {
        super(3);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.changeHealth(1);
    }
}
