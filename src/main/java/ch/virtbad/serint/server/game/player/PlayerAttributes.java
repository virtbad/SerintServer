package ch.virtbad.serint.server.game.player;

import ch.virtbad.serint.server.local.Time;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents all attributes that a player has
 *
 * @author Virt
 */
public class PlayerAttributes {

    private List<AttributeModifier> temporaryAttributes;

    @Expose @Getter
    private int health;
    @Expose
    private int maxHealth;
    @Expose
    private float speed;
    @Expose
    private float boostedSpeed;
    @Expose
    private float maxSpeed;
    @Expose
    private float vision;
    @Expose
    private float boostedVision;
    @Expose
    private float maxVision;

    /**
     * Creates default player attributes according to the config
     */
    public PlayerAttributes() {
        health = ConfigHandler.getConfig().getBaseHealth();
        speed = ConfigHandler.getConfig().getBaseSpeed();
        vision = ConfigHandler.getConfig().getBaseVision();

        maxHealth = ConfigHandler.getConfig().getMaxHealth();
        maxSpeed = ConfigHandler.getConfig().getMaxSpeed();
        maxVision = ConfigHandler.getConfig().getMaxVision();

        this.temporaryAttributes = new ArrayList<>();
    }

    /**
     * Checks the temporary attributes
     * @return whether changed
     */
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

    /**
     * Changes the health of a player
     * @param amount amount to change by
     */
    public void changeHealth(int amount){
        health += amount;
        if (health > maxHealth) health = maxHealth;
        if (health < 0) health = 0;
    }

    /**
     * Changes the Speed
     * @param amount amount to change by
     */
    public void changeSpeed(float amount){
        speed += amount;
        if (speed > maxSpeed) speed = maxSpeed;
        if (speed < 0) speed = 0;
    }

    /**
     * Changes the Vision
     * @param amount amount to change by
     */
    public void changeVision(float amount){
        vision += amount;
        if (vision > maxVision) vision = maxVision;
        if (vision < 0) vision = 0;
    }

    /**
     * Removes a temporary modifier
     * @param modifier modifier to remove
     */
    public void removeTemporaryAttribute(AttributeModifier modifier) {
        temporaryAttributes.remove(modifier);

        boostedSpeed -= modifier.getSpeed();
        boostedVision -= modifier.getVision();
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

        boostedSpeed += attributeModifier.getSpeed();
        boostedVision += attributeModifier.getVision();
    }

    /**
     * Clears attributes except health
     */
    public void clearAttributes(){
        speed = ConfigHandler.getConfig().getBaseSpeed();
        vision = ConfigHandler.getConfig().getBaseVision();

        boostedVision = 0;
        boostedSpeed = 0;

        temporaryAttributes.clear();
    }

}
