import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modellklasse für Bücher.
 * Diese Klasse spiegelt die Struktur der JSON-Dateien wider,
 * z. B. books.json, und wird von Jackson verwendet,
 * um Daten zu serialisieren und deserialisieren.
 */
public class Buch {

    // Eindeutige ID des Buches
    @JsonProperty("bookId")
    private String bookId;

    // Titel des Buches
    @JsonProperty("title")
    private String title;

    // Name des Autors oder der Autorin
    @JsonProperty("author")
    private String author;

    // Verlag des Buches
    @JsonProperty("publisher")
    private String publisher;

    // Liste der Genres, z. B. „Fantasy“, „Drama“ usw.
    @JsonProperty("genres")
    private List<String> genres = new ArrayList<>();

    // Beschreibung des Buches (z. B. für Detailansicht)
    private String description;

    /**
     * Standardkonstruktor (erforderlich für Jackson).
     */
    public Buch() {
    }

    /* ─────────── Getter & Setter ─────────── */

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gibt eine kompakte Textdarstellung des Buchobjekts zurück.
     * Nützlich für Debugging und Logausgaben.
     *
     * @return String-Repräsentation des Buches
     */
    @Override
    public String toString() {
        return String.format(
            "Buch{bookId='%s', title='%s', author='%s', publisher='%s'}",
            bookId, title, author, publisher
        );
    }
}