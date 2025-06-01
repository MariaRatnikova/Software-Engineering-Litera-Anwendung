import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains the business logic for searching and filtering books
 * as well as accessing reviews (ratings).
 */
public class Buchkatalog {

    // List of all available books in the catalog
    private final List<Buch> buecher;

    // Access to the data storage (e.g., loading/saving books and reviews from/to JSON)
    private final StorageService storage;

    /**
     * Constructor – loads books from the provided StorageService.
     *
     * @param storageService Service for data handling (e.g., JSON files)
     */
    public Buchkatalog(StorageService storageService) {
        this.storage = storageService;
        this.buecher = storageService.ladeBuecher(); // Load books on startup
    }

    /**
     * Searches for books by title.
     *
     * @param query Search text (case-insensitive)
     * @return List of books whose titles contain the search text
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
     * Searches for books by author.
     *
     * @param autor Search text for author
     * @return List of books whose author contains the search text
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
     * Searches for books by genre.
     *
     * @param genre Search text for genre
     * @return List of books where any genre matches the search text
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
     * Searches for books by publisher.
     *
     * @param verlag Search text for publisher
     * @return List of books whose publisher matches the search text
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
     * Returns a single book by its ID.
     *
     * @param id ID of the book
     * @return Book object or null if no book was found with that ID
     */
    public Buch buchDetails(String id) {
        return buecher.stream()
            .filter(b -> id != null && id.equals(b.getBookId()))
            .findFirst()
            .orElse(null);
    }

    /**
     * Adds a review to a book.
     *
     * @param id  Book ID
     * @param rez Review to be saved
     * @return true if the addition was successful, otherwise false
     */
    public boolean reviewHinzufuegen(String id, Rezension rez) {
        Buch buch = buchDetails(id);
        if (buch == null || rez == null) {
            return false;
        }

        rez.setBookId(id);                // Assign ID to the review
        storage.speichereRezension(rez);  // Save via StorageService
        return true;
    }

    /**
     * Returns all reviews for a given book.
     *
     * @param id Book ID
     * @return List of matching reviews
     */
    public List<Rezension> showRezensionen(String id) {
        return storage.ladeRezensionen().stream()
            .filter(r -> id.equals(r.getBookId()))
            .collect(Collectors.toList());
    }
    
    /**
     * Finds similar books based on genre matches.
     * A book is considered similar if it shares at least 3 genres with the given book.
     *
     * @param buch The reference book to find similar books for
     * @return List of books that are considered similar (at least 3 matching genres)
     */
    public List<Buch> aehnlich(Buch buch) {
        // Check if the given book is valid
        if (buch == null || buch.getGenres() == null || buch.getGenres().isEmpty()) {
            return new ArrayList<>();
        }

        // Genres of the given book (lowercase for case-insensitive comparison)
        List<String> buchGenres = buch.getGenres().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        // Minimum number of matching genres for "similarity"
        final int MIN_MATCHING_GENRES = 7;

        // Search for similar books in the catalog
        return buecher.stream()
                // Exclude the reference book itself
                .filter(b -> !b.getBookId().equals(buch.getBookId()))
                // Only consider books with genres
                .filter(b -> b.getGenres() != null && !b.getGenres().isEmpty())
                // Calculate number of matching genres
                .filter(b -> {
                    // Genres of the book to compare (lowercase)
                    List<String> otherGenres = b.getGenres().stream()
                            .map(String::toLowerCase)
                            .collect(Collectors.toList());
                    
                    // Count matching genres
                    long matchingGenres = buchGenres.stream()
                            .filter(otherGenres::contains)
                            .count();
                    
                    // Book is similar if at least MIN_MATCHING_GENRES match
                    return matchingGenres >= MIN_MATCHING_GENRES;
                })
                .collect(Collectors.toList());
    }
}
