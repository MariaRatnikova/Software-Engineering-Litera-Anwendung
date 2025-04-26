public class Verlag {
    private String name;

    public Verlag(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Verlag{name='" + name + "'}";
    }
}
