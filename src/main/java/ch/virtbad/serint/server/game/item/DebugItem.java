package ch.virtbad.serint.server.game.item;

import ch.virtbad.serint.server.game.registers.PlayerAttributes;
import lombok.extern.slf4j.Slf4j;

/**
 * This item is an item used for debugging purposes
 * @author Virt
 */
@Slf4j
public class DebugItem extends Item {

    public DebugItem() {
        super(0);
    }

    @Override
    public void collect(PlayerAttributes attributes) {
        log.info("DebugItem has been collected!");
    }
}
