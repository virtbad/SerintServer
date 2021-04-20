package ch.virtbad.serint.server.game.registers;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AttributeModifier {
    public AttributeModifier(int health, float speed, float vision) {
        this.health = health;
        this.speed = speed;
        this.vision = vision;
    }

    @Setter
    private float expires;

    private int health;
    private float speed;
    private float vision;
}
