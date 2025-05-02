import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for loading and storing books and reviews in JSON files.
 */
public class StorageService {

    // Datei mit deinen Büchern
    private static final String BOOKS_FILE   = "Bucher.json";
    // Datei für die Rezensions-Daten
    private static final String REVIEWS_FILE = "reviews.json";

    private final ObjectMapper objectMapper;

    public StorageService() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Lädt alle Bücher aus der JSON-Datei Bucher.json.
     *
     * @return Liste der Bücher oder leer, falls die Datei fehlt / fehlerhaft ist
     */
    public List<Buch> ladeBuecher() {
        File file = new File(BOOKS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(
                file,
                new TypeReference<List<Buch>>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lädt alle Rezensionen aus der JSON-Datei reviews.json.
     *
     * @return Liste der Rezensionen oder leer, falls die Datei fehlt / fehlerhaft ist
     */
    public List<Rezension> ladeRezensionen() {
        File file = new File(REVIEWS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(
                file,
                new TypeReference<List<Rezension>>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Speichert eine neue Rezension und schreibt die aktualisierte Liste zurück.
     *
     * @param rezension die zu speichernde Rezension
     */
    public void speichereRezension(Rezension rezension) {
        List<Rezension> rezensionen = ladeRezensionen();
        rezensionen.add(rezension);
        try {
            objectMapper.writeValue(
                new File(REVIEWS_FILE),
                rezensionen
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
