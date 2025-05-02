import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Modellklasse für Bücher, passend zum JSON-Format in Bucher.json.
 */
public class Buch {
    @JsonProperty("bookId")
    private String bookId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("genres")
    private List<String> genres = new ArrayList<>();

    // No-Arg-Konstruktor für Jackson
    public Buch() {}

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

    @Override
    public String toString() {
        return String.format(
            "Buch{bookId='%s', title='%s', author='%s', publisher='%s'}",
            bookId, title, author, publisher
        );
    }
}

