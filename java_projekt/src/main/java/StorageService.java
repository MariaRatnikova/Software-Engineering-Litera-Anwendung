/*
 * StorageService.java
 * ------------------------------------------------------------------
 * Loads book data from a JSON file bundled in the JAR (resources) and
 * stores user reviews in an external JSON file next to the application.
 *
 * Book list  : src/main/resources/data/books_short.json → read-only
 * Reviews    : ./data/reviews.json                     → read/write
 *
 * Jackson (ObjectMapper) is used for (de)serialisation.
 */

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class StorageService {

    /* ------------------------------------------------------------------
     * Constants & Paths
     * ---------------------------------------------------------------- */
    private static final String BOOKS_RESOURCE = "data/books_short.json";
/** Directory where the running JAR (or classes) reside */
    private static final Path APP_DIR = resolveAppDirectory();

/** External data folder next to the executable */
    private static final Path DATA_DIR = APP_DIR.resolve("data");
    private static final Path REVIEWS_PATH = DATA_DIR.resolve("reviews.json");
    /* ------------------------------------------------------------------
     * Jackson mapper (reuse one instance)
     * ---------------------------------------------------------------- */
    private final ObjectMapper mapper = new ObjectMapper();

    /* ------------------------------------------------------------------
     * Public API
     * ---------------------------------------------------------------- */

    /** Reads all books packaged inside the JAR. */
    public List<Buch> ladeBuecher() {
        try (InputStream in = getClass()
                                   .getClassLoader()
                                   .getResourceAsStream(BOOKS_RESOURCE)) {

            if (in == null) {
                System.err.println("⚠️  Resource " + BOOKS_RESOURCE + " not found!");
                return new ArrayList<>();
            }
            return mapper.readValue(in, new TypeReference<List<Buch>>() {});
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /** Reads reviews from the external file (creates empty file if missing). */
    public List<Rezension> ladeRezensionen() {
        ensureReviewsFileExists();
        try {
            return mapper.readValue(REVIEWS_PATH.toFile(),
                                    new TypeReference<List<Rezension>>() {});
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /** Appends a new review and saves the full list back to disk. */
    public void speichereRezension(final Rezension review) {
        List<Rezension> all = ladeRezensionen();
        all.add(review);
        try {
            mapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(REVIEWS_PATH.toFile(), all);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /* ------------------------------------------------------------------
     * Helpers
     * ---------------------------------------------------------------- */

    /** Creates ./data/reviews.json with an empty array if it does not exist. */
    private static Path resolveAppDirectory() {
    try {
        Path codeSource = Paths.get(
            StorageService.class.getProtectionDomain()
                                .getCodeSource()
                                .getLocation()
                                .toURI());

        
        return Files.isRegularFile(codeSource)
               ? codeSource.getParent().getParent()    // JAR → target → java_projekt
               : codeSource.getParent().getParent();   // classes → target → java_projekt

    } catch (Exception e) {
        return Paths.get(System.getProperty("user.dir"));
    }
}
    private void ensureReviewsFileExists() {
        try {
            if (Files.notExists(DATA_DIR)) {
                Files.createDirectories(DATA_DIR);
            }
            if (Files.notExists(REVIEWS_PATH)) {
                Files.writeString(REVIEWS_PATH, "[]");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}