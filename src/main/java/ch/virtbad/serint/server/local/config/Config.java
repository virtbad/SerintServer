package ch.virtbad.serint.server.local.config;

import lombok.Getter;

/**
 * This class contains all config attributes
 * @author Virt
 */
@Getter
public class Config {

    private int port = 17371;
    private String name = "Serint Server";
    private String description = "A default Serint Server!";

    private int tps = 20;

    private float baseSpeed = 4;
    private float maxSpeed = 8;
    private float baseVision = 4;
    private float maxVision = 8;
    private int baseHealth = 5;
    private int maxHealth = 5;

}
