/**
 * Repräsentiert ein Genre eines Buches, z. B. „Fantasy“ oder „Krimi“,
 * zusammen mit einer kurzen Beschreibung.
 */
public class Genre {

    // Name des Genres (z. B. „Fantasy“)
    private String name;

    // Beschreibung des Genres (z. B. „Magische Welten und epische Abenteuer“)
    private String beschreibung;

    /**
     * No-Argument-Konstruktor – notwendig für Frameworks wie Jackson,
     * die Objekte automatisch aus JSON-Daten erzeugen.
     */
    public Genre() {
    }

    /**
     * Konstruktor mit Parametern zum direkten Setzen von Name und Beschreibung.
     *
     * @param name         Name des Genres
     * @param beschreibung Beschreibung des Genres
     */
    public Genre(String name, String beschreibung) {
        this.name = name;
        this.beschreibung = beschreibung;
    }

    /**
     * Gibt den Namen des Genres zurück.
     *
     * @return Name des Genres
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Genres.
     *
     * @param name Neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Beschreibung des Genres zurück.
     *
     * @return Beschreibung des Genres
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Setzt die Beschreibung des Genres.
     *
     * @param beschreibung Neue Beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Gibt eine textuelle Darstellung des Genres zurück,
     * z. B. „Fantasy: Magische Welten und epische Abenteuer“.
     *
     * @return String-Repräsentation des Genres
     */
    @Override
    public String toString() {
        return name + ": " + beschreibung;
    }
}
