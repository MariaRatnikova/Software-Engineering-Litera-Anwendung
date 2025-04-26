import java.util.*;

public class Buch {
    private String titel;
    private String isbn;
    private Date erscheinungsjahr;
    private String beschreibung;

    private Autor autor;
    private Verlag verlag;
    private List<Genre> genres;
    private List<Rezension> rezensionen;

    public Buch(String titel, String isbn, Date erscheinungsjahr, String beschreibung,
                Autor autor, Verlag verlag,
                List<Genre> genres, List<Rezension> rezensionen) {
        this.titel = titel;
        this.isbn = isbn;
        this.erscheinungsjahr = erscheinungsjahr;
        this.beschreibung = beschreibung;
        this.autor = autor;
        this.verlag = verlag;
        this.genres = genres != null ? genres : new ArrayList<>();
        this.rezensionen = rezensionen != null ? rezensionen : new ArrayList<>();
    }

    // Constructor testing
    public Buch(String titel, String isbn, Date erscheinungsjahr, String beschreibung) {
        this(titel, isbn, erscheinungsjahr, beschreibung,
             null, null, new ArrayList<>(), new ArrayList<>());
    }
    

    public String getTitel() { return titel; }
    public String getIsbn() { return isbn; }
    public Date getErscheinungsjahr() { return erscheinungsjahr; }
    public String getBeschreibung() { return beschreibung; }

    public Autor getAutor() { return autor; }
    public Verlag getVerlag() { return verlag; }
    public List<Genre> getGenres() { return genres; }
    public List<Rezension> getRezensionen() { return rezensionen; }

    @Override
    public String toString() {
        return "Buch{" +
                "titel='" + titel + '\'' +
                ", autor=" + autor +
                '}';
    }
}
