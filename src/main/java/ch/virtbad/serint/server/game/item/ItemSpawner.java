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

    public final float HEALTH_CHANGE = 0.2f;
    public final float VISION_CHANGE = 0.3f;
    public final float SPEED_CHANGE = 0.5f;
    public final float SUPER_CHANGE = 0.25f;

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


        Item item = null;

        float random = (float) Math.random();
        float superRandom = (float) Math.random();

        item = new SuperVisionItem();

        if (random <= HEALTH_CHANGE) {
            item = new HealthItem();
        } else if (random <= VISION_CHANGE + HEALTH_CHANGE) {
            if (superRandom <= SUPER_CHANGE) item = new SuperVisionItem();
            else item = new VisionItem();
        } else if (random <= SPEED_CHANGE + VISION_CHANGE + HEALTH_CHANGE) {
            if (superRandom <= SUPER_CHANGE) item = new SuperSpeedItem();
            else item = new SpeedItem();
        }
        item.getLocation().setPosX(action.getX());
        item.getLocation().setPosY(action.getY());

        item = register.getItem(register.createItem(item));
        com.sendCreateItem(item, ConnectionSelector.exclude());
    }

}
