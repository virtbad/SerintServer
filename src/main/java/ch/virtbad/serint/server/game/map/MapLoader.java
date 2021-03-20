package ch.virtbad.serint.server.game.map;

import ch.virtbad.serint.server.local.FileHandler;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the loading of maps
 */
@Slf4j
public class MapLoader {
    public static final String DEFAULT_PATH = "maps";

    private Map<String, TileMap> maps;

    /**
     * Creates a map loader
     */
    public MapLoader() {
        maps = new HashMap<>();
    }

    /**
     * Returns a map with the given name
     * @param name name of the map
     * @return map
     */
    public TileMap getMap(String name){
        if (!maps.containsKey(name)){
            log.error("Attempted to fetch nonexistent map with name {}", maps);

            return null;
        }

        return maps.get(name);
    }

    /**
     * Read maps from the default location
     */
    public void read() {
        read(DEFAULT_PATH);
    }

    /**
     * Read maps from a folder
     * @param path Path of the folder to be read
     */
    public void read(String path) {
        InputStream[] streams = FileHandler.getFilesystemFolderFiles(path);
        if (streams.length == 0) {
            log.warn("No map loaded from {}", path);
        }

        for (InputStream stream : streams) {
            TileMap map = new Gson().fromJson(new InputStreamReader(stream), TileMap.class);
            log.info("Loaded map {}", map.getName());
            maps.put(map.getName(), map);
        }

        log.info("Loaded {} maps from {}", streams.length, path);
    }
}
