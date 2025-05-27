import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for books.
 * This class reflects the structure of JSON files,
 * such as books.json, and is used by Jackson
 * for serialization and deserialization of data.
 */
public class Buch {

    // Unique ID of the book
    @JsonProperty("bookId")
    private String bookId;

    // Title of the book
    @JsonProperty("title")
    private String title;

    // Name of the author
    @JsonProperty("author")
    private String author;

    // Publisher of the book
    @JsonProperty("publisher")
    private String publisher;

    // List of genres, e.g., "Fantasy", "Drama", etc.
    @JsonProperty("genres")
    private List<String> genres = new ArrayList<>();

    // Description of the book (e.g., for the detail view)
    private String description;
    // Cover image path for the book
        @JsonProperty("image")
        private String image;


    /**
     * Default constructor (required by Jackson).
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

    public String getImage() {
    return image;
    }

    public void setImage(String image) {
    this.image = image;
    }


    /**
     * Returns a compact text representation of the book object.
     * Useful for debugging and logging output.
     *
     * @return String representation of the book
     */
    @Override
    public String toString() {
        return String.format(
            "Buch{bookId='%s', title='%s', author='%s', publisher='%s'}",
            bookId, title, author, publisher
        );
    }
}
