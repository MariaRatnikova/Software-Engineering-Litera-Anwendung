public class Verlag {
    private String name;

    /** No-Arg-Konstruktor für Jackson */
    public Verlag() {}

    public Verlag(String name) {
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
        return "Verlag{name='" + name + "'}";
    }
}
