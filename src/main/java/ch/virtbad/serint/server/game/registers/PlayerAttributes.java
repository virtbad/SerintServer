package ch.virtbad.serint.server.game.registers;

import ch.virtbad.serint.server.local.Time;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents all attributes that a player has
 *
 * @author Virt
 */
@Getter
@Setter
public class PlayerAttributes {

    private List<AttributeModifier> temporaryAttributes;

    @Expose
    private int health;
    @Expose
    private float speed;
    @Expose
    private float vision;

    /**
     * Creates default player attributes according to the config
     */
    public PlayerAttributes() {
        health = ConfigHandler.getConfig().getBaseHealth();
        speed = ConfigHandler.getConfig().getBaseSpeed();
        vision = ConfigHandler.getConfig().getBaseVision();

        this.temporaryAttributes = new ArrayList<>();
    }

    public boolean checkAttributes() {
        boolean changed = false;
        for (int i = 0; i < temporaryAttributes.size(); i++) {
            AttributeModifier modifier = temporaryAttributes.get(i);

            if (modifier.getExpires() <= Time.getSeconds()) {
                removeTemporaryAttribute(modifier);
                changed = true;
            }
        }
        return changed;
    }

    public void removeTemporaryAttribute(AttributeModifier modifier) {
        temporaryAttributes.remove(modifier);

        health -= modifier.getHealth();
        speed -= modifier.getSpeed();
        vision -= modifier.getVision();
    }

    /**
     * Adds a temporary attribute to the player
     *
     * @param seconds           seconds the attribute will be applied
     * @param attributeModifier modifier that will be applied
     */
    public void addTemporaryAttribute(int seconds, AttributeModifier attributeModifier) {
        attributeModifier.setExpires(Time.getSeconds() + seconds);
        this.temporaryAttributes.add(attributeModifier);

        health += attributeModifier.getHealth();
        speed += attributeModifier.getSpeed();
        vision += attributeModifier.getVision();
    }

}
