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

    // Weiterleitung der Methoden an den Katalog:

    /**
     * Sucht Bücher nach Titel.
     */
    @Override
    public List<Buch> buchsuche(String q) {
        return katalog.buchsuche(q);
    }

    /**
     * Sucht Bücher nach Autor.
     */
    @Override
    public List<Buch> sucheNachAutor(String q) {
        return katalog.sucheNachAutor(q);
    }

    /**
     * Sucht Bücher nach Genre.
     */
    @Override
    public List<Buch> sucheNachGenre(String q) {
        return katalog.sucheNachGenre(q);
    }

    /**
     * Sucht Bücher nach Verlag.
     */
    @Override
    public List<Buch> sucheNachVerlag(String q) {
        return katalog.sucheNachVerlag(q);
    }

    /**
     * Gibt die Details eines bestimmten Buches zurück.
     */
    @Override
    public Buch buchdetails(String id) {
        return katalog.buchDetails(id);
    }

    /**
     * Zeigt alle Rezensionen zu einem Buch an.
     */
    @Override
    public List<Rezension> showRezensionen(String id) {
        return katalog.showRezensionen(id);
    }

    /**
     * Fügt eine neue Rezension zu einem Buch hinzu.
     */
    @Override
    public void reviewHinzufuegen(String id, Rezension r) {
        katalog.reviewHinzufuegen(id, r);
    }
}