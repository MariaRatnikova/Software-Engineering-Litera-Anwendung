/*
 * StorageService.java
 * ------------------------------------------------------------------
 * Loads and saves book and review data in JSON format.
 * Uses Jackson (ObjectMapper) to (de)serialize Java objects.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service class for file operations: <br>
 * • loads books and reviews from JSON files<br>
 * • persistently saves new reviews
 */
public final class StorageService {

    /* ------------------------------------------------------------------
     * File paths
     * ---------------------------------------------------------------- */
    private static final String BOOKS_FILE   = "src/main/java/books_short.json";
    private static final String REVIEWS_FILE = "src/main/java/reviews.json";

    /* ------------------------------------------------------------------
     * Jackson ObjectMapper (reused instance)
     * ---------------------------------------------------------------- */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /* ------------------------------------------------------------------
     * Public API
     * ---------------------------------------------------------------- */

    /**
     * Reads the list of all books.
     *
     * @return List of {@link Buch} objects (empty if file is missing / error)
     */
    public List<Buch> ladeBuecher() {
        File file = new File(BOOKS_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, new TypeReference<List<Buch>>() {});
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Reads all stored reviews.
     *
     * @return List of {@link Rezension} objects (empty if file is missing / error)
     */
    public List<Rezension> ladeRezensionen() {
        File file = new File(REVIEWS_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, new TypeReference<List<Rezension>>() {});
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Persists a new review.<br>
     * Existing reviews are loaded, the new one is added,
     * and then the full list is written back.
     *
     * @param review Review to be saved
     */
    public void speichereRezension(final Rezension review) {
        List<Rezension> allReviews = ladeRezensionen();
        allReviews.add(review);

        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(new File(REVIEWS_FILE), allReviews);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
