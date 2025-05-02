import java.util.List;

/**
 * Interface für die Anwendungsschicht des Bücherkatalogs.
 * Alle Methoden arbeiten mit bookId als String, passend zur JSON-Struktur.
 */
public interface ApplicationInterface {

    /**
     * Sucht nach Büchern anhand des Titels (Teilstring, case-insensitive).
     * @param titel Teilstring des Titels
     * @return Liste der passenden Bücher
     */
    List<Buch> buchsuche(String titel);

    /**
     * Sucht nach Büchern eines bestimmten Autors (Teilstring, case-insensitive).
     * @param autorName Name oder Teilname des Autors
     * @return Liste der passenden Bücher
     */
    List<Buch> sucheNachAutor(String autorName);

    /**
     * Sucht nach Büchern eines bestimmten Genres (Teilstring, case-insensitive).
     * @param genreName Name oder Teilname des Genres
     * @return Liste der passenden Bücher
     */
    List<Buch> sucheNachGenre(String genreName);

    /**
     * Sucht nach Büchern eines bestimmten Verlags (Teilstring, case-insensitive).
     * @param verlagName Name oder Teilname des Verlags
     * @return Liste der passenden Bücher
     */
    List<Buch> sucheNachVerlag(String verlagName);

    /**
     * Gibt die Details für ein Buch anhand seiner bookId zurück.
     * @param bookId Eindeutige Buch-ID als String
     * @return Das Buch-Objekt oder null, falls nicht gefunden
     */
    Buch buchdetails(String bookId);

    /**
     * Zeigt alle Rezensionen für ein bestimmtes Buch an.
     * @param bookId Eindeutige Buch-ID als String
     * @return Liste mit Rezensionen
     */
    List<Rezension> showRezensionen(String bookId);

    /**
     * Fügt eine Rezension zu einem Buch hinzu.
     * @param bookId Eindeutige Buch-ID als String
     * @param rezension Die hinzuzufügende Rezension
     */
    void reviewHinzufuegen(String bookId, Rezension rezension);
}
