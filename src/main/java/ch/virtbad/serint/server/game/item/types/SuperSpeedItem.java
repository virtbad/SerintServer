package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.AttributeModifier;
import ch.virtbad.serint.server.game.player.PlayerAttributes;
import ch.virtbad.serint.server.local.config.ConfigHandler;

public class SuperSpeedItem extends Item {
    public final int DURATION = 5;


    public SuperSpeedItem() {
        super(5);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.addTemporaryAttribute(ConfigHandler.getConfig().getSuperDurationSpeed(), new AttributeModifier(ConfigHandler.getConfig().getSuperStepSpeed(), 0));
    }
}
