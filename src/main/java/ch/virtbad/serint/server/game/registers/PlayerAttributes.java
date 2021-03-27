package ch.virtbad.serint.server.game.registers;

import ch.virtbad.serint.server.local.config.ConfigHandler;
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

    /**
     * Creates default player attributes according to the config
     */
    public PlayerAttributes(){
        health = ConfigHandler.getConfig().getBaseHealth();
        speed = ConfigHandler.getConfig().getBaseSpeed();
        vision = ConfigHandler.getConfig().getBaseVision();
    }

}
