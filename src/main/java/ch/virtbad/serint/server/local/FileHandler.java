package ch.virtbad.serint.server.local;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

/**
 * This class takes care of basic operations with files.
 * Part of this is copied over from SerintClient
 * @author Virt
 */
@Slf4j
public class FileHandler {

    /**
     * Returns the InputStream of a file contained in a classpath
     * @param path path of resource in the classpath
     * @return InputStream to be processed
     */
    public static InputStream getClasspathFile(String path){
        log.debug("Fetching Classpath File: {}", path);
        return InputStream.class.getResourceAsStream(path);
    }

    /**
     * Returns the InputStream of a file contained in a directory on the file system
     * @param path path to fetch from
     * @return Stream of file
     */
    public static InputStream getFilesystemFile(String path){
        log.debug("Fetching Filesystem File: {}", path);
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            log.debug("Failed to fetch Filesystem File {}", path);
            return null;
        }
    }

    /**
     * Returns the InputStream as Text
     * @param resource InputStream to load from
     * @return String if loaded correctly, null if not able to load
     */
    public static String getText(InputStream resource){
        if (resource == null) return null;

        Scanner scanner = new Scanner(resource);
        scanner.useDelimiter("\\A");
        return scanner.next();
    }
}
