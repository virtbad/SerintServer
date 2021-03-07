package ch.virtbad.serint.server.network.handling;

import lombok.Getter;

import java.util.UUID;

/**
 * @author Virt
 */
public class Connection {

    @Getter
    private UUID clientId;
    @Getter
    private boolean accepted;

    @Getter
    private boolean inGame = false;
    @Getter
    private int gameId;

    public Connection(UUID clientId) {
        this.clientId = clientId;
    }

    public void setInGame(int id){
        inGame = true;
        gameId = id;
    }

    public void setOutGame(){
        gameId = 0;
        inGame = false;
    }

    public void accept(){
        accepted = true;
    }
}
