package ch.virtbad.serint.server.game.map;

import ch.virtbad.serint.server.local.FileHandler;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

@Slf4j
public class MapLoader {
    public static final String DEFAULT_PATH = "maps";

    /**
     * Read the map from the json file
     *
     * @return Array with all loaded maps
     */
    public java.util.Map<String, Map> read() {
        return read(DEFAULT_PATH);
    }

    /**
     * Read the map from the json file
     *
     * @param path Path of the file to be read
     * @return Array with all loaded maps
     */

    public java.util.Map<String, Map> read(String path) {
        InputStream[] streams = FileHandler.getFilesystemFolderFiles(path);
        if (streams.length == 0) {
            log.error("No map loaded from {}", path);
            return null;
        }
        java.util.Map<String, Map> maps = new HashMap<>();
        for (InputStream stream : streams) {
            Map map = new Gson().fromJson(new InputStreamReader(stream), Map.class);
            maps.put(map.getName(), map);
        }
        log.info("Loaded {} maps from {}", streams.length, path);
        return maps;
    }
}
