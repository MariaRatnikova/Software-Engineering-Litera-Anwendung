/**
 * Model class for a publisher.
 * A publisher currently only has a name, but can be extended if needed 
 * (e.g., address, website).
 */
public class Verlag {

    /** Name of the publisher (e.g., "Penguin Books") */
    private String name;

    /**
     * No-argument constructor – required by libraries like Jackson.
     */
    public Verlag() {
    }

    /**
     * Constructor with name.
     *
     * @param name Name of the publisher
     */
    public Verlag(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the publisher.
     *
     * @return Name of the publisher
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the publisher.
     *
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a textual representation of the publisher.
     * Useful for debugging and logging.
     *
     * @return String representation of the publisher
     */
    @Override
    public String toString() {
        return "Verlag{name='" + name + "'}";
    }
}