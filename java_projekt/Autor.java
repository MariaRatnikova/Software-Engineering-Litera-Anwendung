public class Autor {
    private String name;

    /** No-Arg-Konstruktor für Jackson */
    public Autor() {}

    public Autor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Autor{name='" + name + "'}";
    }
}
