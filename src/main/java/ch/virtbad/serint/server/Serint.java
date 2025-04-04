package ch.virtbad.serint.server;

import ch.virtbad.serint.server.game.Game;
import ch.virtbad.serint.server.local.CommandLineInterface;
import ch.virtbad.serint.server.local.Time;
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
    public static final String VERSION = "1.0";
    public static final String CLIENT_VERSION = "1.0";

    private NetworkHandler network;

    private Game game;

    private CommandLineInterface cli;

    /**
     * Creates the Main Class
     */
    public Serint() {
        Time.getSeconds(); // Intialize Start Time
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

        cli = new CommandLineInterface(communications, game);
        log.info("Starting CommandLineInterface");
        cli.start();
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
