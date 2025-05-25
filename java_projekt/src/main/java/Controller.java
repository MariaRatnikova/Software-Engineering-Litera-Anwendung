import java.util.List;

/**
 * Thin control layer that exposes the book catalog via an interface.
 * Acts as a mediator between the user interface and internal logic.
 */
public class Controller implements ApplicationInterface {

    // Central catalog instance with search and review logic
    private final Buchkatalog katalog;

    /**
     * Constructor – creates the book catalog based on the provided storage service.
     *
     * @param storage Service for loading/saving books and reviews
     */
    public Controller(StorageService storage) {
        this.katalog = new Buchkatalog(storage);
    }

    /* === Forwarding methods to the catalog ======================= */

    /**
     * Searches books by title.
     *
     * @param q Search term for title
     * @return List of matching books
     */
    @Override
    public List<Buch> buchsuche(String q) {
        return katalog.buchsuche(q);
    }

    /**
     * Searches books by author.
     *
     * @param q Search term for author
     * @return List of matching books
     */
    @Override
    public List<Buch> sucheNachAutor(String q) {
        return katalog.sucheNachAutor(q);
    }

    /**
     * Searches books by genre.
     *
     * @param q Search term for genre
     * @return List of matching books
     */
    @Override
    public List<Buch> sucheNachGenre(String q) {
        return katalog.sucheNachGenre(q);
    }

    /**
     * Searches books by publisher.
     *
     * @param q Search term for publisher
     * @return List of matching books
     */
    @Override
    public List<Buch> sucheNachVerlag(String q) {
        return katalog.sucheNachVerlag(q);
    }

    /**
     * Returns the details of a specific book.
     *
     * @param id Book ID
     * @return Book object or null
     */
    @Override
    public Buch buchdetails(String id) {
        return katalog.buchDetails(id);
    }

    /**
     * Shows all reviews for a specific book.
     *
     * @param id Book ID
     * @return List of reviews
     */
    @Override
    public List<Rezension> showRezensionen(String id) {
        return katalog.showRezensionen(id);
    }

    /**
     * Adds a new review to a book.
     *
     * @param id Book ID
     * @param r  Review to be added
     */
    @Override
    public void reviewHinzufuegen(String id, Rezension r) {
        katalog.reviewHinzufuegen(id, r);
    }
}

