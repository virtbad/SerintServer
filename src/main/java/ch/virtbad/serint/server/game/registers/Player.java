package ch.virtbad.serint.server.game.registers;

import ch.virtbad.serint.server.game.primitives.positioning.MovedLocation;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * This class stores information states about one player
 * @author Virt
 */
@Getter @Setter
public class Player {
    private int id;

    private String name;
    private Color color;

    private PlayerAttributes attributes;

    private MovedLocation location;

    /**
     * Creates a player
     * @param name name of the player
     * @param color colour of the player
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;

        location = new MovedLocation();
        attributes = new PlayerAttributes();
    }
}
