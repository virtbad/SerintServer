package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.PlayerAttributes;

/**
 * @author Virt
 */
public class SpeedItem extends Item {

    public SpeedItem() {
        super(1);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.changeSpeed(1);
    }
}
