/*
 * ApplicationInterface.java
 * ------------------------------------------------------------------
 * Central interface of the business logic.
 */

import java.util.List;

/**
 * Defines all operations that the presentation layer performs with the business logic.
 * <p>
 * Book search → details → add or display reviews.
 */
public interface ApplicationInterface {

    /* ------------------------------------------------------------------
     * Book search
     * ---------------------------------------------------------------- */

    /**
     * Searches for books whose titles contain the given string.
     *
     * @param titel Search term for the title (substring)
     * @return List of matching {@link Buch} objects (never {@code null})
     */
    List<Buch> buchsuche(String titel);

    /**
     * Searches for books by author name.
     *
     * @param autorName Partial or full name of the author
     * @return List of found books
     */
    List<Buch> sucheNachAutor(String autorName);

    /**
     * Searches for books by genre.
     *
     * @param genreName Name of the genre
     * @return List of found books
     */
    List<Buch> sucheNachGenre(String genreName);

    /**
     * Searches for books by publisher.
     *
     * @param verlagName Name of the publisher
     * @return List of found books
     */
    List<Buch> sucheNachVerlag(String verlagName);

    /* ------------------------------------------------------------------
     * Details & Reviews
     * ---------------------------------------------------------------- */

    /**
     * Returns the complete details of a book.
     *
     * @param id Book ID
     * @return {@link Buch} object or {@code null} if not found
     */
    Buch buchdetails(String id);

    /**
     * Returns all reviews for a given book.
     *
     * @param id Book ID
     * @return List of existing reviews (never {@code null})
     */
    List<Rezension> showRezensionen(String id);

    /**
     * Adds a new review to a book entity and saves it.
     *
     * @param id        Book ID
     * @param rezension New {@link Rezension} object
     */
    void reviewHinzufuegen(String id, Rezension rezension);
}
