import java.util.Date;

/**
 * Model class for anonymous reviews of a book.
 * A review consists of a rating (e.g., 1–5 stars), a comment,
 * the submission date, and the ID of the related book.
 */
public class Rezension {

    /** The ID of the book to which this review belongs. */
    private String bookId;

    /** Rating (e.g., 1 to 5 stars). */
    private int rating;

    /** Free-text comment of the review. */
    private String comment;

    /** Date the review was submitted. */
    private Date date;

    /**
     * No-argument constructor.
     * Required for frameworks like Jackson to create objects from JSON.
     */
    public Rezension() {
    }

    /**
     * Constructor with all attributes.
     *
     * @param bookId   ID of the associated book
     * @param rating   Rating of the book (e.g., 4)
     * @param comment  Free-text comment
     * @param date     Date of the review
     */
    public Rezension(String bookId, int rating, String comment, Date date) {
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Returns the book ID.
     *
     * @return Book ID
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * Sets the book ID.
     *
     * @param bookId Book ID
     */
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    /**
     * Returns the rating.
     *
     * @return Rating
     */
    public int getBewertung() {
        return rating;
    }

    /**
     * Sets the rating.
     *
     * @param rating New rating
     */
    public void setBewertung(int rating) {
        this.rating = rating;
    }

    /**
     * Returns the comment.
     *
     * @return Comment text
     */
    public String getKommentar() {
        return comment;
    }

    /**
     * Sets the comment.
     *
     * @param comment Comment text
     */
    public void setKommentar(String comment) {
        this.comment = comment;
    }

    /**
     * Returns the review date.
     *
     * @return Date
     */
    public Date getDatum() {
        return date;
    }

    /**
     * Sets the review date.
     *
     * @param date Date
     */
    public void setDatum(Date date) {
        this.date = date;
    }

    /**
     * Returns a string representation of the review.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Rezension{" +
               "bookId='" + bookId + '\'' +
               ", rating=" + rating +
               ", comment='" + comment + '\'' +
               ", date=" + date +
               '}';
    }
}
