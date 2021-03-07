package ch.virtbad.serint.server.game;

import ch.virtbad.serint.server.network.Communications;

/**
 * @author Virt
 */
public class Game {

    private Communications communications;

    public Game(Communications communications){
        this.communications = communications;
        communications.setGame(this);
    }

}
