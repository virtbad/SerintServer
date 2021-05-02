package ch.virtbad.serint.server.game.item;

import ch.virtbad.serint.server.game.item.types.*;
import ch.virtbad.serint.server.game.map.TileMap;
import ch.virtbad.serint.server.local.Time;
import ch.virtbad.serint.server.local.config.ConfigHandler;
import ch.virtbad.serint.server.network.Communications;
import ch.virtbad.serint.server.network.handling.ConnectionSelector;

import java.util.ArrayList;
import java.util.List;

public class ItemSpawner {

    private ItemRegister register;

    private Communications com;

    private float nextItem;

    public ItemSpawner(Communications com, ItemRegister register) {
        this.com = com;
        this.register = register;
    }

    public void trySpawn(TileMap locations){
        if (Time.getSeconds() > nextItem){
            nextItem = Time.getSeconds() + (float) (Math.random() * (ConfigHandler.getConfig().getItemSpawnMax() - ConfigHandler.getConfig().getItemSpawnMin()) + ConfigHandler.getConfig().getItemSpawnMin());

            spawnItem(locations.selectRandomAction(TileMap.Action.ActionType.ITEM));
        }
    }

    public void spawnItem(TileMap.Action action) {
        for (Item item : register.getItems()) {
            if (item.getLocation().getPosX() == action.getX() && item.getLocation().getPosY() == action.getY()) return;

        }


        Item item;

        float random = (float) Math.random();
        float superRandom = (float) Math.random();

        if (random <= ConfigHandler.getConfig().getChanceVision()) { // Vision

            if (superRandom <= ConfigHandler.getConfig().getChanceSuperVision()) item = new SuperVisionItem();
            else item = new VisionItem();

        } else if (random <= ConfigHandler.getConfig().getChanceVision() + ConfigHandler.getConfig().getChanceSpeed()) { // Speed

            if (superRandom <= ConfigHandler.getConfig().getChanceSuperSpeed()) item = new SuperSpeedItem();
            else item = new SpeedItem();

        } else { // Health
          item = new HealthItem();
        }

        item.getLocation().setPosX(action.getX());
        item.getLocation().setPosY(action.getY());

        item = register.getItem(register.createItem(item));
        com.sendCreateItem(item, ConnectionSelector.exclude());
    }

}
