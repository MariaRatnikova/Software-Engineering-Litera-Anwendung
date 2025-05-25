/**
 * Represents an author of a book.
 * Used as a model class, e.g., in JSON files or for display.
 */
public class Autor {

    // Name of the author
    private String name;

    /**
     * No-argument constructor.
     * Required by frameworks like Jackson for automatic deserialization.
     */
    public Autor() {
    }

    /**
     * Constructor with parameter – to easily create an author with a name.
     *
     * @param name The name of the author
     */
    public Autor(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the author.
     *
     * @return Author's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     *
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a readable string representation of the author.
     * Useful for debugging or in list displays.
     *
     * @return Text representation
     */
    @Override
    public String toString() {
        return "Autor{name='" + name + "'}";
    }
}
