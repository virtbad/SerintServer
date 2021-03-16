package ch.virtbad.serint.server.game;

import ch.virtbad.serint.server.game.map.Map;
import ch.virtbad.serint.server.game.map.MapLoader;
import ch.virtbad.serint.server.network.Communications;

/**
 * @author Virt
 */
public class Game {

    private Communications communications;

    private java.util.Map<String, Map> maps;

    public Game(Communications communications) {
        this.communications = communications;
        communications.setGame(this);
    }

    public void init() {
        this.maps = new MapLoader().read();
    }

    public void init(String path) {
        this.maps = new MapLoader().read(path);
    }

}
