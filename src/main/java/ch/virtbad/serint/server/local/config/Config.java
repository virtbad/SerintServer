package ch.virtbad.serint.server.local.config;

import lombok.Getter;

/**
 * This class contains all config attributes
 * @author Virt
 */
@Getter
public class Config {

    // Runtime
    private int tps = 20;


    // Metadata
    private int port = 17371;
    private String name = "Serint Server";
    private String description = "A default Serint Server!";


    // Attributes
    // Spawning Chances
    private float chanceVision = 0.475f;
    private float chanceSpeed = 0.475f;
    private float chanceSuperVision = 0.5f;
    private float chanceSuperSpeed = 0.5f;

    // Speed Data
    private float baseSpeed = 3;
    private float maxSpeed = 8;
    private float stepSpeed = 1;
    private float superStepSpeed = 5;
    private float superDurationSpeed = 10;

    // Vision Data
    private float baseVision = 1.5f;
    private float maxVision = 6;
    private float stepVision = 0.5f;
    private float superStepVision = 10;
    private float superDurationVision = 10;

    // Health Data
    private int baseHealth = 5;
    private int maxHealth = 5;
    private int stepHealth = 1;

    // Spawn Time
    private float itemSpawnMin = 10;
    private float itemSpawnMax = 20;

    // Passing Rules
    private float keepIssuer = 0.5f;
    private float getIssuer = 0.0f;
    private float keepVictim = 0.0f;
    private float getVictim = 0.0f;


    // Time Control
    private float respawnTime = 5;
    private float winScreenTime = 10;
    private float startTime = 10;

    // Map Control
    private String gameMap = "Game";
    private String lobbyMap = "Lobby";

}
