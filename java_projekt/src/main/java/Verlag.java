/**
 * Modellklasse für einen Verlag (Publisher).
 * Ein Verlag hat aktuell nur einen Namen, kann aber bei Bedarf erweitert werden 
 * (z. B. Adresse, Webseite).
 */
public class Verlag {

    /** Name des Verlags (z. B. „Penguin Books“) */
    private String name;

    /**
     * No-Arg-Konstruktor – erforderlich für Bibliotheken wie Jackson.
     */
    public Verlag() {
    }

    /**
     * Konstruktor mit Name.
     *
     * @param name Name des Verlags
     */
    public Verlag(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen des Verlags zurück.
     *
     * @return Name des Verlags
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Verlags.
     *
     * @param name Neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt eine textuelle Darstellung des Verlags zurück.
     * Nützlich für Debugging und Logging.
     *
     * @return String-Repräsentation des Verlags
     */
    @Override
    public String toString() {
        return "Verlag{name='" + name + "'}";
    }
}
