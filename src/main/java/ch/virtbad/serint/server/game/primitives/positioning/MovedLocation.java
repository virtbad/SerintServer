package ch.virtbad.serint.server.game.primitives.positioning;

import lombok.Getter;
import lombok.Setter;

/**
 * This class stores positions that are moved
 * @author Virt
 */
public class MovedLocation extends FixedLocation {

    @Getter @Setter
    protected float velocityX, velocityY;

}
