import java.util.Date;

public class Rezension {
    private int bewertung;
    private String kommentar;
    private Date datum;

    public Rezension(int bewertung, String kommentar, Date datum) {
        this.bewertung = bewertung;
        this.kommentar = kommentar;
        this.datum = datum;
    }

    public int getBewertung() {
        return bewertung;
    }

    public String getKommentar() {
        return kommentar;
    }

    public Date getDatum() {
        return datum;
    }
    @Override
    public String toString() {
        return "Rezension{bewertung=" + bewertung + ", kommentar='" + kommentar + "', datum=" + datum + "}";
    }
}
