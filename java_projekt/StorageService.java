import java.util.ArrayList;
import java.util.List; // Added for potential implementation

// Corrected method signatures and added basic implementation structure
public class StorageService {

    /**
     * Lädt die Liste der Bücher aus einer Datenquelle (z.B. Datei, Datenbank).
     * Muss in einer konkreten Implementierung überschrieben werden.
     * @return Eine Liste von Buch-Objekten.
     */
    public List<Buch> ladeBuecher() {
        // In einer echten Implementierung würden hier Daten geladen.
        // Gibt vorerst eine leere Liste zurück oder wirft eine Exception.
        System.out.println("WARNUNG: StorageService.ladeBuecher() ist nicht implementiert und gibt eine leere Liste zurück.");
        return new ArrayList<>(); 
        // Alternativ: throw new UnsupportedOperationException("ladeBuecher() muss implementiert werden");
    }

    /**
     * Lädt die Liste der Rezensionen aus einer Datenquelle.
     * Muss in einer konkreten Implementierung überschrieben werden.
     * @return Eine Liste von Rezension-Objekten.
     */
    public List<Rezension> ladeRezensionen() {
        // In einer echten Implementierung würden hier Daten geladen.
        System.out.println("WARNUNG: StorageService.ladeRezensionen() ist nicht implementiert und gibt eine leere Liste zurück.");
        return new ArrayList<>();
        // Alternativ: throw new UnsupportedOperationException("ladeRezensionen() muss implementiert werden");
    }

    /**
     * Speichert eine einzelne Rezension in einer Datenquelle.
     * Muss in einer konkreten Implementierung überschrieben werden.
     * @param rezension Das zu speichernde Rezension-Objekt.
     */
    public void speichereRezension(Rezension rezension) {
        // In einer echten Implementierung würden hier Daten gespeichert.
        System.out.println("WARNUNG: StorageService.speichereRezension() ist nicht implementiert. Rezension nicht gespeichert: " + rezension);
        // Alternativ: throw new UnsupportedOperationException("speichereRezension() muss implementiert werden");
    }
}