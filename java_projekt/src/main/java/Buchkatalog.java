import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse enthält die Geschäftslogik für das Durchsuchen und Filtern von Büchern
 * sowie den Zugriff auf Rezensionen (Bewertungen).
 */
public class Buchkatalog {

    // Liste aller verfügbaren Bücher im Katalog
    private final List<Buch> buecher;

    // Zugriff auf die Datenspeicherung (z. B. Bücher und Rezensionen aus JSON laden/speichern)
    private final StorageService storage;

    /**
     * Konstruktor – lädt Bücher aus dem übergebenen StorageService.
     *
     * @param storageService Dienst zur Datenhaltung (z. B. JSON-Dateien)
     */
    public Buchkatalog(StorageService storageService) {
        this.storage = storageService;
        this.buecher = storageService.ladeBuecher(); // Bücher beim Start laden
    }

    /**
     * Sucht nach Büchern anhand des Titels.
     *
     * @param query Suchtext (nicht case-sensitiv)
     * @return Liste der Bücher, deren Titel den Suchtext enthalten
     */
    public List<Buch> buchsuche(String query) {
        String q = query == null ? "" : query.trim().toLowerCase();
        if (q.isEmpty()) {
            return buecher;
        }

        return buecher.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(q))
            .collect(Collectors.toList());
    }

    /**
     * Sucht Bücher anhand des Autors.
     *
     * @param autor Suchtext für Autor
     * @return Liste der Bücher, bei denen der Autor den Suchtext enthält
     */
    public List<Buch> sucheNachAutor(String autor) {
        String q = autor == null ? "" : autor.trim().toLowerCase();
        if (q.isEmpty()) {
            return List.of();
        }

        return buecher.stream()
            .filter(b -> b.getAuthor() != null &&
                         b.getAuthor().toLowerCase().contains(q))
            .collect(Collectors.toList());
    }

    /**
     * Sucht Bücher anhand eines Genres.
     *
     * @param genre Suchtext für Genre
     * @return Liste der Bücher, deren Genre mit dem Suchtext übereinstimmt
     */
    public List<Buch> sucheNachGenre(String genre) {
        String q = genre == null ? "" : genre.trim().toLowerCase();
        if (q.isEmpty()) {
            return List.of();
        }

        return buecher.stream()
            .filter(b -> b.getGenres() != null &&
                         b.getGenres().stream()
                         .anyMatch(g -> g.toLowerCase().contains(q)))
            .collect(Collectors.toList());
    }

    /**
     * Sucht Bücher anhand des Verlags.
     *
     * @param verlag Suchtext für Verlag
     * @return Liste der Bücher, deren Verlag mit dem Suchtext übereinstimmt
     */
    public List<Buch> sucheNachVerlag(String verlag) {
        String q = verlag == null ? "" : verlag.trim().toLowerCase();
        if (q.isEmpty()) {
            return List.of();
        }

        return buecher.stream()
            .filter(b -> b.getPublisher() != null &&
                         b.getPublisher().toLowerCase().contains(q))
            .collect(Collectors.toList());
    }

    /**
     * Gibt ein einzelnes Buch anhand seiner ID zurück.
     *
     * @param id ID des Buches
     * @return Buch-Objekt oder null, wenn kein Buch mit dieser ID gefunden wurde
     */
    public Buch buchDetails(String id) {
        return buecher.stream()
            .filter(b -> id != null && id.equals(b.getBookId()))
            .findFirst()
            .orElse(null);
    }

    /**
     * Fügt eine Rezension einem Buch hinzu.
     *
     * @param id  Buch-ID
     * @param rez Rezension, die gespeichert werden soll
     * @return true, wenn das Hinzufügen erfolgreich war, sonst false
     */
    public boolean reviewHinzufuegen(String id, Rezension rez) {
        Buch buch = buchDetails(id);
        if (buch == null || rez == null) {
            return false;
        }

        rez.setBookId(id);           // ID wird der Rezension zugewiesen
        storage.speichereRezension(rez); // Speicherung über StorageService
        return true;
    }

    /**
     * Gibt alle Rezensionen zu einem bestimmten Buch zurück.
     *
     * @param id Buch-ID
     * @return Liste der passenden Rezensionen
     */
    public List<Rezension> showRezensionen(String id) {
        return storage.ladeRezensionen().stream()
            .filter(r -> id.equals(r.getBookId()))
            .collect(Collectors.toList());
    }
}
