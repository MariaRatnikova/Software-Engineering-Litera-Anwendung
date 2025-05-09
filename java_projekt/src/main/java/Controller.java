import java.util.List;

/**
 * Dünne Steuerungsschicht, die den Buchkatalog über ein Interface nach außen bereitstellt.
 * Dient als Vermittler zwischen der Benutzeroberfläche und der internen Logik.
 */
public class Controller implements ApplicationInterface {

    // Zentrale Kataloginstanz mit Such- und Rezensionslogik
    private final Buchkatalog katalog;

    /**
     * Konstruktor – erstellt den Buchkatalog auf Basis des übergebenen StorageService.
     *
     * @param storage Dienst zum Laden/Speichern von Büchern und Rezensionen
     */
    public Controller(StorageService storage) {
        this.katalog = new Buchkatalog(storage);
    }

    /* === Weiterleitung der Methoden an den Katalog ======================= */

    /**
     * Sucht Bücher nach Titel.
     *
     * @param q Suchbegriff für Titel
     * @return Liste der passenden Bücher
     */
    @Override
    public List<Buch> buchsuche(String q) {
        return katalog.buchsuche(q);
    }

    /**
     * Sucht Bücher nach Autor.
     *
     * @param q Suchbegriff für Autor
     * @return Liste der passenden Bücher
     */
    @Override
    public List<Buch> sucheNachAutor(String q) {
        return katalog.sucheNachAutor(q);
    }

    /**
     * Sucht Bücher nach Genre.
     *
     * @param q Suchbegriff für Genre
     * @return Liste der passenden Bücher
     */
    @Override
    public List<Buch> sucheNachGenre(String q) {
        return katalog.sucheNachGenre(q);
    }

    /**
     * Sucht Bücher nach Verlag.
     *
     * @param q Suchbegriff für Verlag
     * @return Liste der passenden Bücher
     */
    @Override
    public List<Buch> sucheNachVerlag(String q) {
        return katalog.sucheNachVerlag(q);
    }

    /**
     * Gibt die Details eines bestimmten Buches zurück.
     *
     * @param id Buch-ID
     * @return Buchobjekt oder null
     */
    @Override
    public Buch buchdetails(String id) {
        return katalog.buchDetails(id);
    }

    /**
     * Zeigt alle Rezensionen zu einem Buch an.
     *
     * @param id Buch-ID
     * @return Liste der Rezensionen
     */
    @Override
    public List<Rezension> showRezensionen(String id) {
        return katalog.showRezensionen(id);
    }

    /**
     * Fügt eine neue Rezension zu einem Buch hinzu.
     *
     * @param id Buch-ID
     * @param r  Rezension, die hinzugefügt werden soll
     */
    @Override
    public void reviewHinzufuegen(String id, Rezension r) {
        katalog.reviewHinzufuegen(id, r);
    }
}
