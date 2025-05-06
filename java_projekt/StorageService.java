import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Dienstklasse für Dateioperationen:
 * Lädt und speichert Buch- und Rezensionsdaten im JSON-Format.
 * 
 * Arbeitet mit Jackson (ObjectMapper), um Java-Objekte in JSON zu konvertieren und umgekehrt.
 */
public class StorageService {

    // Pfad zur Datei mit Buchdaten (nur Kleinbuchstaben im Namen)
    private static final String BOOKS_FILE = "books_short.json";

    // Pfad zur Datei mit Rezensionen
    private static final String REVIEWS_FILE = "reviews.json";

    // Jackson-Objekt zur Verarbeitung von JSON
    private final ObjectMapper om = new ObjectMapper();

    /**
     * Lädt die Liste der Bücher aus der JSON-Datei.
     * Gibt eine leere Liste zurück, falls die Datei nicht existiert oder fehlerhaft ist.
     * 
     * @return Liste von Buch-Objekten
     */
    public List<Buch> ladeBuecher() {
        File f = new File(BOOKS_FILE);
        if (!f.exists()) return new ArrayList<>(); // Datei nicht vorhanden → leere Liste

        try {
            return om.readValue(f, new TypeReference<List<Buch>>() {});
        } catch (IOException ex) {
            ex.printStackTrace(); // Fehler beim Lesen
            return new ArrayList<>();
        }
    }

    /**
     * Lädt alle Rezensionen aus der JSON-Datei.
     * Gibt eine leere Liste zurück, wenn die Datei fehlt oder nicht lesbar ist.
     * 
     * @return Liste von Rezensionen
     */
    public List<Rezension> ladeRezensionen() {
        File f = new File(REVIEWS_FILE);
        if (!f.exists()) return new ArrayList<>();

        try {
            return om.readValue(f, new TypeReference<List<Rezension>>() {});
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Speichert eine neue Rezension.
     * Lädt zunächst bestehende Rezensionen, fügt die neue hinzu und speichert dann alles neu ab.
     * 
     * @param r Die neue Rezension, die gespeichert werden soll
     */
    public void speichereRezension(Rezension r) {
        List<Rezension> list = ladeRezensionen(); // Bestehende Rezensionen laden
        list.add(r);                               // Neue hinzufügen

        try {
            om.writerWithDefaultPrettyPrinter().writeValue(new File(REVIEWS_FILE), list);
        } catch (IOException ex) {
            ex.printStackTrace(); // Fehler beim Speichern
        }
    }
}