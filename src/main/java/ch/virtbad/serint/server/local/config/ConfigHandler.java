package ch.virtbad.serint.server.local.config;

import ch.virtbad.serint.server.local.FileHandler;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class handles loading the config
 * @author Virt
 */
@Slf4j
public class ConfigHandler {
    public static final String PATH = "config.json";

    private static Config config;

    /**
     * Creates a ConfigHandler.
     * (basically just loads it with a default path or a custom one)
     * @param customPath custom path to load from, may be null
     */
    public ConfigHandler(String customPath) {
        load(customPath == null ? PATH : customPath);
    }

    /**
     * Loads the config
     * @param path path of the config to load
     */
    public static void load(String path){
        log.info("Loading Config from {}", path);

        InputStream file = FileHandler.getFilesystemFile(path);
        if (file == null) {
            log.warn("Failed to load Config file");
            config = new Config();
        }
        else config = new Gson().fromJson(new InputStreamReader(file), Config.class);
    }

    /**
     * Returns a instance of the config to access
     * @return config
     */
    public static Config getConfig(){
        return config;
    }
}
