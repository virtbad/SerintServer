package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.AttributeModifier;
import ch.virtbad.serint.server.game.player.PlayerAttributes;
import ch.virtbad.serint.server.local.config.ConfigHandler;

public class SuperVisionItem extends Item {

    public SuperVisionItem() {
        super(6);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        attributes.addTemporaryAttribute(ConfigHandler.getConfig().getSuperDurationVision(), new AttributeModifier(0, ConfigHandler.getConfig().getSuperStepVision()));
    }
}
