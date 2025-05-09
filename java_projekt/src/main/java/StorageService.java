/*
 * StorageService.java
 * ------------------------------------------------------------------
 * Lädt und speichert Buch- und Rezensions­daten im JSON-Format.
 * Arbeitet mit Jackson (ObjectMapper), um Java-Objekte zu (de)serialisieren.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Dienstklasse für Dateioperationen: <br>
 * • lädt Bücher und Rezensionen aus JSON-Dateien<br>
 * • speichert neue Rezensionen persistent
 */
public final class StorageService {

    /* ------------------------------------------------------------------
     * Dateipfade
     * ---------------------------------------------------------------- */
    private static final String BOOKS_FILE   = "src/main/java/books_short.json";
    private static final String REVIEWS_FILE = "src/main/java/reviews.json";

    /* ------------------------------------------------------------------
     * Jackson ObjectMapper (wird wiederverwendet)
     * ---------------------------------------------------------------- */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /* ------------------------------------------------------------------
     * Öffentliche API
     * ---------------------------------------------------------------- */

    /**
     * Liest die Liste aller Bücher ein.
     *
     * @return Liste von {@link Buch}-Objekten (leer, falls Datei fehlt / Fehler)
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
     * Liest alle gespeicherten Rezensionen ein.
     *
     * @return Liste von {@link Rezension}-Objekten (leer, falls Datei fehlt / Fehler)
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
     * Persistiert eine neue Rezension.<br>
     * Vorhandene Rezensionen werden geladen, die neue wird hinzugefügt
     * und anschließend wird die komplette Liste zurückgeschrieben.
     *
     * @param review Rezension, die gespeichert werden soll
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
