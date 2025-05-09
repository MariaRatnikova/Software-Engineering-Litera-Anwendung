/**
 * Repräsentiert einen Autor eines Buches.
 * Wird als Modellklasse verwendet, z. B. in JSON-Dateien oder zur Anzeige.
 */
public class Autor {

    // Name des Autors
    private String name;

    /**
     * Leerer Konstruktor („No-Arg-Konstruktor“).
     * Wird z. B. von Jackson für die automatische Deserialisierung benötigt.
     */
    public Autor() {
    }

    /**
     * Konstruktor mit Parameter – zum einfachen Erstellen eines Autors mit Name.
     *
     * @param name Der Name des Autors
     */
    public Autor(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen des Autors zurück.
     *
     * @return Name des Autors
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Autors.
     *
     * @param name Neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt eine lesbare Textdarstellung des Autors zurück.
     * Wird z. B. beim Debuggen oder in Listenanzeigen verwendet.
     *
     * @return Textdarstellung
     */
    @Override
    public String toString() {
        return "Autor{name='" + name + "'}";
    }
}
