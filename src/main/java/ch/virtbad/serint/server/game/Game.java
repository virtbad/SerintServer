package ch.virtbad.serint.server.game;

import ch.virtbad.serint.server.game.registers.Player;
import ch.virtbad.serint.server.game.registers.PlayerRegister;
import ch.virtbad.serint.server.network.Communications;
import ch.virtbad.serint.server.network.handling.ConnectionSelector;

import java.awt.*;

/**
 * @author Virt
 */
public class Game {

    private Communications com;

    PlayerRegister register;

    public Game(Communications communications){
        this.com = communications;
        communications.setGame(this);

        register = new PlayerRegister();
    }

    public int initializeClient(int client, String name, int colour){
        // Get Map
        // Send Map
        // TODO: Fill in

        for (Player player : register.getPlayers()) {
            com.sendCreatePlayer(player, ConnectionSelector.include(client));
        }

        register.createPlayer(new Player(name, new Color(colour)), client);
        Player created = register.getPlayer(register.getPlayerId(client));

        com.sendCreatePlayer(created, ConnectionSelector.exclude(client));

        return register.getPlayerId(client);
    }

    public void removeClient(int id){
        com.sendRemovePlayer(register.getPlayerId(id), ConnectionSelector.exclude(id));

        register.removePlayer(register.getPlayerId(id));
    }

    public void updatePlayerLocation(int id, float x, float y, float velocityX, float velocityY){
        Player player = register.getPlayer(register.getPlayerId(id));

        player.getLocation().setPosX(x);
        player.getLocation().setPosY(y);
        player.getLocation().setVelocityX(velocityX);
        player.getLocation().setVelocityY(velocityY);

        com.sendPlayerLocation(player, ConnectionSelector.exclude(id));
    }




}
