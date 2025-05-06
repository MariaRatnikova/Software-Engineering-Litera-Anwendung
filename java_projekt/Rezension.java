import java.util.Date;

/**
 * Modellklasse für anonyme Rezensionen eines Buches.
 * Eine Rezension besteht aus einer Bewertung (z. B. 1–5 Sterne), einem Kommentar,
 * dem Datum der Abgabe und der ID des Buches, auf das sie sich bezieht.
 */
public class Rezension {

    // Die ID des Buches, zu dem die Rezension gehört
    private String bookId;

    // Bewertung (z. B. 1 bis 5 Sterne)
    private int bewertung;

    // Freitext-Kommentar der Rezension
    private String kommentar;

    // Datum, an dem die Rezension abgegeben wurde
    private Date datum;

    /**
     * No-Argument-Konstruktor.
     * Wird benötigt für Frameworks wie Jackson, um Objekte aus JSON zu erstellen.
     */
    public Rezension() {}

    /**
     * Konstruktor mit allen Attributen.
     * @param bookId     ID des zugehörigen Buches
     * @param bewertung  Bewertung des Buches (z. B. 4)
     * @param kommentar  Freitext-Kommentar
     * @param datum      Datum der Rezension
     */
    public Rezension(String bookId, int bewertung, String kommentar, Date datum) {
        this.bookId    = bookId;
        this.bewertung = bewertung;
        this.kommentar = kommentar;
        this.datum     = datum;
    }

    // Getter und Setter für bookId
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    // Getter und Setter für bewertung
    public int getBewertung() {
        return bewertung;
    }
    public void setBewertung(int bewertung) {
        this.bewertung = bewertung;
    }

    // Getter und Setter für kommentar
    public String getKommentar() {
        return kommentar;
    }
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    // Getter und Setter für datum
    public Date getDatum() {
        return datum;
    }
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    /**
     * Gibt eine textuelle Darstellung der Rezension zurück.
     * Nützlich für Logging oder Debugging.
     */
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