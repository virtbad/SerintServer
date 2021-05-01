package ch.virtbad.serint.server.game.item;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all items
 * @author Virt
 */
@Slf4j
public class ItemRegister {

    private final Map<Integer, Item> items;
    private int lastId;

    /**
     * Creates a register
     */
    public ItemRegister() {
        items = new HashMap<>();
    }

    /**
     * Adds an item to the register
     * @param item item to add
     * @return id of the added item
     */
    public int createItem(Item item){
        int id = ++lastId;
        log.info("Adding Item {} with Type {} at {},{}", id, item.getType(), item.getLocation().getPosX(), item.getLocation().getPosY());

        item.setId(id);
        items.put(id, item);

        return id;
    }

    public boolean has(int id){
        return items.containsKey(id);
    }

    /**
     * Removes the items from the register
     * @param id id of the item
     */
    public Item removeItem(int id){
        return items.remove(id);
    }

    /**
     * Returns an item
     * @param id id of the item
     * @return item to get
     */
    public Item getItem(int id){
        return items.get(id);
    }

    /**
     * Returns all items in the register
     * @return items
     */
    public Item[] getItems(){
        return items.values().toArray(new Item[0]);
    }

    /**
     * Removes all items
     */
    public void removeAll(){
        items.clear();
    }
}
