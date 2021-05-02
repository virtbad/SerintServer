package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.PlayerAttributes;
import ch.virtbad.serint.server.local.config.ConfigHandler;

/**
 * @author Virt
 */
public class SpeedItem extends Item {

    public SpeedItem() {
        super(1);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.changeSpeed(ConfigHandler.getConfig().getStepSpeed());
    }
}
