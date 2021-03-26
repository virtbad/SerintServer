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
    private float baseVision = 4;
    private float baseHealth = 5;

}
