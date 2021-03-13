package ch.virtbad.serint.server.game.map;

import ch.virtbad.serint.server.local.FileHandler;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class MapLoader {
    public static final String DEFAULT_PATH = "map.json";

    /**
     * Read the map from the json file
     *
     * @return Map
     */
    public Map read() {
        return read(DEFAULT_PATH);
    }

    /**
     * Read the map from the json file
     *
     * @param path Path of the file to be read
     * @return Map
     */
    public Map read(String path) {
        InputStream stream = FileHandler.getFilesystemFile(path);
        if (stream == null) {
            log.error("Failed to load map from {}", path);
            return null;
        }
        return new Gson().fromJson(new InputStreamReader(stream), Map.class);
    }
}
