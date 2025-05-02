public class Genre {
    private String name;
    private String beschreibung;

    /** No-Arg-Konstruktor für Jackson */
    public Genre() {}

    public Genre(String name, String beschreibung) {
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public String toString() {
        return name + ": " + beschreibung;
    }
}

