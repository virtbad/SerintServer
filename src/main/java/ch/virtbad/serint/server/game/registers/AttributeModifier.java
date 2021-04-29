package ch.virtbad.serint.server.game.registers;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a temporary modifier
 */
@Getter
public class AttributeModifier {

    /**
     * Creates a modifier
     * @param speed speed mod
     * @param vision vision mod
     */
    public AttributeModifier(float speed, float vision) {
        this.speed = speed;
        this.vision = vision;
    }

    @Setter
    private float expires;

    private float speed;
    private float vision;
}
