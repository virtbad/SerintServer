package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.PlayerAttributes;
import ch.virtbad.serint.server.local.config.ConfigHandler;

public class HealthItem extends Item {

    public HealthItem() {
        super(3);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.changeHealth(ConfigHandler.getConfig().getStepHealth());
    }
}
