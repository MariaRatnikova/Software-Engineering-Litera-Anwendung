import java.util.Date;

/**
 * Modellklasse für anonyme Rezensionen.
 */
public class Rezension {
    private String bookId;   // ID des Buches als String
    private int bewertung;
    private String kommentar;
    private Date datum;

    // No-Arg-Konstruktor für Jackson
    public Rezension() {}

    public Rezension(String bookId, int bewertung, String kommentar, Date datum) {
        this.bookId    = bookId;
        this.bewertung = bewertung;
        this.kommentar = kommentar;
        this.datum     = datum;
    }

    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getBewertung() {
        return bewertung;
    }
    public void setBewertung(int bewertung) {
        this.bewertung = bewertung;
    }

    public String getKommentar() {
        return kommentar;
    }
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public Date getDatum() {
        return datum;
    }
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        return "Rezension{" +
               "bookId='" + bookId + '\'' +
               ", bewertung=" + bewertung +
               ", kommentar='" + kommentar + '\'' +
               ", datum=" + datum +
               '}';
    }
}
