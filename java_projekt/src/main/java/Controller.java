import java.util.List;

/**
 * Thin control layer that exposes the book catalog via the
 * {@link ApplicationInterface}.  Simply forwards every UI-request
 * to the underlying {@link Buchkatalog}.
 */
public class Controller implements ApplicationInterface {

    /** Central catalog instance (search, reviews, similarity …) */
    private final Buchkatalog katalog;

    /**
     * @param storage abstraction that loads/saves JSON data
     */
    public Controller(StorageService storage) {
        this.katalog = new Buchkatalog(storage);
    }

    /* ------------------------------------------------------------
     * Book search
     * ----------------------------------------------------------- */

    @Override public List<Buch> buchsuche(String q)        { return katalog.buchsuche(q); }
    @Override public List<Buch> sucheNachAutor(String q)   { return katalog.sucheNachAutor(q); }
    @Override public List<Buch> sucheNachGenre(String q)   { return katalog.sucheNachGenre(q); }
    @Override public List<Buch> sucheNachVerlag(String q)  { return katalog.sucheNachVerlag(q); }

    /* ------------------------------------------------------------
     * Details & reviews
     * ----------------------------------------------------------- */

    @Override public Buch buchdetails(String id)                  { return katalog.buchDetails(id); }
    @Override public List<Rezension> showRezensionen(String id)   { return katalog.showRezensionen(id); }
    @Override public void reviewHinzufuegen(String id, Rezension r){ katalog.reviewHinzufuegen(id, r); }

    /* ------------------------------------------------------------
     * Similar books
     * ----------------------------------------------------------- */

    /**
     * Returns books that are similar (≥3 matching genres)
     * to the reference book with the given ID.
     */
    @Override
    public List<Buch> aehnlich(String bookId) {
        Buch ref = katalog.buchDetails(bookId);
        if (ref == null) {
            return List.of();          // no such book → empty list
        }
        return katalog.aehnlich(ref);
    }
}
