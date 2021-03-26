package ch.virtbad.serint.server;

import ch.virtbad.serint.server.game.Game;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import ch.virtbad.serint.server.network.Communications;
import ch.virtbad.serint.server.network.NetworkHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is basically the main class. In it, all major things get initialized and loaded
 *
 * @author Virt
 */
@Slf4j
public class Serint {
    public static final String VERSION = "0.1-alpha";
    public static final String CLIENT_VERSION = "0.1-alpha";

    private NetworkHandler network;

    private Game game;

    /**
     * Creates the Main Class
     */
    public Serint() {
        log.info("Starting Server version {}", VERSION);

        init();
        create();
        post();
    }

    /**
     * Initializes minor Things
     */
    public void init() {
        log.info("Initializing key components");

        // Load Config
        ConfigHandler.load(ConfigHandler.PATH);
    }

    /**
     * Creates major things
     */
    public void create() {
        log.info("Creating Server components");

        // Creating Network Handler
        network = new NetworkHandler();

        // Initiating Game
        Communications communications = network.createServer(new Communications());
        game = new Game(communications);
    }

    /**
     * Cleans up the creation process
     */
    public void post() {
        log.info("Cleaning current Instance");

        while (true) { // TODO: Add breakpoints
            game.getUpdater().call();
        }
    }
}
