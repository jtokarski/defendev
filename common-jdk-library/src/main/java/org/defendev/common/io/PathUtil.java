package org.defendev.common.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.String.format;



public class PathUtil {

    /**
     * Written when I wasn't sure how to escape "file separator" on Windows. Parsing a given path
     * with Path.of(), getting parent and verifying its existence gives some better confidence.
     *
     */
    public static Path outputFilePath(String rawPath, boolean createParentDirectories) {
        final Path path = Path.of(rawPath);
        final Path parentPath = path.getParent();
        if (Files.exists(parentPath)) {
            return path;
        } else if (createParentDirectories) {
            try {
                Files.createDirectories(parentPath);
                return path;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException(format("Parent directory does not exist for %s", rawPath));
        }
    }

    public static Path outputFilePath(String rawPath) {
        return outputFilePath(rawPath, false);
    }

}
