package ch.virtbad.serint.server.game.registers;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents all attributes that a player has
 * @author Virt
 */
@Getter @Setter
public class PlayerAttributes {

    private int health;
    private float speed;
    private float vision;

}
