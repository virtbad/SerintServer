package ch.virtbad.serint.server.game.item.types;

import ch.virtbad.serint.server.game.item.Item;
import ch.virtbad.serint.server.game.player.PlayerAttributes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuperDebugItem extends Item {

    public SuperDebugItem() {
        super(4);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        log.info("SuperDebugItem has been collected!");
    }
}
